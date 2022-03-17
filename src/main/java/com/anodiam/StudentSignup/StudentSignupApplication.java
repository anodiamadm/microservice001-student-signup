package com.anodiam.StudentSignup;

//import org.apache.catalina.Context;
//import org.apache.catalina.connector.Connector;
//import org.apache.tomcat.util.descriptor.web.SecurityCollection;
//import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import com.anodiam.StudentSignup.secretManager.Quickstart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.context.annotation.Bean;

//@EnableEurekaClient
@SpringBootApplication
public class StudentSignupApplication {

	public static int languageId = 1;
	public static void main(String[] args) {
		System.out.println("Inside main()");
		SpringApplication.run(StudentSignupApplication.class, args);
		secretMgrExp();
	}

	private static void secretMgrExp(){
		System.out.println("\nInside secretMgrExp\n*************************\n");
		Quickstart quickstart = new Quickstart();
		quickstart.quickstart1();
		System.out.println("\n*************************\nCompleted secretMgrExp\n");
	}
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		System.out.println("Inside servletContainer()");
////		 Enable SSL Trafic
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
//			@Override
//			protected void postProcessContext(Context context) {
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
////		 Add HTTP to HTTPS redirect
//		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());
//		return tomcat;
//	}
//
////    We need to redirect from HTTP to HTTPS. Without SSL, this application used
////    port 4444. With SSL it will use port 8444. So, any request for 4444 needs to be
////    redirected to HTTPS on 8444.
//    private Connector httpToHttpsRedirectConnector() {
//		System.out.println("Inside httpToHttpsRedirectConnector()");
//		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
//		connector.setScheme("http");
//		connector.setPort(4444);
//		connector.setSecure(false);
//		connector.setRedirectPort(8444);
//		return connector;
//	}
}
