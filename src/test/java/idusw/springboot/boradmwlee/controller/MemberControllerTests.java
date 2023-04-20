package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MemberControllerTests {
    @Autowired
    MemberService memberService;
    @Test
    void contextLoads() {
        List<Member> result = memberService.readList();
        for(Member m : result)
            System.out.println(m.getEmail());
    }
}
