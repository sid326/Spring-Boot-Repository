/**
 * 
 */
package com.springscheduler;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Siddhant Sharma
 *
 */
@Configuration
@EnableScheduling
public class SpringBootScheduler 
{
	private static final Logger logger = LoggerFactory.getLogger(SpringBootScheduler.class);
	
	/*
	 * We are using this counter so that if we cross 100 schedules in total, we will stop our all of schedulers 
	 */
	private final AtomicInteger counter = new AtomicInteger(0);
	
	@Autowired
	SchedulerStop scheduleStop;
	
	/*
	 * @Scheduled
	 * Spring Scheduler for cron values, the the schedule of every minute.
	 */
	@Scheduled(cron = "0 * * * * *")
	public void scheduleCronJobEveryMin() throws Exception
	{
		counter.incrementAndGet();
		LocalDateTime dateTime = LocalDateTime.now();
		Thread.currentThread().setName("scheduleCronJobEveryMin");
		logger.info("scheduleCronJobEveryMin Current DateTime {} Thread Name {}",dateTime, Thread.currentThread().getName());
	}
	
	/*
	 * Spring Scheduler for Fixed delay values.
	 * Here the duration between the end of last execution and the start of next execution is fixed with 1000 millisecond
	 * with initial delay of 1000 millisecond
	 */
	@Scheduled(fixedDelay = 1000, initialDelay = 1000)
	public void scheduleFixedDelayTask() 
	{
		counter.incrementAndGet();
		Thread.currentThread().setName("scheduleFixedDelayTask");
		LocalDateTime dateTime = LocalDateTime.now();
		logger.info("scheduleFixedDelayTask Current DateTime {} Thread Name {}",dateTime, Thread.currentThread().getName());
	}
	
	/*
	 * Spring Scheduler for Fixed rate values.
	 * Here the scheduler will be running with the fixed rate of 2000 millisecond without any initial delays
	 */
	@Scheduled(fixedRate = 2000)
	public void scheduleFixedRateTask() 
	{
		counter.incrementAndGet();
		Thread.currentThread().setName("scheduleFixedRateTask");
		LocalDateTime dateTime = LocalDateTime.now();
		logger.info("scheduleFixedRateTask Current DateTime {} Thread Name {}",dateTime, Thread.currentThread().getName());
		if(counter.get() >= 100)
		{
			try 
			{
				scheduleStop.stopJobSchedulerWhenSchedulerDestroyed();
			} 
			catch (Exception e) 
			{
				logger.info("Exception Caught with message {} and cause {}",e.getMessage(),e.getCause());
			}
		}
	}
	
	
}
