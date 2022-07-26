package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.ContactFormDTO;
import com.argentinaprograma.backend.service.EmailService;
import com.argentinaprograma.backend.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xander.-
 */
@RestController
@RequestMapping("/contacto")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<?> sendEmail(@RequestBody ContactFormDTO contactFormDTO) {
        emailService.send(contactFormDTO);

        return new ResponseEntity(new Message("Email enviado con éxito"), HttpStatus.CREATED);
    }
}
