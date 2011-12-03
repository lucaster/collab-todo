// Place your Spring DSL code here
beans = {
	
	commonsMultipartResolver(org.springframework.web.multipart.commons.CommonsMultipartResolver) {
		maxUploadSize = 1000000 
	}

	//	<bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
	//		<property name="maxUploadSize"><value>1000000</value></property>
	//	</bean>
	
//	mailSender(org.springframework.mail.javamail.JavaMailSenderImpl) {
//		host = "smtp.apress.com"
//		session = mailSession
//	}
	
	//	<bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
	//		<property name="host" value="smtp.apress.com" />
	//		<property name="session" ref="mailSession" />
	//	</bean>
}