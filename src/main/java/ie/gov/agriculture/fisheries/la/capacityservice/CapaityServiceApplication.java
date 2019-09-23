package ie.gov.agriculture.fisheries.la.capacityservice;

import java.util.concurrent.Executor;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class CapaityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapaityServiceApplication.class, args);
	}
	
//	@Value("${database.user:IFISSN_INET}")
	@Value("${database.user:IFIS}")
	private String databaseUser;

//	@Value("${database.password:IFISSN_INET}")
	@Value("${database.password:Wilco}")
	private String databasePassword;

	@Bean(name = "dataSource")
	@Profile({"tomcat","default"})
	public DataSource jdbcDataSource() {
	    
	    String databaseUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS= (PROTOCOL = TCP)(HOST = sdbahpocm01-db.agriculture.gov.ie)(PORT = 1532)) "
	                       + "(CONNECT_DATA=(SERVICE_NAME="
	                       + "DEVC"
	                       + ".agriculture.gov.ie))(HS=OK))";
//	    String databaseUrl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS= (PROTOCOL = TCP)(HOST = sdbahpocm01-db.agriculture.gov.ie)(PORT = 1532)) "
//                + "(CONNECT_DATA=(SERVICE_NAME="
//                + "UATC"
//                + ".agriculture.gov.ie))(HS=OK))";
	    
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
	    dataSource.setUrl( databaseUrl );
	    dataSource.setUsername(databaseUser);
	    dataSource.setPassword(databasePassword);


	    return dataSource;
	}	
	
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
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
