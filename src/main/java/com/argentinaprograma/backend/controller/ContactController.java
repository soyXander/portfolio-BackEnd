package com.argentinaprograma.backend.controller;

import com.argentinaprograma.backend.dto.ContactFormDTO;
import com.argentinaprograma.backend.service.EmailService;
import com.argentinaprograma.backend.utils.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Xander.-
 */
@RestController
@RequestMapping("/contacto")
@CrossOrigin(origins = "http://localhost:4200/")
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<?> sendEmail(@RequestParam("contact") String contact) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ContactFormDTO contactFormDTO = mapper.readValue(contact, ContactFormDTO.class);
        if (StringUtils.isBlank(contactFormDTO.getName()))
            return new ResponseEntity(new Message("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(contactFormDTO.getEmail()))
            return new ResponseEntity(new Message("El email es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(contactFormDTO.getMessage()))
            return new ResponseEntity(new Message("El mensaje es obligatorio"), HttpStatus.BAD_REQUEST);
        emailService.send(contactFormDTO);

        return new ResponseEntity(new Message("Email enviado con éxito"), HttpStatus.CREATED);
    }
}
