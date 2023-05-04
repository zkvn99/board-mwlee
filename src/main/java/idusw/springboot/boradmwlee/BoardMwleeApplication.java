package idusw.springboot.boradmwlee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication  //(exclude = DataSourceAutoConfiguration.class)
// @Configuration : 해당 클래스가 설정 클래스 임을 Spring Framework 에게 알림
@EnableJpaAuditing    // JPA Auditing을 활성화함
public class BoardMwleeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardMwleeApplication.class, args);
    }
    @Bean   //메소드를 호출하여 Bean 객체를 생성
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {    //put, delete 처리
        return new HiddenHttpMethodFilter();
    }
}
