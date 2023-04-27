package idusw.springboot.boradmwlee.service;

import idusw.springboot.boradmwlee.domain.Member;

import java.util.List;

public interface MemberService {
    int create(Member m);
    Member read(Member m);
    List<Member> readList();
    int update(Member m);
    int delete(Member m);
    Member login(Member m);
}
