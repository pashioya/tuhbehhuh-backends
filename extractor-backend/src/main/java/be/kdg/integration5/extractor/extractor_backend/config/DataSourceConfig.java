package be.kdg.integration5.extractor.extractor_backend.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.text.MessageFormat;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource getDataSource() {
        var url = MessageFormat.format(
                "jdbc:mysql://{0}:{1}/{2}?useSSL=true",
                System.getenv("EXTRACTOR_MYSQL_HOST"), System.getenv("EXTRACTOR_DB_PORT"), System.getenv("EXTRACTOR_MYSQL_DATABASE")
        );
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(url)
                .username(System.getenv("EXTRACTOR_MYSQL_USER"))
                .password(System.getenv("EXTRACTOR_MYSQL_PASSWORD"))
                .build();
    }
}
