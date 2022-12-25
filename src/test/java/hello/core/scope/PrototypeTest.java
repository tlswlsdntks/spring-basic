package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeTest {

    @Test
    public void prototypeBeanFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();
        // @Scope("prototype")
        // @PreDestroy -> destroy() 호출이 안된다

        // 수동으로 호출은 가능하다
        // prototypeBean1.destroy();
        // prototypeBean2.destroy();
    }

    /*
        [프로토타입 빈의 특징 정리]
        스프링 컨테이너에 요청할때마다 새로 생성된다
        스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입, 초기화까지만 관여한다
        -> 종료메소드를 호출하지 않는다

        그래서 프로토타입 빈은 프로토타입 빈을 조회한 클라이언트가 관리해야한다
        종료메소드에 대한 호출도 클라이언트가 직접 호출 해야한다
     */

    @Scope("prototype")
    // new AnnotationConfigApplicationContext(PrototypeBean.class);
    // 로 지정해주면 @Component 대상 자체처럼 동작한다
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }

    }

}
