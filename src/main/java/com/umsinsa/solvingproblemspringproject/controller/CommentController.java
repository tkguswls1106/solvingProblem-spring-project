package com.umsinsa.solvingproblemspringproject.controller;

import com.umsinsa.solvingproblemspringproject.dto.comment.*;
import com.umsinsa.solvingproblemspringproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/problems/{problemId}/comments")  // 헤더 토큰정보 필요함 O
    public CommentSaveResponseDto saveComment(@PathVariable Long problemId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {  // 신규 댓글 생성
        CommentSaveResponseDto commentSaveResponseDto = commentService.saveComment(problemId, commentSaveRequestDto);
        return commentSaveResponseDto;
    }

    @GetMapping("/problems/{problemId}/comments")  // 헤더 토큰정보 필요함 O
    public List<CommentResponseDto> problemLoadComments(@PathVariable Long problemId) {  // 선택한 문제의 모든 댓글들 조회
        List<CommentResponseDto> commentResponseDtos = commentService.findCommentsByProblemId(problemId);
        return commentResponseDtos;
    }

    @PutMapping("/comments/{commentId}")  // 헤더 토큰정보 필요함 O
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {  // 댓글 내용 수정
        commentService.updateComment(commentId, commentUpdateRequestDto);
    }

    @PutMapping("/recommend-comment/{commentId}")  // 헤더 토큰정보 필요함 O
    public void updateRecommend(@PathVariable Long commentId) {  // 댓글 추천
        commentService.updateRecommend(commentId);
    }

    @DeleteMapping("/comments/{commentId}")  // 헤더 토큰정보 필요함 O
    public void deleteComment(@PathVariable Long commentId) {  // 댓글 삭제
        commentService.deleteComment(commentId);
    }

}
