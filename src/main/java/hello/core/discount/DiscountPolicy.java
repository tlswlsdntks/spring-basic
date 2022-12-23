package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    /**
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);

    /*
        [빈 등록]
        업무로직(화면개발): MVC 일반화 -> @Component 자동등록
        기술로직(기술지원): 광범위 -> AppConfig.class처럼 수동등록
         -> 자동등록을 원하면 특정패키지에서 작성

        [정리]
        편리한 자동기능을 기본으로 사용
        직접등록하는 기술지원객체는 수동등록
        다형성을 적극 활용하는 비즈니스 로직은 수동등록을 고민해보자
     */
}
