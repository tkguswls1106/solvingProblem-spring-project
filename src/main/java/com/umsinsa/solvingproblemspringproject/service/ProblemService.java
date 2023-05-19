package com.umsinsa.solvingproblemspringproject.service;

import com.umsinsa.solvingproblemspringproject.dto.problem.*;

import java.util.List;

public interface ProblemService {

    ProblemSaveResponseDto saveProblem(Long categoryId, ProblemSaveRequestDto problemSaveRequestDto);  // 해당 categoryId 카테고리에 신규 문제 생성 기능.
    List<ProblemResponseDto> findProblemsByCategoryId(Long categoryId);  // 해당 categoryId 카테고리의 모든 문제들 리스트 정렬해서 반환 기능.
    ProblemResponseDto findById(Long problemId);  // problemId로 검색한 문제 1개 반환 기능.
    void updateProblem(Long problemId, ProblemUpdateRequestDto problemUpdateRequestDto);  // 해당 problemId의 문제 수정 기능.
    void updateRecommend(Long problemId, ProblemUpdateRecommendRequestDto problemUpdateRecommendRequestDto);
    void deleteProblem(Long problemId);  // 해당 problemId의 문제 삭제 기능.
}
