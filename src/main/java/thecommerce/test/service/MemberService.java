package thecommerce.test.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import thecommerce.test.domain.Member;
import thecommerce.test.repository.MemberRepository;
import thecommerce.test.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private MemberRepository memberRepository;

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


    //회원 목록 조회
    public Page<Member> findMembers(int page, int pageSize, String sort) {
        Sort.Order order = Sort.Order.asc(sort);
        PageRequest pageRequest = PageRequest.of(page -1, pageSize,Sort.by(order));
        return memberRepository.findAll(pageRequest);
    }

    // 특정 회원 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
