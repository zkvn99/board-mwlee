package idusw.springboot.boradmwlee.service;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.entity.MemberEntity;

import java.util.List;

public interface MemberService {
    int create(Member m);
    Member read(Member m);
    List<Member> readList();
    int update(Member m);
    int delete(Member m);
    Member login(Member m);

    Member findById(Long id);

    void banMember(Long id);

    // java 1.8 : 인터페이스가 기본 메소드를 가질 수 있도록 함.
    PageResultDTO<Member, MemberEntity> getList(PageRequestDTO requestDTO);

    boolean isEmailDuplicated(String email);

    default MemberEntity dtoToEntity(Member dto) { // dto객체를 entity 객체로 변환 : service -> repository
        MemberEntity entity = MemberEntity.builder()
                .seq(dto.getSeq())
                .email(dto.getEmail())
                .name(dto.getName())
                .pw(dto.getPw())
                .phone(dto.getPhone())
                .abandon(dto.isAbandon())
                .build();
        return entity;
    }
    default Member entityToDto(MemberEntity entity) { // entity 객체를 dto 객체로 변환 : service -> controller
        Member dto = Member.builder()
                .seq(entity.getSeq())
                .email(entity.getEmail())
                .name(entity.getName())
                .pw(entity.getPw())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .phone(entity.getPhone())
                .abandon(entity.isAbandon())
                .build();
        return dto;
    }

    int checkEmail(Member member);
}
