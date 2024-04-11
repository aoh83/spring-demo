package scalable.monster.demo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

  private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class);
  }

  @Bean
  HikariConfig hikariConfig(@Value("${spring.datasource.url}") String url,
                            @Value("${spring.datasource.username}") String username,
                            @Value("${spring.datasource.password:#{null}}") Optional<String> password) {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    password.ifPresent(config::setPassword);
    config.setUsername(username);
    return config;
  }

  @Bean
  public DataSource iamDatasource(HikariConfig hikariConfig,
                                  @Value("${datasource:#{null}}") Optional<String> datasource) {
    // FIXME: this should really be a profile
    if (datasource.orElseGet(() -> "real").equals("real")) {
      return new IamDatasource(hikariConfig);
    }
    return new HikariDataSource(hikariConfig);
  }

  @Bean
  public CommandLineRunner demo(CustomerRepository repository) {
    return (args) -> {
      // bootstrap repository with fake customers
      repository.save(new Customer("Jack", "Bauer"));
      repository.save(new Customer("Chloe", "O'Brian"));
      repository.save(new Customer("Kim", "Bauer"));
      repository.save(new Customer("David", "Palmer"));
      repository.save(new Customer("Michelle", "Dessler"));
    };
  }
}
