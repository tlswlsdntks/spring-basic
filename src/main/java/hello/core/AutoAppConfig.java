package hello.core;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION
                , classes = Configuration.class)
        // AppConfig.class에 @Configuration이 붙어있으니까 지워준다
        // ComponentScan의 중첩을 피함

)
public class AutoAppConfig {


}
