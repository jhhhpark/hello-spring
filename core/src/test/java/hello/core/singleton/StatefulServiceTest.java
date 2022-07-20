package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    @DisplayName("스테이트가 변경되는 필드를 가진 싱글톤 객체 테스트")
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService_1 = ac.getBean(StatefulService.class);
        StatefulService statefulService_2 = ac.getBean(StatefulService.class);

        /*
        시나리오 : 사용자 A가 10000원에 주문을 하고 가격을 확인하는데
        그 사이에 사용자 B가 20000원에 주문을 해버렸다. 이러면 price는 20000원이 되었으므로
        잘못된 싱글턴 객체 설계이다.
         */
        // ThreadA : A 사용자가 10000원 주문
        int userAPrice = statefulService_1.order("userA", 10000);

        // ThreadB : B 사용자가 20000원 주문
        int userBPrice = statefulService_2.order("userB", 20000);

        // ThreadA : 사용자A 주문 금액 조회
        System.out.println("price = " + userAPrice);

        Assertions.assertThat(userAPrice).isEqualTo(10000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}