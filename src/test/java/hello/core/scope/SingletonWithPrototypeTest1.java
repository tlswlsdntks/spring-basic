package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    public void prototypeBean() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
//        assertThat(count2).isEqualTo(2);
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton") // default: @Scope("singleton")
    static class ClientBean {
//        private final PrototypeBean prototypeBean; // 생성시점에 주입

        /*
            -> 프로토타입빈은 생성시점 마다 주입이 되어야하는데
            주입된 걸 다시 주입하게 된다 -> 의도와는 다르다 -> 새로 생성해서 사용해야함
         */

////        @Autowired: 생략가능 -> 생성자가 하나이므로
////        @RequiredArgsConstructor -> 클래스 위에다 이 메소드 하나면 해결가능
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }


        // 싱글톤과 프로토타입 같이 사용하기위한 해결책
        // ObjectProvider<PrototypeBean>
        // ObjectProvider<PrototypeBean>
        // 둘이 같지만 ObjectProvider 가 기능이 더 많이 추가됐다
        @Autowired
//        private ObjectFactory<PrototypeBean> prototypeBeanProvider;
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        // 스프링에 의존적이지 않는 jsr-330
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {

            // [DL: 의존관계 조회(탐색)]
            // prototypeBeanProvider.getObject();
            // 스프링컨테이너에서 프로토타입 빈을 찾아주서 주는 기능
            // -> 하지만 스프링에 의존적이다
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();

            // 스프링에 의존적이지 않는 jsr-330
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();


            /*
                [정리]
                프로토타입 빈을 언제 사용할까?
                매번 사용할 때마다
                의존관계 주입이 완료된 새로운 객체가 필요하면 사용하면 된다!

                그러나 실무에서는 거의 드물다!
             */


        }

    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("prototypeBean.init = " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("prototypeBean.destroy");
        }

    }
}
