package com.raghu.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class FeedJobExecutionListener implements JobExecutionListener {

	@Override
	public void beforeJob(final JobExecution jobExecution) {
		System.out.println("Before starting the job: " + jobExecution.getJobInstance().getJobName());
		System.out.println("Before starting the job: " + jobExecution.getExecutionContext().toString());
		jobExecution.getExecutionContext().put("name", "jair");
	}

	@Override
	public void afterJob(final JobExecution jobExecution) {
		System.out.println(
				"After finish the job - Job Execution Context " + jobExecution.getExecutionContext().toString());

	}

}
