package thecommerce.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import thecommerce.test.domain.Member;
import thecommerce.test.service.MemberService;

import java.util.List;

@Controller
public class MemberContraller {

    private MemberService memberService;

    @Autowired
    public MemberContraller(MemberService memberService) {
        this.memberService = memberService;
    }

    // 1. 회원가입
    @PostMapping("/api/user/join")
    public ResponseEntity<Void> joinMember(@RequestBody Member member) {
        memberService.join(member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/user/list")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
