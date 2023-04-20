package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Memo;
import idusw.springboot.boradmwlee.service.MemoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/memo")
// @RequestMapping("/api")
public class MemoController {
    // 생성자 주입 (Constructor DI)
    MemoService memoService;

    public MemoController(MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping("/init")
    public String initialize(Model model) {
        List<Memo> result = new ArrayList<>();
        result = memoService.initialize();
        model.addAttribute("attr", result);
        return "/memo/list";
    }

    @GetMapping("/tables") // 자원을 접근하는 개념
    public String getTables() {
        return "/memo/tables";
    }

    @GetMapping("/")
    public String getList(Model model) {
        List<Memo> result = new ArrayList<>();
        result = memoService.readList(); // 여기를 수정함
        model.addAttribute("attr", result);
        return "/memo/list";
    }

    @GetMapping("/{mno}")
    public String getList(@PathVariable("mno") Long mno, Model model) {
        Memo result = new Memo();
        Memo m = new Memo();
        m.setMno(mno);
        result = memoService.read(m); // 여기를 수정함
        model.addAttribute("attr", result);
        return "/memo/one";
    }
}
