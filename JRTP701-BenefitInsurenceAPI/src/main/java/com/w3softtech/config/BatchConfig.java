package com.w3softtech.config;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.w3softtech.entity.EligibilityDetails;
import com.w3softtech.entity.EligibilityDetailsEntity;
import com.w3softtech.processor.EDDetailsProcessor;
import com.w3softtech.repository.IEligibilityDetermineRepository;

@Configuration
public class BatchConfig {

	@Autowired
	private IEligibilityDetermineRepository elgRepo;
	
	@Autowired
	private EDDetailsProcessor processor;
	
	@Bean(name="reader")
	public RepositoryItemReader<EligibilityDetailsEntity> createReader(){
		return new RepositoryItemReaderBuilder<EligibilityDetailsEntity>()
				.name("repo-reader")
				.repository(elgRepo)
				.methodName("findAll")
				.sorts(Map.of("caseNo",Sort.Direction.ASC))
				.build();
	}
	@Bean(name="writer")
	public FlatFileItemWriter<EligibilityDetails> createWriter(){
		return new FlatFileItemWriterBuilder<EligibilityDetails>()
				.name("file-writer")
				.resource(new FileSystemResource("beneficiaries_list.csv"))
				.lineSeparator("\r\n")
				.delimited().delimiter(",")
				.names("caseNo","holderName","holderSSN","planName","planStatus","benifitAmt","bankName","accountNo")
				.build();
	}
	@Bean(name="step1")
	public Step createStep1(JobRepository jobRepository,PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1",jobRepository)
				.<EligibilityDetailsEntity,EligibilityDetails>chunk(3,transactionManager)
				.reader(createReader())
				.processor(processor)
				.writer(createWriter())
				.build();
	}
	@Bean(name="job")
	public Job createJob(JobRepository jobRepository,Step step1) {
		return new JobBuilder("job1",jobRepository)
				.incrementer(new RunIdIncrementer())
				.start(step1)
				.build();
	}
	
}
