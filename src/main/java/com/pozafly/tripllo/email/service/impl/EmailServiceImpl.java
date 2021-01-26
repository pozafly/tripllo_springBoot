package com.pozafly.tripllo.email.service.impl;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.email.model.Email;
import com.pozafly.tripllo.email.service.EmailService;
import com.pozafly.tripllo.user.dao.UserDao;
import com.pozafly.tripllo.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

// https://1-7171771.tistory.com/85
@Service
public class EmailServiceImpl implements EmailService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;

    private static final String FROM_ADDRESS = "pozafly@gmail.com";

    @Override
    public ResponseEntity<Message> checkAndSendEmail(Email email) {
        User user = userDao.readUser(email.getUserId());
        if (!ObjectUtils.isEmpty(user)) {
            if (user.getEmail().equals(email.getUserEmail())) {
                String newPassword = getTempPassword();
                String emailTitle = "[Tripllo toy Project] "+ email.getUserId() + "님의 임시 비밀번호 안내 이메일 입니다.";
                String content = "";
                content += "<h1>" + email.getUserId() + "님의 임시 비밀번호 안내 이메일 입니다.</h1>";
                content += "<br/><br/><br/>";
                content += "<p>안녕하세요." + email.getUserId() + "님의 임시 비밀번호는 <b>" + newPassword + "</b> 입니다." +
                        " 로그인 후 반드시 비밀번호를 재설정 해주세요.</p>";
                content += "<p>혹시 문제가 생기면 pozafly@gmail.com으로 회신 부탁드립니다!</p>";

                Email newEmail = new Email(email.getUserId(), email.getUserEmail(), newPassword, emailTitle, content);
                sendEmail(newEmail);


                String encodePassword = passwordEncoder.encode(newPassword);

                Map<String, Object> map = new HashMap<>();
                map.put("password", encodePassword);
                map.put("id", email.getUserId());

                userDao.updateUser(map);

                headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
                message.setStatus(StatusEnum.OK);
                message.setMessage(ResponseMessage.SEND_EMAIL_SUCCESS);

                return new ResponseEntity<>(message, headers, HttpStatus.OK);
            } else {
                message.setStatus(StatusEnum.BAD_REQUEST);
                message.setMessage(ResponseMessage.ID_EMAIL_WRONG);
                return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
            }
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_USER);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    private void sendEmail(Email email) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        try {
            mailMessage.addRecipients(javax.mail.Message.RecipientType.TO, String.valueOf(new InternetAddress(email.getUserEmail())));
            mailMessage.setFrom(new InternetAddress(FROM_ADDRESS));
            mailMessage.setSubject(email.getEmailTitle());
            mailMessage.setText(email.getContent(), "UTF-8", "html");

            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            return;
        } catch (MailException e) {
            e.printStackTrace();
            return;
        }
    }

    private String getTempPassword() {
        String newPassword = "";
        int idx = 0;
        char[] charset = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        for(int i = 0; i < 10; i++) {
            idx = (int) (charset.length * Math.random());
            newPassword += charset[idx];
        }
        System.out.println(newPassword);
        return newPassword;
    }
}
