package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 공연 기획자


// Spring
@Configuration
public class AppConfig {

    // 리펙터링 : 구성정보에서 역할과 구현을 명확하게 분리
    // 역할이 잘 들어남

    // new MemberRepository : 중복제거


    // Spring

    // @Bean memberService() -> new MemoryMemberRepository()
    // @Bean orderService() -> new MemoryMemberRepository()


    // [예상]
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
    // call AppConfig.memberRepository
    // call AppConfig.memberRepository


    // [결과]
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
//        return new OrderServiceImpl(memberRepository(), discountPolicy());
        return null;
    }


    @Bean
    public DiscountPolicy discountPolicy() {
//        return new FixDiscountPolicy(); // 정액
        return new RateDiscountPolicy(); // 정률
    }

}
