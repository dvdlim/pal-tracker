package io.pivotal.pal.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class PalTrackerApplication {

    /*@Bean
    public TimeEntryRepository getRepository(){
        TimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();
        return  timeEntryRepository;
    }*/

    @Bean
    public TimeEntryRepository getRepository(@Value("${SPRING_DATASOURCE_URL}") String datasourceUrl){
        TimeEntryRepository jdbcTimeEntryRepository = new JdbcTimeEntryRepository(newJdbcDatasource(datasourceUrl));
        return  jdbcTimeEntryRepository;
    }

    public MysqlDataSource newJdbcDatasource(String url)
    {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);

        return dataSource;
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .modules(new JavaTimeModule())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }
}