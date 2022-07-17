package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        // given (무언가가 주어졌는데)
        Member member = new Member();
        member.setName("홍길동");

        // when (이런 일이 생겼을 때)
        Long saveId = memberService.join(member);

        // then (결과가 이런것이 나와야 해)
        Member findName = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findName.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member = new Member();
        member.setName("홍길동");

        Member dupMember = new Member();
        dupMember.setName("홍길동");

        // when
        memberService.join(member);

        // then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(dupMember));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}