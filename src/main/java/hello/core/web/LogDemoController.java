package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final MyLogger myLogger;

    /*
        Error creating bean with name 'myLogger':
        Scope 'request' is not active for the current thread;

        -> @Scope(value = "request")
        고객이 요청하고 나갈때 까지의 스코프
        스프링 시작 시점에는 에러가 생긴다!
     */

//    @Autowired
//    public LogDemoController(LogDemoService logDemoService) {
//        this.logDemoService = logDemoService;
//    }

    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {

        // http://localhost:8080/log-demo 로 입력하면
        // request.getRequestURL() 에 저장한다
        String requestURL = request.getRequestURL().toString();

        MyLogger myLogger = myLoggerProvider.getObject();

        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");

        Thread.sleep(1000);

        logDemoService.login("testId");
        return "OK";
    }

}
