package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Board;
import idusw.springboot.boradmwlee.domain.Member;
import idusw.springboot.boradmwlee.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class BoardController {
    HttpSession session = null;
    private final BoardService boardService; // BoardController에서 사용할 BoardService 객체를 참조하는 변수
    public BoardController(BoardService boardService) {
        // Spring Framework가 BoardService 객체를 주입, boardService(주입될 객체의 참조변수)
        this.boardService = boardService;
    }
    @GetMapping(value = {"/", ""})
    public String getBoardList(Model model) {
        model.addAttribute("key", "value");
        return "/boards/list"; // board/list.html 뷰로 전달
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("board", Board.builder().build());
        // model.addAttribute("member", Member.builder().build());
        return "/boards/register"; // register.html, view resolving
    }

    @PostMapping("/")
    public String registerMember(@ModelAttribute("board") Board board, Model model, HttpServletRequest request) {
        session = request.getSession();
        Member member = (Member) session.getAttribute("mb");
        if(member != null) {
            board.setWriterSeq(member.getSeq());
            board.setWriterEmail(member.getEmail());
            board.setWriterName(member.getName());

            if(boardService.registerBoard(board) > 0 )
                return "redirect:/"; // 홈으로 재지정함 : 컨트롤러에게 재지정
            else
                return "/404"; // 예외 처리 화면
        }
        else
            return "redirect:/members/register";
    }
}
