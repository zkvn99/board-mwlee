package idusw.springboot.boradmwlee.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티 클래스임으로 나타내는 애노테이션
@Table(name = "a201912012_board_like")

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a201912012_board_like_seq_gen")
    @SequenceGenerator(sequenceName = "a201912012_board_like_seq", name = "a201912012_board_like_seq_gen",initialValue = 1, allocationSize = 1)
    // Oracle : GenerationType.SEQUENCE, Mysql/MariaDB : GenerationType.IDENTITY, auto_increment
    private Long lno; // 유일키

    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(MemberEntitY)
    private MemberEntity seq; // 1:1 연관 관계 지정 : 회원 1 : 좋아요 1

    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(MemberEntitY)
    private BoardEntity bno; // 1:1 연관 관계 지정 : 블로그 1 : 좋아요 N

}
