package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    MemoService memoService;  // MemoService 인터페이스의 구현체를 필드 주입

    @GetMapping
    public String getHome() {
        return "redirect:/admin";
    }
    @GetMapping("admin")
    public String getAdmin() {
        System.out.println("getAdmin");
        return "/admin/index";
    }
    @GetMapping("buttons") // 자원을 접근하는 개념 , /admin/buttons
    public String getButtons() {
        return "/admin/buttons";
    }

    @GetMapping("cards") // 자원을 접근하는 개념
    public String getCards() {
        return "/admin/cards";
    }
}
