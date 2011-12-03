package collab.todo

import org.apache.log4j.Logger;

import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.ByteArrayResource

import org.springframework.mail.MailException
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.MimeMessageHelper

import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress;

class EMailAuthenticatedService {

	boolean transactional = false
	MailSender mailSender

	def sendEmail = { mail, eMailProperties, attachements ->
		MimeMessage mimeMessage = mailSender.createMimeMessage()

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "ISO-8859-1")
		helper.from = eMailProperties.from
		helper.to = getInternetAddresses(mail.to)
		helper.subject = mail.subject
		helper.setText(mail.text, true);
		if(mail.bcc) helper.bcc = getInternetAddresses(mail.bcc)
		if(mail.cc) helper.cc = getInternetAddresses(mail.cc)

		attachements.each { key, value ->
			helper.addAttachment(key, new ByteArrayResource(value))
		}

		mailSender.send(mimeMessage)
	}

	private InternetAddress[] getInternetAddresses(List emails) {
		InternetAddress[] mailAddresses = new InternetAddress[emails.size()]
		emails.eachWithIndex {mail, i ->
			mailAddresses[i] = new InternetAddress(mail)
		}
		return mailAddresses
	}
}