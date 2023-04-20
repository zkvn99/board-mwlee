package idusw.springboot.boradmwlee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  //(exclude = DataSourceAutoConfiguration.class)
public class BoardMwleeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardMwleeApplication.class, args);
    }

}
