package com.omni.fansoffury.startup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ApplicationStartupListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) {
    	//WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
//        WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(listener);
    	
    }

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}