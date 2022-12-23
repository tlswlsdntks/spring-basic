package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {



    /*
        [implements InitializingBean, implements DisposableBean]
        1) implements InitializingBean . afterPropertiesSet()
        의존관계 주입(빈 등록)이 끝나면 호출

        1-1) implements DisposableBean . destroy()
        종료

        [초기화, 소멸 인터페이스 단점]
        스프링 전용 인터페이스
        초기화, 소멸 메소드 이름 변경 불가능
        내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다

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
}
