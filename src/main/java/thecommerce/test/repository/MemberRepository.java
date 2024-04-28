package thecommerce.test.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thecommerce.test.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    Optional<Member> findByUserId(String userId);
    Optional<Member> findByEmail(String email);
    Page<Member> findAll(Pageable pageable);
    Member update(Member member);
}
