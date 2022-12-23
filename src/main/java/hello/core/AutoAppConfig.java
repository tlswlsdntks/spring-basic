package hello.core;


import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 탐색 위치
//        basePackages = "hello.core.member",
//        basePackages = {"hello.core.member", "hello.core.order"},
//        basePackageClasses = AutoAppConfig.class,

        /*
                [추세]
                프로젝트 시작루트에
                AppConfig 같은 메인 설정정보를 두고
                @ComponentScan을 붙여
                basePackages 지정을 생략한다
         */

        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = Configuration.class
        )
        // AppConfig.class에 @Configuration이 붙어있으니까 지워준다
        // ComponentScan의 중첩을 피함


        /*
                @Controller: MVC 컨트롤러로 인식
                @Repository: DB나 파일같은 외부 I/O 작업을 처리
                @Configuration: 스프링 설정정보로 인식, 싱글톤 유지 처리
                @Service: 로직 처리
         */
)

public class AutoAppConfig {

    /*
        [수동빈 등록이 우선권을 가진다]
        Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing [Generic bean: class [hello.core.member.MemoryMemberRepository];
        ...
        destroyMethodName=(inferred);
        defined in hello.core.AutoAppConfig]




        [스프링 부트가 자동적으로 거부하게끔 한다]

        CoreApplication.class 에서 실행
        The bean 'memoryMemberRepository', defined in class path resource [hello/core/AutoAppConfig.class], could not be registered.
        A bean with that name has already been defined in file [C:\Users\master\git\repository\spring-basic\out\production\classes\hello\core\member\MemoryMemberRepository.class] and overriding is disabled.




        ********************************************************
        spring.main.allow-bean-definition-overriding=false

        application.properties 에서
        spring.main.allow-bean-definition-overriding=true로 변경하면
        가능하게끔 실행된다
        ********************************************************
     */

//    @Bean
//    //    @Bean(name = "memoryMemberRepository")
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }

}
