package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.repository.MemberRepository;
import idusw.springboot.boradmwlee.service.MemberService;
import jakarta.transaction.Transactional;
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
                    .email("email" + i + "@induk.ac.kr")
                    .pw("pw" + i)
                    .name("name" + i)
                    .phone("01050940"+i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    @Transactional // could not initialize proxy - no Session : Lazy fetch로 인한 오류
    void readMember() {
        Member member = new Member();
        member.setSeq(51L);
        Member result = null;
        if((result = memberService.read(member)) != null)
            System.out.println("조회 성공" + result.getEmail() + ":::" + result.getName());
        else
            System.out.println("조회 실패");
    }

    @Test
    public void testPageList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(5).perPage(10).build();
        PageResultDTO<Member, MemberEntity> resultDTO = memberService.getList(pageRequestDTO);
        for(Member member : resultDTO.getDtoList())
            System.out.println(member);

        System.out.println("Prev : " + resultDTO.isPrev()); // PerPagination = 4인경, 1 - 4 , 5 - 8, 9 - 12
        System.out.println("Next : " + resultDTO.isNext());
        System.out.println("Total Page : " + resultDTO.getTotalPage());
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
