package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.repository.MemberRepository;
import idusw.springboot.boradmwlee.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberControllerTests {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;
    @Test
    void contextLoads() {
        List<Member> result = memberService.readList();
        for(Member m : result)
            System.out.println(m.getEmail());
    }

    @Test
    void initializeMember() {
        // Integer 데이터 흐름, Lambda 식 - 함수형 언어의 특징을 활용
        IntStream.rangeClosed(1, 101).forEach(i -> {
            MemberEntity member = MemberEntity.builder()
                    .seq(Long.valueOf(i))
                    .email("email" + i + "@induk.ac.kr")
                    .pw("pw" + i)
                    .name("name" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void testPageList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(5).size(10).build();
        PageResultDTO<Member, MemberEntity> resultDTO = memberService.getList(pageRequestDTO);
        for(Member member : resultDTO.getDtoList())
            System.out.println(member);

        System.out.println("Prev : " + resultDTO.isPrev()); // PerPagination = 4인경, 1 - 4 , 5 - 8, 9 - 12
        System.out.println("Next : " + resultDTO.isNext());
        System.out.println("Total Page : " + resultDTO.getTotalPage());
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}