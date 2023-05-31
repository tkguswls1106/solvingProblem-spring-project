package com.umsinsa.solvingproblemspringproject.service.logic;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.category.CategoryJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.comment.CommentJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.problem.ProblemJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import com.umsinsa.solvingproblemspringproject.domain.user.UserJpaRepository;
import com.umsinsa.solvingproblemspringproject.dto.problem.*;
import com.umsinsa.solvingproblemspringproject.dto.user.UserResponseDto;
import com.umsinsa.solvingproblemspringproject.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class ProblemServiceLogic implements ProblemService {

    private final ProblemJpaRepository problemJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CommentJpaRepository commentJpaRepository;


    public UserResponseDto getMyInfoBySecurity() {  // 헤더에 있는 token값을 토대로 User의 data를 건내주는 메소드이다.
        return userJpaRepository.findById(com.umsinsa.solvingproblemspringproject.util.SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }


    @Transactional
    @Override
    public ProblemSaveResponseDto saveProblem(Long categoryId, ProblemSaveRequestDto problemSaveRequestDto) {  // 해당 categoryId 카테고리에 신규 문제 생성 기능.

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.

        User userEntity = User.builder()
                .id(securityUserResponseDto.getId())
                .loginId(securityUserResponseDto.getLoginId())
                .username(securityUserResponseDto.getUsername())
                .solvableCount(securityUserResponseDto.getSolvableCount())
                .build();

        Category categoryEntity = categoryJpaRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 categoryId의 카테고리 조회 실패"));

        Problem entity = problemJpaRepository.save(problemSaveRequestDto.toEntity(userEntity, categoryEntity));

        return new ProblemSaveResponseDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProblemResponseDto> findProblemsByCategoryId(Long categoryId) {  // 해당 categoryId 카테고리의 모든 문제들 리스트 정렬해서 반환 기능.

//        getMyInfoBySecurity();  // 헤더의 토큰 유효검사와 사용자 일치 검사 실행.

        Category categoryEntity = categoryJpaRepository.findById(categoryId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 categoryId의 카테고리 조회 실패"));

        List<Problem> problems = problemJpaRepository.findAllByCategory(categoryEntity);

        return problems.stream().map(ProblemResponseDto::new)
                .sorted(Comparator.comparing(ProblemResponseDto::getId, Comparator.reverseOrder()))  // 문제 id 내림차순 정렬
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ProblemOneResponseDto findById(Long problemId) {  // problemId로 검색한 문제 1개 반환 기능. (이 안에 정답자 유무 정보도 포함되어있음.)

        getMyInfoBySecurity();  // 헤더의 토큰 유효검사와 사용자 일치 검사 실행.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));
        return new ProblemOneResponseDto(entity);
    }

    @Transactional
    @Override
    public void updateProblem(Long problemId, ProblemUpdateRequestDto problemUpdateRequestDto) {  // 해당 problemId의 문제 수정 기능.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() != entity.getUser().getId()) {
            // 헤더 token의 userId와 problem의 userId가 일치하는지 확인하여,
            // 해당 문제를 작성했던 사용자가 맞는지 확인하고 이를통해, 문제를 수정 또는 삭제할 권한이 있는 사용자인지 확인한다.
            throw new RuntimeException("ERROR - 해당 문제에 접근할 권한이 없습니다.");
        }

        entity.updateProblem(problemUpdateRequestDto.getTitle(), problemUpdateRequestDto.getContent());
    }

    @Transactional
    @Override
    public void updateRecommend(Long problemId) {  // 해당 problemId의 문제 추천 기능.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() == entity.getUser().getId()) {
            // 헤더 token의 userId와 problem의 userId가 일치하는지 확인하여,
            // 접속한 사용자와 문제를 작성했던 사용자가 일치하는지 확인하고
            // 만약 동일 사용자라면 본인이 본인 게시문제에 대해서는 추천을 누를수 없도록 예외처리 시킨다.
            throw new RuntimeException("ERROR - 본인이 작성한 문제에는 추천을 누를 수 없습니다.");
        }


        String beforeParsing = entity.getRecommendUsers();
        String[] afterParsing = beforeParsing.split("p");
        List<Long> userIds = new ArrayList<>();
        for (String userId : afterParsing) {
            userIds.add(Long.parseLong(userId));
        }
        boolean isContain = userIds.contains(securityUserResponseDto.getId());

        if(!isContain) {  // 추천을 아직 누른사람이 아니라면
            String parsingStr = Long.toString(securityUserResponseDto.getId()) + "p";
            entity.updateRecommend(entity.getRecommendCount()+1, entity.getRecommendUsers()+parsingStr);
        }
        else {
            throw new RuntimeException("ERROR - 이미 추천을 눌렀던 사용자입니다.");
        }
    }

    @Transactional
    @Override
    public void updateSolve(Long problemId) {  // 해당 problemId의 문제의 사용자 정답처리 기능.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() == entity.getUser().getId()) {
            // 헤더 token의 userId와 problem의 userId가 일치하는지 확인하여,
            // 접속한 사용자와 문제를 작성했던 사용자가 일치하는지 확인하고
            // 만약 동일 사용자라면 본인이 본인 게시문제에 대해서는 문제풀이가능잔여횟수 차감이 없도록하며, 정답 처리는 없도록 함.
            throw new RuntimeException("ERROR - 이는 본인이 작성한 문제이므로 횟수 차감 및 정답 처리는 없습니다.");
        }


        String beforeParsing = entity.getSolveUsers();
        String[] afterParsing = beforeParsing.split("p");
        List<Long> userIds = new ArrayList<>();
        for (String userId : afterParsing) {
            userIds.add(Long.parseLong(userId));
        }
        boolean isContain = userIds.contains(securityUserResponseDto.getId());

        if(!isContain) {  // 정답을 아직 맞춰본 사용자가 아니라면
            String parsingStr = Long.toString(securityUserResponseDto.getId()) + "p";
            entity.updateSolve(entity.getSolveUsers()+parsingStr);
        }
        else {
            throw new RuntimeException("ERROR - 이미 해당 문제의 정답을 맞췄던 사용자입니다.");
        }
    }

    @Transactional
    @Override
    public void deleteProblem(Long problemId) {  // 해당 problemId의 문제 삭제 기능.

        Problem entity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() != entity.getUser().getId()) {
            // 헤더 token의 userId와 problem의 userId가 일치하는지 확인하여,
            // 해당 문제를 작성했던 사용자가 맞는지 확인하고 이를통해, 문제를 수정 또는 삭제할 권한이 있는 사용자인지 확인한다.
            throw new RuntimeException("ERROR - 해당 문제에 접근할 권한이 없습니다.");
        }

        commentJpaRepository.deleteAllByProblem(entity);  // 부모 테이블인 Problem보다 먼저, 자식 테이블인 Comment에서 해당 문제와 관련된 댓글들 전부 삭제.
        problemJpaRepository.delete(entity);  // 부모 테이블인 problem의 데이터 삭제.
    }
}
