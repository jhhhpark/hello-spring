package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {
    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        this.memberService = appConfig.memberService();
        this.orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        // given
        long memberId = 1;
        Member member = new Member(memberId, "홍길동", Grade.VIP);
        memberService.join(member);

        // when
        Order order = orderService.createOrder(memberId, "선풍기", 20000);

        // then
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
