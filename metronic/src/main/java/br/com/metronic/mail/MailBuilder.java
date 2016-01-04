package br.com.metronic.mail;

import lombok.Getter;

public class MailBuilder {
	
	private @Getter String from;
	private @Getter String to;
	private @Getter String subject;
	private @Getter String body;
	
	public MailBuilder from(String from) {
		this.from = from;
		return this;
	}
	
	public MailBuilder to(String to) {
		this.to = to;
		return this;
	}
	
	public MailBuilder subject(String subject) {
		this.subject = subject;
		return this;
	}
	
	public MailBuilder body(String body) {
		this.body = body;
		return this;
	}
	
	public Mail create() {
		return new Mail(this);
	}
}