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

//    private final ObjectProvider<MyLogger> myLoggerProvider;


    /*
        [프록시]
        @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
     */
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {

        // http://localhost:8080/log-demo 로 입력하면
        // request.getRequestURL() 에 저장한다
        String requestURL = request.getRequestURL().toString();

//        MyLogger myLogger = myLoggerProvider.getObject();

        System.out.println("myLogger = " + myLogger.getClass());

        /*
            프록시란? 앞에서 요청하면 대신 처리해주는 것 (위임, 간단한 조작)
            myLogger = class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$e01b0e34
            CGLIB : 스프링이 조작한것
            -> 가짜 MyLog 등록 -> 진짜 빈 요청 시, 진짜 MyLog 조회

            [정리]
            사실 Provider를 사용하든, 프록시를 사용하든
            핵심 아이디어는 진짜 객체 조회를 꼭 필요한 시점까지
            가짜로 객체로 지연처리 한다는 점이다

            마치 싱글톤을 사용하는 것 같지만
            다르게 동작하기 때문에 주의해서 사용해야하고
            이런 특별한 스코프는 필요한 부분에만 사용해야한다!
         */

        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");

        Thread.sleep(1000);

        logDemoService.login("testId");
        return "OK";
    }

}
