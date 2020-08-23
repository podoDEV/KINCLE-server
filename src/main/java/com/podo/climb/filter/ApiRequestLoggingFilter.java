package com.podo.climb.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.podo.climb.model.response.ApiResult;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiRequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRequestLoggingFilter.class);

    final ObjectMapper objectMapper;

    public ApiRequestLoggingFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean isFirstRequest = !isAsyncDispatch(request);

        HttpServletResponse responseToUse = response;

        HttpServletRequest requestToUse = request;

        if (isFirstRequest && !(response instanceof ContentCachingResponseWrapper)) {
            responseToUse = new ContentCachingResponseWrapper(response);
        }

        if (isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request);

        }

        StopWatch stopWatch = new StopWatch();


        try {
            stopWatch.start();
            filterChain.doFilter(requestToUse, responseToUse);
        } finally {
            stopWatch.stop();

            if (!isAsyncStarted(request)) {
                logRequest(requestToUse, responseToUse, stopWatch.getTotalTimeMillis());
            }
        }
    }

    private void logRequest(HttpServletRequest request, HttpServletResponse response, long executionTime) {

        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (responseWrapper == null)
            return;

        Content content = getContent(responseWrapper);

        int resultCode = -1;

        ApiResult<?> apiResult = convertContentToApiResult(content.getContent());
        if (apiResult != null) {
            resultCode = apiResult.getCode();
        }

        String payload = "";
        try {
            MediaType mediaType = MediaType.parseMediaType(request.getContentType());
            if (mediaType.isCompatibleWith(MediaType.APPLICATION_JSON)) {
                ContentCachingRequestWrapper contentCachingRequestWrapper =
                        (ContentCachingRequestWrapper) WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
                if (contentCachingRequestWrapper != null) {
                    payload = new String(contentCachingRequestWrapper.getContentAsByteArray(), 0,
                            contentCachingRequestWrapper.getContentAsByteArray().length,
                            contentCachingRequestWrapper.getCharacterEncoding());
                }
            }
        } catch (Exception e) {
            // ignore
        }

        String uri = getRequestUriAndQueryString(request);
        if (!uri.startsWith("/webjars/springfox-swagger-ui")) {
            LOGGER.info("{} {} {} {} {} {} {} {}",
                    request.getRemoteAddr(),
                    request.getMethod(),
                    uri,
                    responseWrapper.getStatus(),
                    content.getContentSize(),
                    executionTime,
                    resultCode,
                    payload);
        }

    }

    private Content getContent(ContentCachingResponseWrapper wrapper) {
        String content = null;

        byte[] buf = wrapper.getContentAsByteArray();

        int contentSize = buf.length;
        if (contentSize > 0) {
            try {
                content = new String(buf, 0, contentSize, wrapper.getCharacterEncoding());
                wrapper.copyBodyToResponse();
            } catch (Exception e) {
                // ignore
            }
        }

        return new Content(content, contentSize);
    }

    private ApiResult<?> convertContentToApiResult(String content) {
        if (content == null) {
            return null;
        }

        DefaultApiResult<?> apiResult = null;
        try {
            apiResult = objectMapper.readValue(content, new TypeReference<DefaultApiResult<?>>() {
            });
        } catch (IOException e) { /* ignore */ }

        return apiResult;
    }

    private String getRequestUriAndQueryString(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();

        sb.append(request.getRequestURI());

        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            sb.append("?");
            sb.append(queryString);
        }

        return sb.toString();
    }

    @Getter
    @Setter
    private static class Content {
        private String content;
        private int contentSize;


        public Content(String content, int contentSize) {
            this.content = content;
            this.contentSize = contentSize;
        }

    }

    private static class DefaultApiResult<T> extends ApiResult<T> {
        public DefaultApiResult() {
            super(0);
        }
    }

}
