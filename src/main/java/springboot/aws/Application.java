package springboot.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	 
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		Jackson2ObjectMapperBuilder.json()
    		.featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
	}
	
    @Bean
    CommandLineRunner init(PersonRepository rr) {	
        return args -> {
	        rr.findAll().forEach(p -> LOGGER.info("Person: " + p));
        };
    }

}