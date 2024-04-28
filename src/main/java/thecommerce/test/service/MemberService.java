package thecommerce.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thecommerce.test.domain.Member;
import thecommerce.test.repository.MemberRepository;
import thecommerce.test.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //회원 가입
    public Long join(Member member) {
        // 1. 동일 아이디를 가지고 있는 중복 회원 가입 불가
        validateDuplicateUserId(member);    // 중복 아이디 검증
        // 2. 비밀번호가 특정 조건을 만족하는지 확인
        validatePassword(member.getPassword()); // 비밀번호 검증
        // 3. 이메일이 중복되는지 확인
        validateDuplicateEmail(member.getEmail()); // 중복 이메일 검증


        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateUserId(Member member) {
        memberRepository.findByUserId(member.getUserId())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        });
    }
    private void validatePassword(String password) {
        // 비밀번호가 특정 조건을 만족하는지 확인하는 로직 작성
        // 예를 들어, 최소 길이가 8자 이상이고 특수문자를 포함하는지 검증하는 코드를 작성할 수 있음
        if (password.length() < 8 || !password.matches(".*[!@#$%^&*()].*")) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 하고, 특수문자를 포함해야 합니다.");
        }
    }
    private void validateDuplicateEmail(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 이메일 주소입니다.");
                });
    }


    //전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 특정 회원 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
