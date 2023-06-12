package idusw.springboot.boradmwlee.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.domain.Memo;
import idusw.springboot.boradmwlee.domain.PageRequestDTO;
import idusw.springboot.boradmwlee.domain.PageResultDTO;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import idusw.springboot.boradmwlee.entity.MemoEntity;
import idusw.springboot.boradmwlee.entity.QMemberEntity;
import idusw.springboot.boradmwlee.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class MemberServiceImpl implements MemberService {
    MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public int create(Member m) {
        // DTO -> Entity : Repository에서 처리하기 위해
        MemberEntity entity = dtoToEntity(m);
        //MemberEntity entity = new MemberEntity(m.getSeq(), m.getEmail(), m.getName(), m.getPw())
        if(memberRepository.save(entity) != null) // 저장 성공
            return 1;
        else
            return 0;
    }

    @Override
    public Member read(Member m) {
        MemberEntity e = memberRepository.getById(m.getSeq());
        Member member = Member.builder()
                .seq(e.getSeq())
                .email(e.getEmail())
                .name(e.getName())
                .pw(e.getPw())
                .phone(e.getPhone())
                .build();
        return member;
    }

    @Override
    public List<Member> readList() {
        // 1. 매개변수를 처리하고(처리를 위한 객체 선언 및 초기화), 리파지터리 객체에게 전달
        // 2. 리파지터리 객체의 해당 메소드가 처리한 결과를 반환 유형으로 변환
        // repository : entity 객체를 대상으로 함, service : dto or domain 객체를 대상으로 함

        List<Member> result = new ArrayList<>(); // Memo 객체를 원소로 갖는 리스트형 객체를 생성, 배정
        List<MemberEntity> entities = memberRepository.findAll(); // select * from a_memo;
        for(MemberEntity e : entities) {
            // Function Language 특징을 활용한 처리, 1.8 Lamda 식 ->
            Member m = Member.builder()
                    .seq(e.getSeq())
                    .email(e.getEmail())
                    .name(e.getName())
                    .pw(e.getPw())
                    .regDate(e.getRegDate())
                    .modDate(e.getModDate())
                    .phone(e.getPhone())
                    .build();
            result.add(m);// DTO (Data Transfer Object) : Controller - Service or Controller - View
            /*
            Member m = new Member();
            m.setSeq(e.getSeq());
            m.setEmail(e.getEmail());
            m.setName(e.getName());
             */
        }
        return result;
    }

    @Override
    public int update(Member m) {
        MemberEntity entity = MemberEntity.builder()
                .email(m.getEmail())
                .name(m.getName())
                .pw(m.getPw())
                .phone(m.getPhone())
                .build();
        if(memberRepository.save(entity) != null) // 저장 성공
            return 1;
        else
            return 0;
    }

    @Override
    public int delete(Member m) {
        MemberEntity entity = MemberEntity.builder()
                .seq(m.getSeq())
                .build();
        memberRepository.deleteById(entity.getSeq());
        return 1;
    }

    @Override
    public Member login(Member m) { // 인터페이스에 선언된 메소드 중 구현하지 않는 메소드를 구현해야 함
        Member result = null;
        MemberEntity entity = memberRepository.getByEmailpw(m.getEmail(),m.getPw());
        if(entity != null){
            result = new Member();
            result.setSeq(entity.getSeq());
            result.setEmail(entity.getEmail());
            result.setName(entity.getName());
            result.setAbandon(entity.isAbandon());
        }
        return result;
    }

    @Override
    public Member findById(Long id) {
        MemberEntity entitySeq = memberRepository.findById(id).orElse(null);
        return entityToDto(entitySeq);
    }

    @Override
    public PageResultDTO<Member, MemberEntity> getList(PageRequestDTO requestDTO) {
        Sort sort = Sort.by("seq").ascending();
        /*
        if(requestDTO.getSort() == null)
            sort = Sort.by("seq").descending();
        else
            sort = Sort.by("seq").ascending();
            */
        Pageable pageable = requestDTO.getPageable(sort);
        //Page<MemberEntity> result = memberRepository.findAll(pageable);
        BooleanBuilder booleanBuilder = findByCondition(requestDTO);
        Page<MemberEntity> result = memberRepository.findAll(booleanBuilder, pageable);

        Function<MemberEntity, Member> fn = (entity -> entityToDto(entity));

        PageResultDTO pageResultDTO = new PageResultDTO<>(result, fn, requestDTO.getPerPagination());
        return pageResultDTO;
    }

    @Override
    public boolean isEmailDuplicated(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public int checkEmail(Member member) {
        List<MemberEntity> memberEntityList = memberRepository.getMemberEntitiesByEmail(member.getEmail());
        if(memberEntityList.size() > 0)
            return 1;
        else
            return 0;
    }

    private BooleanBuilder findByCondition(PageRequestDTO pageRequestDTO) {

        String type = pageRequestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMemberEntity qMemberEntity = QMemberEntity.memberEntity;
        // 없을 경우 Gradle clean 후 build

        BooleanExpression expression = qMemberEntity.seq.gt(0L); // where seq > 0 and title == "title"
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("e")) { // email로 검색
            conditionBuilder.or(qMemberEntity.email.contains(keyword));
        }
        if(type.contains("n")) { // name로 검색
            conditionBuilder.or(qMemberEntity.name.contains(keyword));
        }

        if(type.contains("p")) { // phone로 검색
            conditionBuilder.or(qMemberEntity.phone.contains(keyword));
        }
        /*
        if(type.contains("a")) { // address로 검색
            conditionBuilder.or(qMemberEntity.address.contains(keyword));
        } // 조건을 전부 줄 수도 있으니 if else문 아님
        if(type.contains("l")) {
            conditionBuilder.or(qMemberEntity.level.contains(keyword));
        } */
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }

    @Override
    public void banMember(Long id) {
        MemberEntity entitySeq = memberRepository.findById(id).orElse(null);
        if (entitySeq != null) {
            if (entitySeq.isAbandon()) {
                entitySeq.setAbandon(false);
            } else {
                entitySeq.setAbandon(true);
            }
            memberRepository.save(entitySeq);
        }
    }
}
