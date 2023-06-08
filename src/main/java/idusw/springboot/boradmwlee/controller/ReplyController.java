package idusw.springboot.boradmwlee.controller;

import idusw.springboot.boradmwlee.domain.Reply;
import idusw.springboot.boradmwlee.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies/")
@Log4j2
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody Reply reply) {
        return new ResponseEntity<>(replyService.register(reply), HttpStatus.OK);
    }

    @GetMapping(value = "/boards/{bno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Reply>> getListByBno(@PathVariable("bno") Long bno) {
        return new ResponseEntity<>(replyService.getList(bno), HttpStatus.OK);
    }

    @PutMapping("/{rno}")
    public ResponseEntity<String> modify(@RequestBody Reply reply) {
        replyService.modify(reply);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @DeleteMapping("/{rno}")
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        Reply reply = Reply.builder().rno(rno).build();
        replyService.remove(reply);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
