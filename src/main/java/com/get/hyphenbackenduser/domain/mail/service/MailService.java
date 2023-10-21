package com.get.hyphenbackenduser.domain.mail.service;

import com.get.hyphenbackenduser.domain.mail.exception.MailAuthKeyCreateException;
import com.get.hyphenbackenduser.domain.mail.exception.MailCreateException;
import com.get.hyphenbackenduser.domain.mail.exception.MailSendException;
import com.get.hyphenbackenduser.global.annotation.ServiceWithTransactionalReadOnly;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@ServiceWithTransactionalReadOnly
public class MailService {

    @Autowired
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final Random random = new Random();

    @Value("${AdminMail.id}")
    private String senderMail;
    @Value("${AdminMail.name}")
    private String senderName;

    private String setContext(String authKey) {
        Context context = new Context();
        context.setVariable("authKey", authKey);
        return templateEngine.process("mail", context);
    }

    public String sendMail(String mail) {
        String authKey = IntStream.range(0, 8).mapToObj(index -> {
                    int typeNumber = random.nextInt(3);
                    return switch (typeNumber) {
                        case 0 -> (char) (random.nextInt(26)+97); // a~z
                        case 1 -> (char) (random.nextInt(26)+65); // A~Z
                        case 2 -> Character.forDigit(random.nextInt(10), 10); // 0~9
                        default -> throw MailAuthKeyCreateException.EXCEPTION;
                    };
        }).map(String::valueOf).collect(Collectors.joining());

        try {
            MimeMessage message = emailSender.createMimeMessage();
            message.addRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("[인증번호:" + authKey + "] 하이픈 이메일 인증을 진행해 주세요.\n");
            message.setText(setContext(authKey), "utf-8", "html");
            message.setFrom(new InternetAddress(senderMail, senderName));
            emailSender.send(message);
        } catch (UnsupportedEncodingException e) {
            throw MailCreateException.EXCEPTION;
        } catch (MessagingException e) {
            throw MailSendException.EXCEPTION;
        }
        return authKey;
    }
}
