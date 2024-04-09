package scalable.monster.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {


  // uncomment if you need normal postgres support
  @Bean
  public IamDatasource iamDatasource() {
    return new IamDatasource();
  }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
