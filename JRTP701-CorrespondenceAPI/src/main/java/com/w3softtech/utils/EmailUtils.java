package com.w3softtech.utils;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;



@Component
public class EmailUtils {
	@Autowired
	private  JavaMailSender mailSender;
	
	public  void sendMail(String email,String subject,String body ,File file) throws Exception{
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(email);
		helper.setSentDate(new Date());
		helper.setText(body, true); // body is sending as a HTML
		helper.setSubject(subject);
		//add file as a attachment
		helper.addAttachment(file.getName(), file);
		mailSender.send(msg);

	}
}
