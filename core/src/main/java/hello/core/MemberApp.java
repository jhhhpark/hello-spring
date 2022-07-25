package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberServiceImpl.class);

        Member member = new Member(1L, "홍길동", Grade.VIP);
        // 회원가입
        memberService.join(member);

        // 회원조회
        Member result = memberService.findMember(1L);

        // 결과확인
        System.out.println("이름 : " + result.getName() + ", 등급 : " + result.getGrade());

    }
}
