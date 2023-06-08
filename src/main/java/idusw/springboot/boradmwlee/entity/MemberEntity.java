package idusw.springboot.boradmwlee.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티 클래스임으로 나타내는 애노테이션
@Table(name = "a201912012_member")

@ToString   // lombok 라이브러리 사용
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@SequenceGenerator(sequenceName = "a201912012_member_seq", name = "a201912012_member_seq_gen", initialValue = 1, allocationSize = 1)
public class MemberEntity extends BaseEntity{
    // Entity : Service -> Repository -> Service 데이터 객체, Database 관점
    // Repository : persistence Data
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    // Oracle : GenerationType.SEQUENCE, mysql : GenerationType.IDENTITY
    // Mysql :auto increment, Oracle : sequence - 자동 증가 식별번호
    private Long seq;

    @Column(length = 30, nullable = false)
    private String email;
    @Column(length = 20, nullable = false)
    private String name;
    @Column(length = 20, nullable = false)
    private String pw;

    // nextval => 새로운 번호 (값 증가)
    // currval => 번호 읽기
}
