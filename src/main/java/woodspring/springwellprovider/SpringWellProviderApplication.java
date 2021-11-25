package woodspring.springwellprovider;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWellProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWellProviderApplication.class, args);
		//SpringApplication app = new SpringApplication(SpringWellProviderApplication.class);
        //app.setDefaultProperties(Collections.singletonMap("server.port", "8181"));
        //app.run(args);
	}

}
