package thecommerce.test.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thecommerce.test.domain.Member;
import thecommerce.test.service.MemberService;

import java.util.*;

@RestController
@Tag(name = "Member", description = "Member API")
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

    // 2. 회원 목록 조회
    @GetMapping("/api/user/list")
    public ResponseEntity<List<Member>> list(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "sort", defaultValue = "joinDate") String sort,
            Model model) {
        Page<Member> membersPage = memberService.findMembers(page, pageSize, sort);

        List<Member> members = membersPage.getContent();
        long totalElements = membersPage.getTotalElements();
        int totalPages = membersPage.getTotalPages();

        Map<String, Object> response = new HashMap<>();
        response.put("members", members);
        response.put("totalElements", totalElements);
        response.put("totalPages", totalPages);

        return ResponseEntity.ok().body(members);
    }

    // 3. 회원 수정
    @PostMapping("/api/user/{userid}")
    public ResponseEntity<Void> updateMember(@PathVariable("userid") Long userId, @RequestBody Member member){
    // userId를 사용하여 해당 회원을 조회하여 업데이트
        Optional<Member> optionalMember = memberService.findOne(userId);
        if (!optionalMember.isPresent()) {
            // 해당 userId로 회원을 찾을 수 없는 경우
            return ResponseEntity.notFound().build();
        }

        Member existingMember = optionalMember.get();

        // memberDetails에서 가져온 필드로 기존 회원 정보를 업데이트
        if (member.getName() != null) {
            existingMember.setName(member.getName());
        }
        if (member.getEmail() != null) {
            existingMember.setEmail(member.getEmail());
        }

        // 회원 정보를 업데이트
        memberService.update(member, existingMember);
        // 업데이트가 성공적으로 완료되었음을 클라이언트에게 응답
        return ResponseEntity.ok().build();
    }
}
