package com.argentinaprograma.backend.service;

import com.argentinaprograma.backend.dto.ContactFormDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Xander.-
 */
@Service
public class EmailService {

    Logger logger = LogManager.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailTo;

    @Async
    public void send(ContactFormDTO contactFormDTO) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
                mimeMessage.setSubject("Portfolio - " + contactFormDTO.getName() + " quiere contactarte");

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setText(
                        "<html>"
                                +	"<body>"
                                +	"De: " + contactFormDTO.getName() + "<br/>"
                                +   "Email: " + contactFormDTO.getEmail()
                                +   "<br/><br/>"
                                +   contactFormDTO.getMessage()
                                +   "</body>"
                                + "</html>", true);
            }
        };

        try {
            emailSender.send(preparator);
            logger.info("Email enviado con éxito.");
        } catch (Exception e) {
            logger.error("Error al enviar el email.");
            throw e;
        }
    }
}