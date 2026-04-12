package pe.inpe.ms_internos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsInternosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsInternosApplication.class, args);
	}

}
