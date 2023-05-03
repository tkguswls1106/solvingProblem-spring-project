package com.umsinsa.solvingproblemspringproject.controller;

import com.umsinsa.solvingproblemspringproject.dto.problem.*;
import com.umsinsa.solvingproblemspringproject.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
@RequestMapping("/problems")
public class ProblemController {

    private final ProblemService problemService;


    @PostMapping
    public ProblemSaveResponseDto saveProblem(@RequestBody ProblemSaveRequestDto problemSaveRequestDto) {  // 신규 문제 생성
        ProblemSaveResponseDto problemSaveResponseDto = problemService.saveProblem(problemSaveRequestDto);
        return problemSaveResponseDto;
    }

    @GetMapping("/{problemId}")
    public ProblemResponseDto findProblemById(@PathVariable Long problemId) {  // 문제 1개 정보 조회
        ProblemResponseDto problemResponseDto = problemService.findById(problemId);
        return problemResponseDto;
    }

    @PutMapping("/{problemId}")
    public void updateProblem(@PathVariable Long problemId, @RequestBody ProblemUpdateRequestDto problemUpdateRequestDto) {  // 문제 내용 수정
        problemService.updateProblem(problemId, problemUpdateRequestDto);
    }

    @DeleteMapping("/{problemId}")
    public void deleteProblem(@PathVariable Long problemId, @RequestBody ProblemDeleteRequestDto problemDeleteRequestDto) {  // 문제 삭제
        problemService.deleteProblem(problemId, problemDeleteRequestDto);
    }

}
