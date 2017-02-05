package net.readify.knockknock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	private static ClassPathXmlApplicationContext context;
	
	public static void main(String[] args) {
		LOG.debug("Loading application context");
		
		try {
			LOG.info("Initializing application context and starting the server");
			context = new ClassPathXmlApplicationContext("context-application.xml");
			
			LOG.info("Adding shutdown hook");
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
