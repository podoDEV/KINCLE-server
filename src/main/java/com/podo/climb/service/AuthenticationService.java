package com.podo.climb.service;

import com.podo.climb.Utils.PasswordGenerator;
import com.podo.climb.encoder.Sha256PasswordEncoder;
import com.podo.climb.entity.Member;
import com.podo.climb.exception.ApiFailedException;
import com.podo.climb.model.AuthenticationToken;
import com.podo.climb.model.OauthType;
import com.podo.climb.model.request.SignInRequest;
import com.podo.climb.repository.MemberRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import java.util.Optional;

@Slf4j
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final MemberService memberService;
    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;

    @Autowired
    AuthenticationService(AuthenticationManager authenticationManager,
                          MemberService memberService,
                          JavaMailSender javaMailSender,
                          MemberRepository memberRepository) {
        this.authenticationManager = authenticationManager;
        this.memberService = memberService;
        this.javaMailSender = javaMailSender;
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
            throw new ApiFailedException(400, "bad request");
        }
    }

    private AuthenticationToken selfSignIn(SignInRequest signInRequest,
                                           HttpSession session) {
        String emailAddress = signInRequest.getEmailAddress();
        String password = signInRequest.getPassword();

        Member member = Optional.ofNullable(memberRepository.findByEmailAddress(emailAddress)).orElseThrow(new ApiFailedException(400, "wrong email or password"));

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

    public void initPassword(String emailAddress) throws Exception {
        Member member = memberService.findByEmailAddress(emailAddress);
        //TODO: null 처리
        if (!OauthType.SELF.equals(member.getOauthType())) {
            throw new ApiFailedException(403, "only self member can change password");
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("no-reply-climb@gmail.com");
        msg.setTo(member.getEmailAddress());
        msg.setSubject("비밀번호 변경");
        String newPassword = generateNewPassword();
        changePassword(member, newPassword);
        msg.setText("새 비밀번호:" + newPassword);

        try {
            javaMailSender.send(msg);
        } catch (MailException ex) {
            log.error("", ex);
            throw new RuntimeException();
        }
    }

    public void changePassword(String password) {
        Member member = memberService.getCurrentMember();
        changePassword(member, password);
    }

    private String generateNewPassword() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        return passwordGenerator.generate(8);
    }

    public void signOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    private void changePassword(Member member, String password) {
        Sha256PasswordEncoder sha256PasswordEncoder = new Sha256PasswordEncoder();
        member.setPassword(sha256PasswordEncoder.encode(password));
        memberRepository.saveAndFlush(member);
    }

}
