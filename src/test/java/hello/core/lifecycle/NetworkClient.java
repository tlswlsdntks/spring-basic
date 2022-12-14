package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient {



    /*
        [implements InitializingBean, implements DisposableBean]
        1) implements InitializingBean . afterPropertiesSet()
        의존관계 주입(빈 등록)이 끝나면 호출

        1-1) implements DisposableBean . destroy()
        종료

        [초기화, 소멸 인터페이스 단점]
        1) 스프링 전용 인터페이스
        2) 초기화, 소멸 메소드 이름 변경 불가능
        3) 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다

        거의 사용하지 않는다!
     */

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public NetworkClient(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시, 호출
    public void connect() {
        System.out.println("connect = " + url);
    }

    public void call(String message) {
        System.out.println("call = " + url + ", message = " + message);
    }

    // 서비스 종료 시, 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    /*
        // 의존관계 주입이 끝나면 호출
        @Override
        public void afterPropertiesSet() throws Exception {
            System.out.println("NetworkClient.afterPropertiesSet");
            connect();
            call("초기화 연결 메세지");
        }

        @Override
        public void destroy() throws Exception {
            System.out.println("NetworkClient.destroy");
            disconnect();
        }
     */



//    public void init() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메세지");
//    }
//
//    public void close() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }

    /*
        [설정 정보 사용 특징]
        1) 스프링에 의존하지않는다
        2) 설정 정보를 사용하기 때문에 코드를 고칠 수 없는
        3) 외부 라이브러리에도 초기화, 종료 메소드를 적용할 수 있다
     */


    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }

    /*
        [@PostConstruct, @PreDestroy]
        @PostConstruct: 생성된 이후에
        @PreDestroy: 소멸되기 전에

        import javax.annotation.PostConstruct;
        import javax.annotation.PreDestroy;
        JSR-250이라는 자바표준, 스프링이 아닌 다른 컨테이너에서도 동작한다
        @ComponentScan 과 어울린다 -> @Bean 등록이 아니기 때문에

        유일한 단점
        -> 외부 라이브러리에는 적용하지 못한다
        -> 외부 라이브러리를 초기화, 종료해야하면
        @Bean(initMethod = "init", destroyMethod = "close")기능을 사용하자

     */


}
