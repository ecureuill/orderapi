package com.ecureuill.ada.avanade.orderapi.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendEmail(String to, String subject, String text) {
        Email emailFrom = new Email("logikasciuro@gmail.com");
        Email emailTo = new Email(to);
        Content content = new Content("text/plain", text);
        Mail mail = new Mail(emailFrom, subject, emailTo, content);

        System.out.println(System.getenv("SENDGRID_API_KEY"));
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
