package com.sample.couchbase.spring.mca;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sample.couchbase.spring.mca.config.CouchbaseProperties;

@SpringBootApplication
public class SpringMCAApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(SpringMCAApplication.class);

	@Autowired
	private CouchbaseProperties couchbaseProperties;
	
  	public static void main(String[] args) {
  		SpringApplication.run(SpringMCAApplication.class, args);
   	}

	@Component
    @Order(1)
    public class MCAAppRunner implements ApplicationRunner {

		@Override
		public void run(ApplicationArguments args) throws Exception {
			logger.info("<MCAAppRunner.main(START)> command-line arguments getOptionNames: {}, arguments: {}", args.getOptionNames().size(), Arrays.toString(args.getSourceArgs()));
			logger.info("<MCAAppRunner.main()> couchbaseProperties: {}", couchbaseProperties);
			
			logger.info("<MCAAppRunner.main(END)> command-line arguments: {}", Arrays.toString(args.getSourceArgs()));

		}
	}
}
