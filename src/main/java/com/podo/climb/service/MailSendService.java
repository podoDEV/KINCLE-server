//
//package com.podo.climb.service;
//
//import com.podo.climb.Utils.PasswordGenerator;
//import com.podo.climb.entity.Member;
//import com.podo.climb.exception.ApiFailedException;
//import com.podo.climb.model.OauthType;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//public class MailSendService {
//
//    private MemberService memberService;
//    private AuthenticationService authenticationService;
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    MailSendService(MemberService memberService,
//                    AuthenticationService authenticationService,
//                    JavaMailSender javaMailSender) {
//        this.memberService = memberService;
//        this.authenticationService = authenticationService;
//        this.javaMailSender = javaMailSender;
//
//    }
//
//    public void initPassword(String emailAddress) throws Exception {
//        Member member = memberService.findByEmailAddress(emailAddress);
//        if (!OauthType.SELF.equals(member.getOauthType())) {
//            throw new ApiFailedException(403, "only self member can change password");
//        }
//
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom("no-reply-climb@gmail.com");
//        msg.setTo(member.getEmailAddress());
//        msg.setSubject("비밀번호 변경");
//        String newPassword = generateNewPassword();
//        authenticationService.generateTempPassword(member, newPassword);
//        msg.setText("새 비밀번호:" + newPassword);
//
//        try {
//            javaMailSender.send(msg);
//        } catch (MailException ex) {
//            log.error("{}", ex);
//            throw new RuntimeException();
//        }
//    }
//
//    private String generateNewPassword() {
//        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
//                .useDigits(true)
//                .useLower(true)
//                .useUpper(true)
//                .build();
//        return passwordGenerator.generate(8);
//    }
//}
