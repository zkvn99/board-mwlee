package idusw.springboot.boradmwlee.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity // 엔티티 클래스임으로 나타내는 애노테이션
@Table(name = "a201912012_board")

@ToString(exclude = "writer")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
// @Data // == @ToString, @EqualsAndHashCode, @Getter @Setter @RequiredArgConstructor
// JPA Auditing 을 활용하여서 생성한사람, 생성일자, 수정한사람, 수정일자 등을 선택하여서 감사
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a201912012_board_seq_gen")
    @SequenceGenerator(sequenceName = "a201912012_board_seq", name = "a201912012_board_seq_gen",initialValue = 1, allocationSize = 1)
    // Oracle : GenerationType.SEQUENCE, Mysql/MariaDB : GenerationType.IDENTITY, auto_increment
    private Long bno; // 유일키
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // EAGER로 할 경우 메모리 낭비
    // @JoinColumn(MemberEntitY)
    private MemberEntity writer; // N:1 연관 관계 지정 : 작성자 1명 : 게시물 다수
}
