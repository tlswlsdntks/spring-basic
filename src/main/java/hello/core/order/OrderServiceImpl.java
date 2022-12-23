package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();


    // null -> 의존관계 주입 필요
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;



    // 1) 생성자 주입
    //  1번만 호출!
    // "불변, 필수" 의존관계에 사용
    // 생성자가 한개만 있을 땐, @Autowired 생략이 가능하다
    @Autowired // 생략 가능
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        System.out.println("memberRepository = " + memberRepository);
//        System.out.println("discountPolicy = " + discountPolicy);
//        System.out.println("1. memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }



    // 2) 수정자 주입
    // 순서: 생성자 -> 수정자
    // "선택, 변경" 가능성이 있는 의존관계에 사용
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("2. discountPolicy = " + discountPolicy);
//        this.discountPolicy = discountPolicy;
//    }
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("3. memberRepository = " + memberRepository);
//        this.memberRepository = memberRepository;
//    }



    // 3) 자바빈 프로퍼티 규약 예시
    /*
        class {
            get()
            set()
        }
     */



    // 4) 필드 주입
    // 필드주입을 권장하지 않는다!
    // 테스트 코드에서만 활용가능 -> 그래도 사용을 권장하지 않는다!
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;



    // 5) 일반 메소드 주입
    // 일반적으로 잘 사용하지 않는다
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {

        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
