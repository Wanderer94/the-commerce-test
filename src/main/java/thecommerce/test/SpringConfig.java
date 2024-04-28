package thecommerce.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import thecommerce.test.repository.MemberRepository;
import thecommerce.test.repository.MemoryMemberRepository;
import thecommerce.test.service.MemberService;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
