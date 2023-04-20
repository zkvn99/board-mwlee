package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/members")
public class MemberController {
    // 생성자 주입
    MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping("/login")
    public String getLoginForm() {
        //memberService.toString();
        return "/members/login";
    }
    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        // Member 형의 객체를 생성하고,
        model.addAttribute("member", Member.builder().build());
        //memberService.toString();
        return "/members/register"; // register.html, view resolving
    }
    @PostMapping("/register")
    public String registerMember(@ModelAttribute("member") Member m, Model model) {
        if(memberService.create(m) > 0 )
            return "redirect:/members/login"; // 홈으로 재지정함 : 컨트롤러에게 재지정
        else
            return "redirect:/members/register";
    }
    //@RequestMapping(value ="/forgot", method = RequestMethod.GET)

    @GetMapping("/forgot")
    public String getForgotForm() {
        //memberService.toString();
        return "/members/forgot-password";
    }

}
