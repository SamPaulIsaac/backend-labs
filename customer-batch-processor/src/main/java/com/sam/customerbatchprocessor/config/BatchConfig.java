package com.sam.customerbatchprocessor.config;

import com.sam.customerbatchprocessor.entity.Customer;
import com.sam.customerbatchprocessor.exception.ValidationException;
import com.sam.customerbatchprocessor.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfig {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private CustomSkipListener customSkipListener;

    @Autowired
    private CustomJobExecutionListener customJobExecutionListener; // Injected as @Component


    @Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("customers.csv"));
        reader.setLinesToSkip(1);

        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setDelimiter(DELIMITER_COMMA);
                setNames("id", "name", "email", "status");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Customer.class);
            }});
        }});

        return reader;
    }

    @Bean
    public ItemProcessor<Customer, Customer> processor() {
        return customer -> {
            log.info("Validating customer record → ID: {}, Email: '{}'", customer.getId(), customer.getEmail());
            if (customer.getEmail() == null || customer.getEmail().isBlank()) {
                log.warn("Skipping customer with missing email: {}", customer);
                throw new ValidationException("Skipping customer with missing email: " + customer);
            }
            return customer;
        };
    }

    @Bean
    public ItemWriter<Customer> writer() {
        return customers -> {
            log.info("Writing {} customer records to DB", customers.size());
            customers.forEach(c -> log.info("Writing: {}", c));
            customerRepository.saveAll(customers);
        };
    }

    @Bean
    public Step customerImportStep() {
        return new StepBuilder("customer-import-job-" + LocalDateTime.now().toString(), jobRepository)
                .<Customer, Customer>chunk(2, platformTransactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
                .skip(ValidationException.class)
                .skipLimit(5)
                .listener((SkipListener<?, ?>) customSkipListener)          // Register skip listener
                .listener((StepExecutionListener) customSkipListener)       // Register step execution listener
                .listener(customJobExecutionListener)
                .build();
    }

    @Bean
    public Job customerImportJob() {
        return new JobBuilder("customer-import-job-" + LocalDateTime.now(),
                jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(customJobExecutionListener)
                .start(customerImportStep())
                .build();
    }
}
