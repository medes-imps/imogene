
------------------------------------------------------------------------------------------------------
                  Instructions to use the web application in the Imogene RCP container 
                  to build the Imogene Desktop application
------------------------------------------------------------------------------------------------------




-> Change the following configurations:




-----------------------------
in application-context.xml:
-----------------------------
Comment this imogSecurityDetailService configuration
	<!-- Spring-security users details provider -->
	<bean class="org.imogene.avion.server.security.ImogSecurityDetailService" id="imogSecurityDetailService">
		<property name="genericHandler" ref="genericHandler"/>
		<property name="managerHandler" ref="managerHandler"/>
		<property name="technicianHandler" ref="technicianHandler"/>
  	</bean> 
  	
Replace it by this one (uncomment it)
	<!-- Spring-security users details provider for web application in rcp container -->
	<bean class="org.imogene.web.server.security.ImogSecurityDetailServiceForRcp" id="imogSecurityDetailService"/>
	
	
	
	
-----------------------------
in security-context.xml:
-----------------------------
Comment this configuration
		<!-- for web application -->
		<form-login login-page="/jsp/Login.jsp"
			authentication-success-handler-ref="localeAuthenticationHandler"
			authentication-failure-url="/jsp/WrongLogin.jsp"/> 
  	
Replace it by this one (uncomment it)
		<!-- for web application in rcp container -->
		<form-login login-page="/jsp_rcp/Login.jsp"
			authentication-success-handler-ref="localeAuthenticationHandler"
			authentication-failure-url="/jsp_rcp/WrongLogin.jsp"/>	
			
			
			