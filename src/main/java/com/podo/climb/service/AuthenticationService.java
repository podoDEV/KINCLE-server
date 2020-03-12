package com.podo.climb.service;

import com.podo.climb.entity.Member;
import com.podo.climb.model.AuthenticationToken;
import com.podo.climb.model.OauthType;
import com.podo.climb.model.request.SignInRequest;
import com.podo.climb.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private MemberRepository memberRepository;

    @Autowired
    AuthenticationService(AuthenticationManager authenticationManager,
                          MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
    }

    public AuthenticationToken signIn(SignInRequest signInRequest,
                                      HttpSession session) {
        OauthType oauthType = signInRequest.getOauthType();
        if (OauthType.SELF.equals(oauthType)) {
            return selfSignIn(signInRequest, session);
        } else if (OauthType.GOOGLE.equals(oauthType) || OauthType.APPLE.equals(oauthType)) {
            return oauthSignIn(signInRequest, session);
        } else {
            throw new RuntimeException();
        }
    }

    private AuthenticationToken selfSignIn(SignInRequest signInRequest,
                                           HttpSession session) {
        String emailAddress = signInRequest.getEmailAddress();
        String password = signInRequest.getPassword();

        Member member = memberRepository.findByEmailAddress(emailAddress);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(emailAddress, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                             SecurityContextHolder.getContext());

        return new AuthenticationToken(member.getNickname(), member.getMemberRole(), session.getId());
    }

    private AuthenticationToken oauthSignIn(SignInRequest signInRequest,
                                            HttpSession session) {

        String memberToken = signInRequest.getToken();
        OauthType oauthType = signInRequest.getOauthType();
        Member member = memberRepository.findByTokenAndOauthType(memberToken, oauthType);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member.getEmailAddress(), memberToken);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                             SecurityContextHolder.getContext());

        return new AuthenticationToken(member.getNickname(), member.getMemberRole(), session.getId());
    }

    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

}
