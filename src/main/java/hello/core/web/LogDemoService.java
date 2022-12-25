package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    //    private final MyLogger myLogger;

    /*
        Error creating bean with name 'myLogger':
        Scope 'request' is not active for the current thread;

        -> @Scope(value = "request")
        고객이 요청하고 나갈때 까지의 스코프
        스프링 시작 시점에는 에러가 생긴다!
     */

    private final ObjectProvider<MyLogger> myLoggerProvider;

    public void login(String id) {

        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id = " + id);
    }
}
