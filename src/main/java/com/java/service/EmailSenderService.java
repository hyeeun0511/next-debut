package com.java.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final String from;

    public EmailSenderService(JavaMailSender mailSender,
                              @Value("${app.mail.from:}") String from) {
        this.mailSender = mailSender;
        this.from = from == null ? "" : from.trim();
    }

    public void sendVerificationCode(String toEmail, String code) {
        String to = toEmail == null ? "" : toEmail.trim();
        if (to.isBlank()) {
            throw new IllegalArgumentException("이메일이 비어 있습니다.");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("인증번호 생성에 실패했습니다.");
        }
        if (from.isBlank()) {
            throw new IllegalStateException("메일 발신자(from) 설정이 필요합니다.");
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject("[NEXT DEBUT] 이메일 인증번호");
        msg.setText("안녕하세요.\n\n요청하신 이메일 인증번호는 아래와 같습니다.\n\n인증번호: " + code + "\n\n감사합니다.\nNEXT DEBUT");
        mailSender.send(msg);
    }
}

