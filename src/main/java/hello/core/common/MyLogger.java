package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
//@Scope("request")
/*
    HTTP request 요청 당 각각 할당되는 request 스코프
 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
/*
    클래스: ScopedProxyMode.TARGET_CLASS
    인터페이스: ScopedProxyMode.INTERFACES
 */

public class MyLogger {

    private String uuid;
    private String requestURL;

    // requestURL 은 이 빈이 생섬되는 시점에는 알 수 없으므로,
    // 외부에서 setter로 입력 받는다
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    }
    
    @PostConstruct // 빈 생성시 자동으로 @PostConstruct 실행 -> 랜덤값을 uuid에 저장
    public void init(){
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope been create: " + this);
    }

    @PreDestroy // request 스코프는 @PreDestroy 호출 가능
    public void close(){
        System.out.println("[" + uuid + "] request scope been close: " + this);
    }
}
