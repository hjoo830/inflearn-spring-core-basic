package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     * 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존하고 있다.
     * 추상(인터페이스) 의존: `DiscountPolicy`
     * 구체(구현) 클래스: `FixDiscountPolicy` , `RateDiscountPolicy`
     */

    /**
     * `FixDiscountPolicy` 를 `RateDiscountPolicy` 로 변경하는 순간
     * `OrderServiceImpl` 의 소스 코드도 함께 변경해야 한다!
     * -> OCP 위반
     */

//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
