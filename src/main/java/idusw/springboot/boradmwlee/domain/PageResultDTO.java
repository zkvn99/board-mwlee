package idusw.springboot.boradmwlee.domain;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList; // DTO : Member, Board 객체

    private int totalPage; // 총 페이지 번호
    private int curPage; // 현재 페이지 번호
    private int size; // 한 페이지 게시물 갯수

    private int start, end; // 시작 페이지 번호, 끝 페이지 번호 : 게시물
    private boolean prev, next; // 버튼 표시

    private List<Integer> pageList; // 페이지 번호 목록

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
    }
}
