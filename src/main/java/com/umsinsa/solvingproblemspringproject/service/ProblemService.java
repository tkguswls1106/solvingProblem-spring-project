package com.umsinsa.solvingproblemspringproject.service;

import com.umsinsa.solvingproblemspringproject.dto.problem.*;

import java.util.List;

public interface ProblemService {

    ProblemSaveResponseDto saveProblem(Long categoryId, ProblemSaveRequestDto problemSaveRequestDto);  // 해당 categoryId 카테고리에 신규 문제 생성 기능.
    List<ProblemResponseDto> findProblemsByCategoryId(Long categoryId);  // 해당 categoryId 카테고리의 모든 문제들 리스트 정렬해서 반환 기능.
    ProblemOneResponseDto findById(Long problemId);  // problemId로 검색한 문제 1개 반환 기능. (이 안에 정답자 유무 정보도 포함되어있음.)
    void updateProblem(Long problemId, ProblemUpdateRequestDto problemUpdateRequestDto);  // 해당 problemId의 문제 수정 기능.
    void updateRecommend(Long problemId, ProblemUpdateUserIdRequestDto problemUpdateUserIdRequestDto);  // 해당 problemId의 문제 추천 기능.
    void updateSolve(Long problemId, ProblemUpdateUserIdRequestDto problemUpdateUserIdRequestDto);  // 해당 problemId의 문제의 사용자 정답처리 기능.
    void deleteProblem(Long problemId);  // 해당 problemId의 문제 삭제 기능.
}
