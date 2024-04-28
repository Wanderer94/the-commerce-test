package thecommerce.test.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import thecommerce.test.domain.Member;
import thecommerce.test.repository.MemberRepository;
import thecommerce.test.repository.MemoryMemberRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntergrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("12345678!");
        member.setEmail("test@thecommerce.com");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());


    }

    @Test
    public void validateDuplicateUserId() {
        //given
        Member member1 = new Member();
        member1.setUserId("test");
        member1.setPassword("12345678!");
        member1.setEmail("test1@thecommerce.com");

        Member member2 = new Member();
        member2.setUserId("test");
        member2.setPassword("12345678!");
        member2.setEmail("test2@thecommerce.com");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디 입니다.");
    }

    @Test
    public void validatePassword() {
        //given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("1234");
        member.setEmail("test@thecommerce.com");

        //when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> memberService.join(member));

        //then
        assertThat(e.getMessage()).isEqualTo("비밀번호는 최소 8자 이상이어야 하고, 특수문자를 포함해야 합니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}