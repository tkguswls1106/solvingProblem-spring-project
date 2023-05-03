package com.umsinsa.solvingproblemspringproject.service;

import com.umsinsa.solvingproblemspringproject.dto.problem.*;

public interface ProblemService {

    ProblemSaveResponseDto saveProblem(ProblemSaveRequestDto problemSaveRequestDto);  // 신규 문제 생성 기능.
    ProblemResponseDto findById(Long problemId);  // problemId로 검색한 문제 1개 반환 기능.
    void updateProblem(Long problemId, ProblemUpdateRequestDto problemUpdateRequestDto);  // 해당 problemId의 문제 수정 기능.
    void deleteProblem(Long problemId, ProblemDeleteRequestDto problemDeleteRequestDto);  // 해당 problemId의 문제 삭제 기능.
}
