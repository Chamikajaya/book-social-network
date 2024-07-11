package com.chamika.books_project.emails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;


    @Async
    // * using @Async to send emails asynchronously, so that the user does not have to wait for the email to be sent
    public void sendEmail(
            String to,
            String userName,
            EmailTemplateName emailTemplate,  // template to be used for the email.
            String confirmationUrl,
            String activationCode,
            String subject
    ) throws MessagingException {

        String templateName;

        // If no email template is provided, use the default "confirm-email" template
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        // the properties to be passed to the email template
        Map<String, Object> properties = new HashMap<>();
        properties.put("userName", userName);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        mimeMessageHelper.setFrom("chamika.21@cse.mrt.ac.lk");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);

        String template = springTemplateEngine.process(templateName, context);

        mimeMessageHelper.setText(template, true);

        javaMailSender.send(mimeMessage);


    }


}
