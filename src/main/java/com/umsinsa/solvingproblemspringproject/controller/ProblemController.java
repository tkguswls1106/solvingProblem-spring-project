package com.umsinsa.solvingproblemspringproject.controller;

import com.umsinsa.solvingproblemspringproject.dto.problem.*;
import com.umsinsa.solvingproblemspringproject.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class ProblemController {

    private final ProblemService problemService;


    @PostMapping("/categories/{categoryId}/problems")
    public ProblemSaveResponseDto saveProblem(@PathVariable Long categoryId, @RequestBody ProblemSaveRequestDto problemSaveRequestDto) {  // 신규 문제 생성
        ProblemSaveResponseDto problemSaveResponseDto = problemService.saveProblem(categoryId, problemSaveRequestDto);
        return problemSaveResponseDto;
    }

    @GetMapping("/categories/{categoryId}/problems")
    public List<ProblemResponseDto> categoryLoadProblems(@PathVariable Long categoryId) {  // 선택한 카테고리의 모든 문제들 조회
        List<ProblemResponseDto> problemResponseDtos = problemService.findProblemsByCategoryId(categoryId);
        return problemResponseDtos;
    }

    @GetMapping("/problems/{problemId}")
    public ProblemResponseDto findProblemById(@PathVariable Long problemId) {  // 문제 1개 정보 조회
        ProblemResponseDto problemResponseDto = problemService.findById(problemId);
        return problemResponseDto;
    }

    @PutMapping("/problems/{problemId}")
    public void updateProblem(@PathVariable Long problemId, @RequestBody ProblemUpdateRequestDto problemUpdateRequestDto) {  // 문제 내용 수정
        problemService.updateProblem(problemId, problemUpdateRequestDto);
    }

    @DeleteMapping("/problems/{problemId}")
    public void deleteProblem(@PathVariable Long problemId, @RequestBody ProblemDeleteRequestDto problemDeleteRequestDto) {  // 문제 삭제
        problemService.deleteProblem(problemId, problemDeleteRequestDto);
    }

}