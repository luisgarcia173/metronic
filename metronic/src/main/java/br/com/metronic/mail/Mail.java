package br.com.metronic.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import lombok.Getter;

public class Mail {

	@Autowired
	private MailSender mailer;
	
	private final @Getter String from;
	private final @Getter String to;
	private final @Getter String subject;
	private final @Getter String body;
	
	public Mail(MailBuilder builder) {
		this.from = builder.getFrom();
		this.to = builder.getTo();
		this.subject = builder.getSubject();
		this.body = builder.getBody();
	}
	
	public void send() {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom(from);
		email.setTo(to);
		email.setSubject(subject);
		email.setText(body);
		mailer.send(email);
	}
	
}
