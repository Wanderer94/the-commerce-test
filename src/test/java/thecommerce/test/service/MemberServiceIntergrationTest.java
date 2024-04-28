package thecommerce.test.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
        // given
        // 회원 생성 및 저장
        Member member1 = new Member();
        member1.setUserId("user1");
        member1.setPassword("12345678!");
        member1.setEmail("user1@thecommerce.com");
        memberService.join(member1);

        Member member2 = new Member();
        member2.setUserId("user2");
        member2.setPassword("12345678!");
        member2.setEmail("user2@thecommerce.com");
        memberService.join(member2);

        // when
        // 페이징 정보 설정
        int page = 1; // 페이지 번호 (1부터 시작)
        int pageSize = 10; // 페이지 크기
        String sort = "joinDate"; // 정렬 기준

        Page<Member> membersPage = memberService.findMembers(page, pageSize, sort);

        // then
        // 회원이 포함된 페이지 반환 확인
        assertThat(membersPage).isNotNull();
        assertThat(membersPage.getContent().size()).isEqualTo(2); // 페이지에 회원이 2명 있어야 함
        assertThat(membersPage.getTotalElements()).isEqualTo(2); // 전체 회원 수가 2명이어야 함
        assertThat(membersPage.getTotalPages()).isEqualTo(1); // 전체 페이지 수가 1페이지여야 함 (페이지 크기가 10이므로)
    }

    @Test
    void findOne() {
        // given
        // 회원 생성 및 저장
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("12345678!");
        member.setEmail("test@thecommerce.com");
        Long memberId = memberService.join(member);

        // when
        // 회원 조회
        Member foundMember = memberService.findOne(memberId).orElse(null);

        // then
        // 조회된 회원과 저장한 회원이 동일한지 확인
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getUserId()).isEqualTo(member.getUserId());
        assertThat(foundMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    void updateMember() {
        // given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("12345678!");
        member.setEmail("test@thecommerce.com");
        Long memberId = memberService.join(member);

        // 새로운 멤버 정보 생성
        Member updatedMember = new Member();
        updatedMember.setUserId("test"); // 동일한 아이디로 업데이트
        updatedMember.setPassword("newPassword123!"); // 새로운 비밀번호
        updatedMember.setEmail("updated_email@thecommerce.com"); // 업데이트된 이메일

        // when
        memberService.update(member,updatedMember);

        // then
        // 업데이트된 멤버 정보 확인
        Member foundMember = memberService.findOne(memberId).orElse(null);
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getUserId()).isEqualTo(updatedMember.getUserId()); // 아이디가 변경되지 않았는지 확인
        assertThat(foundMember.getPassword()).isEqualTo(updatedMember.getPassword()); // 비밀번호가 업데이트되었는지 확인
        assertThat(foundMember.getEmail()).isEqualTo(updatedMember.getEmail()); // 이메일이 업데이트되었는지 확인
    }

    @Test
    void updateMember_Empty() {
        // given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("12345678!");
        member.setEmail("test@thecommerce.com");
        Long memberId = memberService.join(member);

        // 새로운 멤버 정보 생성 (비어있는 필드)
        Member updatedMember = new Member();
        updatedMember.setUserId("test"); // 동일한 아이디로 업데이트
        updatedMember.setEmail(""); // 비어있는 이메일 설정

        // when
        memberService.update(member, updatedMember);

        // then
        // 업데이트된 멤버 정보 확인
        Member foundMember = memberService.findOne(memberId).orElse(null);
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail()); // 비어있는 이메일 필드가 업데이트되지 않았는지 확인
    }

    @Test
    void updateMember_NotExist() {
        // given
        Member member = new Member();
        member.setUserId("test");
        member.setPassword("12345678!");
        member.setEmail("test@thecommerce.com");

        Member newMember = new Member();
        member.setUserId("nonExistingUser");
        member.setPassword("12345678!");
        member.setEmail("nonexisting@thecommerce.com");

        // when, then
        // 존재하지 않는 회원을 업데이트하려고 시도하면 예외가 발생해야 함
        assertThrows(IllegalArgumentException.class, () -> memberService.update(member,newMember));
    }

    @Test
    void updateMember_withInvalidPassword() {
        // given
        Member existingMember = new Member();
        existingMember.setUserId("existingUser");
        existingMember.setPassword("12345678!");
        existingMember.setEmail("existing@thecommerce.com");
        Long memberId = memberService.join(existingMember);

        // 새로운 멤버 정보 생성 (비밀번호가 적합하지 않은 경우)
        Member updatedMember = new Member();
        updatedMember.setUserId("existingUser"); // 동일한 아이디로 업데이트
        updatedMember.setPassword("1234"); // 적합하지 않은 비밀번호
        updatedMember.setEmail("updated_email@thecommerce.com"); // 이메일은 유효한 값으로 설정

        // when, then
        // 적합하지 않은 비밀번호로 업데이트를 시도하면 예외가 발생해야 함
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> memberService.update(existingMember, updatedMember));

        // 예외 메시지가 올바른지 확인
        assertThat(e.getMessage()).isEqualTo("비밀번호는 최소 8자 이상이어야 하고, 특수문자를 포함해야 합니다.");
    }
}