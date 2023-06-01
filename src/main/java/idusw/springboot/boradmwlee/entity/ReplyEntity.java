package idusw.springboot.boradmwlee.entity;

import idusw.springboot.boradmwlee.domain.Board;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "a201912012_reply")

@ToString(exclude = "board")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "a201912012_reply_seq_gen")
    @SequenceGenerator(sequenceName = "a201912012_reply_seq", name = "a201912012_reply_seq_gen",initialValue = 1, allocationSize = 1)
    // Oracle : GenerationType.SEQUENCE, Mysql/MariaDB : GenerationType.IDENTITY, auto_increment
    private Long rno; // 유일키
    private String text; // 댓글 내용
    private String replier; // 댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY) // BoardEntity 참조를 늦게 함
    private BoardEntity board; // BoardEntity : MemberEntity = N : 1
}
