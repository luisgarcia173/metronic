package br.com.metronic.models;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
public @Data class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotBlank
	private String description;
	@NotBlank
	@Enumerated(EnumType.STRING)
	private NotificationType type;
	@DateTimeFormat
	private Calendar createdDate;
	@NotBlank
	private byte read;
	@Transient
	private String time;
	
	public String getTime(){
		DateTime now = new DateTime();
		DateTime created = new DateTime(createdDate.getTimeInMillis());
		
		int minutes = Minutes.minutesBetween(created, now).getMinutes();
		if (minutes <= 1) {
			return "just now";
		} else if (minutes < 60) {
			return minutes + " mins";
		} else {
			int hours = Hours.hoursBetween(created, now).getHours();
			if (hours < 24) {
				return hours == 1 ? (hours+" hr") : (hours+" hrs");
			} else {
				int days = Days.daysBetween(created, now).getDays();
				return days == 1 ? (days+" day") : (days+" days");
			}
		}
	}
	
}