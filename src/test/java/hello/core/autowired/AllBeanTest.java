package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        DiscountService discountService = ac.getBean(DiscountService.class);
        // 실제 값으로 제공된 개체(discountService)가
        // 예상 유형의 인스턴스(DiscountService.class)인 경우 성공
        assertThat(discountService).isInstanceOf(DiscountService.class);

        Member member = new Member(1l, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fix");
        assertThat(discountPrice).isEqualTo(1000); // 정액: 1000

        int rateDiscountPrice = discountService.discount(member, 20000, "rate");
        assertThat(rateDiscountPrice).isEqualTo(2000); // 정률: 10%

    }


    static class DiscountService {

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        // @Autowired: 생략 가능
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }
        
        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }
}
