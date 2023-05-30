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


    @PostMapping("/problems/{problemId}/comments")
    public CommentSaveResponseDto saveComment(@PathVariable Long problemId, @RequestBody CommentSaveRequestDto commentSaveRequestDto) {
        CommentSaveResponseDto commentSaveResponseDto = commentService.saveComment(problemId, commentSaveRequestDto);
        return commentSaveResponseDto;
    }

    @GetMapping("/problems/{problemId}/comments")
    public List<CommentResponseDto> problemLoadComments(@PathVariable Long problemId) {
        List<CommentResponseDto> commentResponseDtos = commentService.findCommentsByProblemId(problemId);
        return commentResponseDtos;
    }

    @PutMapping("/comments/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        commentService.updateComment(commentId, commentUpdateRequestDto);
    }

    @PutMapping("/recommend-comment/{commentId}")
    public void updateRecommend(@PathVariable Long commentId, @RequestBody CommentUpdateUserIdRequestDto commentUpdateUserIdRequestDto) {
        commentService.updateRecommend(commentId, commentUpdateUserIdRequestDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

}
