package com.umsinsa.solvingproblemspringproject.service;

import com.umsinsa.solvingproblemspringproject.dto.comment.*;

import java.util.List;

public interface CommentService {

    CommentSaveResponseDto saveComment(Long problemId, CommentSaveRequestDto commentSaveRequestDto);
    List<CommentResponseDto> findCommentsByProblemId(Long problemId);
    void updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto);
    void updateRecommend(Long commentId, CommentUpdateUserIdRequestDto commentUpdateUserIdRequestDto);
    void deleteComment(Long commentId);
}
