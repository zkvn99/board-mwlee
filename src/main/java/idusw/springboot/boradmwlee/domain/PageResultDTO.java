package idusw.springboot.boradmwlee.domain;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList; // DTO : Member, Board 객체

    private int totalPage; // 총 페이지 번호
    private int curPage; // 현재 페이지 번호
    private int size; // 한 페이지 게시물 갯수
    private int totalRows; // 총 행의 수

    private int start, end; // 시작 페이지 번호, 끝 페이지 번호 : 게시물
    private boolean prev, next; // 버튼 표시

    private List<Integer> pageList; // 페이지 번호 목록

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList()); // get records
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.curPage = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(curPage / ((double) size))) * size;

        start = tempEnd - (size - 1);
        end = (totalPage > tempEnd) ? tempEnd: totalPage;

        prev = start > 1; // 1보다 크면 true, 작으면 false
        next = totalPage > tempEnd;

        // 메소드 체이닝
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList()); // get pageNumber list
        for(Integer i : pageList)
            System.out.println(i.intValue());
    }
}
