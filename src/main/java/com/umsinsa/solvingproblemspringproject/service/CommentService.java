package com.umsinsa.solvingproblemspringproject.service;

import com.umsinsa.solvingproblemspringproject.dto.comment.*;

import java.util.List;

public interface CommentService {

    CommentSaveResponseDto saveComment(Long problemId, CommentSaveRequestDto commentSaveRequestDto);  // 해당 problemId 문제에 신규 댓글 생성 기능.
    List<CommentResponseDto> findCommentsByProblemId(Long problemId);  // 해당 problemId 문제의 모든 댓글들 리스트 정렬해서 반환 기능.
    void updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto);  // 해당 commentId의 댓글 수정 기능.
    void updateRecommend(Long commentId);  // 해당 commentId의 댓글 추천 기능.
    void deleteComment(Long commentId);  // 해당 commentId의 댓글 삭제 기능.
}
