package com.raghu.config;

import com.raghu.listener.HelloWorldStepExecutionListener;
import com.raghu.processor.InMemoryItemProcessor;
import com.raghu.reader.InMemoryReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.raghu.listener.HelloWorldJobExecutionListener;
import com.raghu.writer.ConsoleItemWriter;

@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Autowired
	private HelloWorldJobExecutionListener jobListener;

	@Autowired
	private HelloWorldStepExecutionListener stepListener;

	@Autowired
	private InMemoryItemProcessor processor;

	@Bean
	public Step step1() {
		return steps.get("step-1").listener(stepListener).tasklet(helloworldTasklet()).build();
	}

	private Tasklet helloworldTasklet() {
		return new Tasklet() {

			@Override
			public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext)
					throws Exception {
				System.out.println("Hello world");
				return RepeatStatus.FINISHED;
			}
		};
	}

	@Bean
	public Step step2() {
		return steps.get("step-2").<Integer, Integer>chunk(3).reader(reader()).processor(processor)
				.writer(new ConsoleItemWriter()).build();
	}

	@Bean
	public ItemReader<? extends Integer> reader() {
		return new InMemoryReader();
	}

	@Bean
	public Job helloWorldJob() {
		return jobs.get("helloWorld").listener(jobListener).start(step1()).next(step2()).build();
	}

}
