package idusw.springboot.boradmwlee.repository;

import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>,
        QuerydslPredicateExecutor<MemberEntity> { // Join 사용안해서 Sort Page Search 간단하게 해결
    //JPQL
    @Transactional
    @Query("select m from MemberEntity m where m.email = :email and m.pw = :pw")
    MemberEntity getByEmailpw(@Param("email") String email, @Param("pw") String pw);
    // JpaRepository : 영속 데이터를 처리하기 위해 ORM(Object - Relation Mapping)을 정의한
    // JPA 사양서를 구현한 구현체에 대한 인터페이스
    // 'org.springframework.boot:spring-boot-starter-data-jpa' : spring-data-jpa 라이브러리에 포함
    boolean existsByEmail(String email);
}
