package idusw.springboot.boradmwlee.domain;

import lombok.*;

import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@ToString
@EqualsAndHashCode

public class Board { // 5 fields
    //board
    private Long bno; // 유일성 있음
    private String title;
    private String content;

    //join
    private Long writerSeq;
    private String writerEmail;
    private String writerName;

    //reply
    private Long replyCount;

    // auditing
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    //like
    private Long boardLike;
}
