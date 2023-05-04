package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Member;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    @GetMapping("/register-form")
    public String getCreate(Model model) { //register 호출
        String result = "form load";
        return result;
    }
    @PostMapping("/")
    public String createMember(@RequestBody Member member, Model model) { //register 호출
        String result = "post";
        return result;
    }
    @GetMapping("/")
    public String listMember() {
        String result = "list";
        return result;
    }
    @GetMapping("/{id}")
    public String readMember(@PathVariable Long id, Model model) {
        String result = "read" + id;
        return result;
    }
    @PutMapping("/{id}")
    public String updateMember(@PathVariable Long id, Model model) {
        String result = "update";
        return result;
    }
    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable Long id, Model model) {
        String result = "delete";
        return result;
    }
}
