package hello.core.singleton;

import hello.core.member.MemberService;
import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService_1 = appConfig.memberService();

        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService_2 = appConfig.memberService();

        // memberService1 != memberService2
        Assertions.assertThat(memberService_1).isNotSameAs(memberService_2);
    }

    @Test
    @DisplayName("싱글턴 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService_1 = SingletonService.getInstance();
        SingletonService singletonService_2 = SingletonService.getInstance();

        Assertions.assertThat(singletonService_1).isSameAs(singletonService_2);
        // same == 실제 자바 객체의 할당된 값이 같은지 비교
        // equal == equals 메소드를 오버라이드 가능, 그것을 호출
    }
}
