package net.readify.knockknock;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	private static Server server;
	private static ClassPathXmlApplicationContext context;
	
	public static void main(String[] args) {
		LOG.debug("Loading application context");
		
		try {
			context = new ClassPathXmlApplicationContext("context-application.xml");
			KnockKnockResource resource = context.getBean(KnockKnockResource.class);
			
			LOG.info("Initializing server factory");
			JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
			factoryBean.setResourceClasses(KnockKnockResource.class);
			factoryBean.setResourceProvider(KnockKnockResource.class,
					new SingletonResourceProvider(resource, true));
			factoryBean.setAddress("http://localhost:8090");
			
			LOG.info("Starting REST server");
			server = factoryBean.create();
			server.start();
			
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					cleanup();
				}
			});
			
			LOG.info("Server started. Press Ctrl+C to stop");
		} 
		catch (Exception ex) {
			LOG.error("Application failed to start", ex);
		}
	}
	
	private static void cleanup() {
		try {
			if (server != null) {
				LOG.info("Stopping server");
				server.stop();
				server.destroy();
				server = null;
			}
			
			if (context != null) {
				context.stop();
				context.destroy();
				context = null;
			}
		}
		catch (Exception ex) {
			LOG.warn("Failed to cleanup properly", ex);
		}
		
		LOG.info("Done");
	}
}
