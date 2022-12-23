package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void BeanLifeCycleTest() {
        // 빈 설정등록
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        // 빈 조회
        NetworkClient client = ac.getBean(NetworkClient.class);

        ac.close();


    }

    @Configuration
    static class LifeCycleConfig {

//        @Bean
        @Bean(initMethod = "init", destroyMethod = "close")
        /*
            [종료 메소드 추론]
            default: destroyMethod = "(inferred)";
            라이브러리는 대부분 "close", "shutdown" 이라는 이름의 메소드를 사용
            "(inferred)" -> 추론
            종료메소드를 추론해서 호출해준다
            종료메소드를 따로 적어주지 않아도 잘 동작한다!

            추론 기능 사용하지 않겠다면
            destroyMethod = "";

         */
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            System.out.println("@Bean Start");
            networkClient.setUrl("http://hello-spring.dev/");
            System.out.println("@Bean End");
            return networkClient;
        }
    }

    /*
        [스프링 빈의 이벤트 라이프 사이클]
        스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입
            -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료

        객체의 생성(New 생성자 정보 받기, 메모리 할당) 과
        초기화(커넥션 연결 등의 동작)를 분리하자
     */


}
