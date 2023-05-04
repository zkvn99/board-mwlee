package idusw.springboot.boradmwlee.entity;

import jakarta.persistence.*;

public class BoardEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sw_board_seq_gen")
    @SequenceGenerator(sequenceName = "sw_board_seq", name = "sw_board_seq_gen", allocationSize = 1)
    private Long ano; // 유일키

    private String title; // 제목
    private String content; // 내용
    private Long views; // 조회수
    private String block; // 차단여부

    @ManyToOne
    private MemberEntity writer; // N:1 연관 관계 지정 : 작성자 1명 : 게시물 다수
}
