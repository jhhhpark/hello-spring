package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    // 비즈니스 로직을 검증 할 때에는 예외 처리가 잘 되는지도 검증하는 것이 중요하다.
    @Test
    public void 회원가입() {
        // given (무언가가 주어졌는데)
        Member member = new Member();
        member.setName("홍길동");

        // when (이런 일이 생겼을 때)
        Long saveId = memberService.join(member);

        // then (결과가 이런것이 나와야 해)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
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
        // 예외 처리를 받는 검증 메소드
        // 2번째 람다식이 예외를 발생시켜서 첫번째 인자에 해당하는 예외가 발생해야 성공!
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(dupMember));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }


    @Test
    public void 회원전체조회() {
        // given
        Member member1 = new Member();
        member1.setName("홍길동");

        Member member2 = new Member();
        member2.setName("김아무개");

        // when
        memberService.join(member1);
        memberService.join(member2);

        // then
        assertThat(memberService.findMembers().size()).isEqualTo(2);
    }

    @Test
    public void 회원조회() {
        // given
        Member member1 = new Member();
        member1.setName("홍길동");

        Member member2 = new Member();
        member2.setName("김아무개");

        // when
        memberService.join(member1);
        Long saveId = memberService.join(member2);

        // then
        Member result = memberService.findOne(saveId).get();
        assertThat(result).isEqualTo(member2);
    }
}