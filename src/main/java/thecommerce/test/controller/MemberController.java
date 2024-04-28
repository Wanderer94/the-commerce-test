package thecommerce.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import thecommerce.test.domain.Member;
import thecommerce.test.service.MemberService;

import java.util.List;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 1. 회원가입
    @PostMapping("/api/user/join")
    public ResponseEntity<Void> joinMember(@RequestBody Member member) {
        memberService.join(member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/user/list")
    public ResponseEntity<List<Member>> list(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "joinDate") String sort,
            Model model) {
        List<Member> members = memberService.findMembers();

        return ResponseEntity.ok().body(members);
    }
}
