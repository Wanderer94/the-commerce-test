package thecommerce.test.repository;

import thecommerce.test.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    Optional<Member> findByUserId(String userId);

    List<Member> findAll();
}
