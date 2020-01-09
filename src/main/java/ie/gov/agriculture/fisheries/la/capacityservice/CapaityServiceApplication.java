package ie.gov.agriculture.fisheries.la.capacityservice;

import java.util.concurrent.Executor;

import javax.sql.DataSource;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import ie.gov.agriculture.fisheries.la.capacityservice.config.MapperConverter;


@SpringBootApplication
@EnableAsync
public class CapaityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapaityServiceApplication.class, args);
	}
	

	@Value("${database.user}")
	private String databaseUser;


	@Value("${database.password}")
	private String databasePassword;

	@Value("${database.url}")
	private String databaseUrl;
	
	@Bean(name = "dataSource")
	public DataSource jdbcDataSource() {
	    
	 
	    
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
	    dataSource.setUrl( databaseUrl );
	    dataSource.setUsername(databaseUser);
	    dataSource.setPassword(databasePassword);


	    return dataSource;
	}	
	
	@Bean
	public ModelMapper modelMapper() {
	  ModelMapper modelMapper = new ModelMapper();
	  modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
	  modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	  modelMapper.addConverter(MapperConverter.getDateMapper());
	  return modelMapper;
	}
    
    @Bean(name = "asyncExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Customer-Capacity-Service-");
        executor.initialize();
        return executor;
    }
}
