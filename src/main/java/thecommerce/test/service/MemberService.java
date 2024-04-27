package thecommerce.test.service;

import thecommerce.test.domain.Member;
import thecommerce.test.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    //회원 가입
    public Long join(Member member) {
        // 1. 동일 아이디를 가지고 있는 중복 회원 가입 불가
        validateDuplicateUserId(member);    // 중복 아이디 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateUserId(Member member) {
        memberRepository.findByUserId(member.getUserId())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        });
    }

    //전체 회원 조회
    public List<Member> findMembers() {

    }

    // 특정 회원 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId)
    }
}
