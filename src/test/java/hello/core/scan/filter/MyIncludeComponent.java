package hello.core.scan.filter;


import java.lang.annotation.*;

@Target(ElementType.TYPE) // TYPE == class
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
