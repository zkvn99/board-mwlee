package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/members")
public class MemberController {
    // 생성자 주입
    MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    private HttpSession session;
    @RequestMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("member", Member.builder().build()); // 폼에서의 요청을 전달할 DTO 객체를 생성
        //memberService.toString();
        return "/members/login";
    }
    @PostMapping("/login")
    public String loginMember(@ModelAttribute("member") Member m, Model model, HttpServletRequest request) {
       // @ModelAttribute : 요청으로 전달된 객체 (폼에서 입력한 정보를 갖는) , email, pw
        Member result = memberService.login(m);
        if (result != null) {
            if (!result.isAbandon()) {
                session = request.getSession();
                session.setAttribute("mb", result);
                return "redirect:/"; // 로그인 성공 시 메인 페이지로 리다이렉트
            } else {
                String message = "정지된 아이디입니다.";
                model.addAttribute("message", message);
                model.addAttribute("alert", true); // 알림 창을 표시하기 위한 플래그 추가
                return "/members/login"; // 로그인 페이지로 이동
            }
        } else {
            return "redirect:/members/register";
        }
    }
    @GetMapping("/logout")
    public String logoutMember() {
        session.invalidate(); // session 객체 무효화, 저장된 속성도 사라짐
        return "redirect:/";
    }
   /*
    @RequestMapping("/")
    public String getMemberList(Model model) {
        // 1. 매개변수를 받아 기본 작업하고, 2. 서비스에게 요청을 전달 - readList() 가 처리 후 반환
        // 3. 결과를 view에 전달
        List<Member> memberList = new ArrayList<>(); // 결과를 받을 객체
        if((memberList = memberService.readList()) != null) {
            model.addAttribute("list", memberList);
            return "members/list";
        } else {
            model.addAttribute("error message", "목록 조회에 실패, 권한 확인");
            return "/members/message";
        }
    }
*/
    /*
   @GetMapping(value = {"", "/{pn}/{size}"}) // /?pn=&size=
   public String listMemberPagination(@PathVariable("pn") int pn, @PathVariable("size") int size, Model model) { */
    @GetMapping(value = {"", "/"}) // ?page=&perPage=
    public String listMemberPagination(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "perPage", required = false, defaultValue = "10") int perPage,
                                       @RequestParam(value = "perPagination", required = false, defaultValue = "5") int perPagination,
                                       @RequestParam(value = "type", required = false, defaultValue = "e") String type,
                                       @RequestParam(value = "keyword", required = false, defaultValue = "@") String keyword,
                                       Model model, HttpServletRequest request) {
        session = request.getSession();
        Member member = (Member) session.getAttribute("mb");
        if(member == null)
            return "/404";
        if(member.getEmail().equals("root201912012@induk.ac.kr"))
        {
            PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                    .page(page)
                    .perPage(perPage)
                    .perPagination(perPagination)
                    .type(type)
                    .keyword(keyword)
                    .build();
            PageResultDTO<Member, MemberEntity> resultDTO = memberService.getList(pageRequestDTO);
            if(resultDTO != null) {
                model.addAttribute("result", resultDTO);
                return "/members/list"; // view : template engine - thymeleaf .html
            } else
                return "/404";
        }
        else
            return "redirect:/";
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
        if (memberService.isEmailDuplicated(m.getEmail())) {
            // 중복 이메일이 존재하는 경우
            model.addAttribute("errorMessage", "중복된 이메일입니다. 다른 이메일을 사용해주세요.");
            return "/members/register";
        } else {
            if (memberService.create(m) > 0)
                return "redirect:/members/login"; // 회원가입이 성공한 경우 로그인 페이지로 리다이렉트
            else
                return "redirect:/members/register";
        }
    }

    //@RequestMapping(value ="/forgot", method = RequestMethod.GET)

    @GetMapping("/forgot")
    public String getForgotForm() {
        //memberService.toString();
        return "/members/forgot-password";
    }

    @GetMapping("/{seq}")
    public String getMember(@PathVariable("seq") Long seq, Model model) {
        Member result = new Member(); // 반환
        Member m= new Member(); // 매개변수로 전달
        m.setSeq(seq);
        result = memberService.read(m);
        // MemberService가 MemberRepository에게 전달
        // MemberRepository는 JpaRepository 인터페이스의 구현체를 활용할 수 있음
        model.addAttribute("member", result);
        return "members/detail";
    }
    @PostMapping("/{seq}/ban")
    public String banMember(@PathVariable("seq") Long seq) {
        memberService.banMember(seq);
        return "redirect:/members";
    }


    @PutMapping("/{seq}") // @PostMapping("/{seq}/update")
    public String updateMember(@ModelAttribute("member") Member member, Model model) { // 수정 처리 -> service -> repository -> service -> controller
        if(memberService.update(member) > 0 ) {
            session.setAttribute("mb", member);
            return "redirect:/";
        }
        else
            return "/404";
    }
    @DeleteMapping("/{seq}") // @PostMapping("/{seq}/delete")
    public String deleteMember(@ModelAttribute("member") Member member) { // 삭제 처리 -> service -> repository -> service -> controller
        if(memberService.delete(member) > 0) {
            session.invalidate();
            return "redirect:/";
        }
        else
            return "/404";
    }

    @PostMapping("/check-email")
    @ResponseBody
    public int checkEmail(@RequestParam("email") String email) {
        Member member = Member.builder().email(email).build();
        int cnt = memberService.checkEmail(member);
        System.out.println("check-email" + email + " : " + cnt);
        return cnt;
    }

}

