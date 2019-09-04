/**
 * 
 */
package com.springscheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Component;
/**
 * @author Siddhant Sharma
 *
 */
@Component
public class SchedulerStop 
{
	private static final Logger logger = LoggerFactory.getLogger(SchedulerStop.class);

	@Autowired
	ApplicationContext context;
	
	/*
	 * This method will destroy all the spring schedulers,scheduled using @Scheduled.
	 * All the schedulers will be stopped after 2 min of application context created, means once the project started
	 *
	 */
	public void stopJobSchedulerWhenSchedulerDestroyed() throws Exception {
		ScheduledAnnotationBeanPostProcessor bean = (ScheduledAnnotationBeanPostProcessor) context.getBean("scheduleFixedDelayTask");
		bean.destroy();
		logger.info("Bean Destroyed, Now None of the scheduler will be running");
	}
}
