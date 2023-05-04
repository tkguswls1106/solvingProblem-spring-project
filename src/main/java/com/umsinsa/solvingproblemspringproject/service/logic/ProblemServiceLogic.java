package com.umsinsa.solvingproblemspringproject.service.logic;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.category.CategoryJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.problem.ProblemJpaRepository;
import com.umsinsa.solvingproblemspringproject.dto.problem.*;
import com.umsinsa.solvingproblemspringproject.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class ProblemServiceLogic implements ProblemService {

    private final ProblemJpaRepository problemJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;


    @Transactional
    @Override
    public ProblemSaveResponseDto saveProblem(Long categoryId, ProblemSaveRequestDto problemSaveRequestDto) {  // 해당 categoryId 카테고리에 신규 문제 생성 기능.

        Category categoryEntity = categoryJpaRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 categoryId의 카테고리 조회 실패"));

        Problem entity = problemJpaRepository.save(problemSaveRequestDto.toEntity(categoryEntity));

        return new ProblemSaveResponseDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProblemResponseDto> findProblemsByCategoryId(Long categoryId) {  // 해당 categoryId 카테고리의 모든 문제들 리스트 정렬해서 반환 기능.

        Category categoryEntity = categoryJpaRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 categoryId의 카테고리 조회 실패"));

        List<Problem> problems = problemJpaRepository.findAllByCategory(categoryEntity);

        return problems.stream().map(ProblemResponseDto::new)
                .sorted(Comparator.comparing(ProblemResponseDto::getId, Comparator.reverseOrder()))  // 문제 id 내림차순 정렬
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProblemResponseDto findById(Long problemId) {  // problemId로 검색한 문제 1개 반환 기능.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));
        return new ProblemResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateProblem(Long problemId, ProblemUpdateRequestDto problemUpdateRequestDto) {  // 해당 problemId의 문제 수정 기능.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        if (!entity.getPassword().equals(problemUpdateRequestDto.getPassword())) {  // 이미 DB의 비밀번호와 입력받은 비밀번호가 다른 경우라면,
            throw new RuntimeException("ERROR - 비밀번호 불일치 에러");
        }

        entity.updateContent(problemUpdateRequestDto.getTitle(), problemUpdateRequestDto.getContent());
    }

    @Transactional
    @Override
    public void deleteProblem(Long problemId, ProblemDeleteRequestDto problemDeleteRequestDto) {  // 해당 problemId의 문제 삭제 기능.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        if (!entity.getPassword().equals(problemDeleteRequestDto.getPassword())) {  // 이미 DB의 비밀번호와 입력받은 비밀번호가 다른 경우라면,
            throw new RuntimeException("ERROR - 비밀번호 불일치 에러");
        }

        problemJpaRepository.delete(entity);
    }
}
