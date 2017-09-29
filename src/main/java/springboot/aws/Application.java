package springboot.aws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	 
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
    @Bean
    CommandLineRunner init(PersonRepository rr) {	
        return args -> {
        	Iterable<Person> people = rr.findAll();
        	if (!people.iterator().hasNext()) {
        		Stream.of("Fabio", "Carlos", "Maria", "Ana")
        		.forEach(name -> rr.save(new Person(0, name)));
        	}
	        rr.findAll().forEach(p -> LOGGER.info("Person: " + p));
        };
    }
    
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DATABASE_URL = "jdbc:mysql://<host>:<port>";

    public static void initSQLServer() throws Exception {
        try {
        	LOGGER.info("Loading driver");
        	Class.forName(DRIVER).newInstance();
            try {
            	LOGGER.info("Getting connection");
                Connection connection = DriverManager.getConnection(DATABASE_URL, "springbootrdsdb", "spring1234");
                String sql = "CREATE DATABASE springbootdb";
                Statement statement = connection.createStatement();
                LOGGER.info("Creating database");
                statement.executeUpdate(sql);
                statement.close();
                
                connection.commit();
                connection.close();
                LOGGER.info("Database created");
            } catch (SQLException e) {
                LOGGER.error("Error", e);
                throw e;
            }
        } catch (Exception ex) {
        	LOGGER.error("Error", ex);
        	throw ex;
        }
    }

}