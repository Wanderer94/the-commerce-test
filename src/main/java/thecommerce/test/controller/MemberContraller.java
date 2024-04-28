package thecommerce.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/api/user/join")
    public String cretaeForm() {
        return "";
    }

    @GetMapping("/api/user/list")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
