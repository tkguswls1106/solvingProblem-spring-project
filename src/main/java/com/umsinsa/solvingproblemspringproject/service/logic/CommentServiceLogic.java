package com.umsinsa.solvingproblemspringproject.service.logic;

import com.umsinsa.solvingproblemspringproject.domain.comment.Comment;
import com.umsinsa.solvingproblemspringproject.domain.comment.CommentJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.problem.Problem;
import com.umsinsa.solvingproblemspringproject.domain.problem.ProblemJpaRepository;
import com.umsinsa.solvingproblemspringproject.domain.user.User;
import com.umsinsa.solvingproblemspringproject.domain.user.UserJpaRepository;
import com.umsinsa.solvingproblemspringproject.dto.comment.*;
import com.umsinsa.solvingproblemspringproject.dto.user.UserResponseDto;
import com.umsinsa.solvingproblemspringproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class CommentServiceLogic implements CommentService {

    private final UserJpaRepository userJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final ProblemJpaRepository problemJpaRepository;


    public UserResponseDto getMyInfoBySecurity() {  // 헤더에 있는 token값을 토대로 User의 data를 건내주는 메소드이다.
        return userJpaRepository.findById(com.umsinsa.solvingproblemspringproject.util.SecurityUtil.getCurrentMemberId())
                .map(UserResponseDto::new)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }


    @Transactional
    @Override
    public CommentSaveResponseDto saveComment(Long problemId, CommentSaveRequestDto commentSaveRequestDto) {  // 해당 problemId 문제에 신규 댓글 생성 기능.

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.

        User userEntity = User.builder()
                .id(securityUserResponseDto.getId())
                .loginId(securityUserResponseDto.getLoginId())
                .username(securityUserResponseDto.getUsername())
                .solvableCount(securityUserResponseDto.getSolvableCount())
                .build();

        Problem problemEntity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        Comment entity = commentJpaRepository.save(commentSaveRequestDto.toEntity(userEntity, problemEntity));

        return new CommentSaveResponseDto(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentResponseDto> findCommentsByProblemId(Long problemId) {  // 해당 problemId 문제의 모든 댓글들 리스트 정렬해서 반환 기능.

        getMyInfoBySecurity();  // 헤더의 토큰 유효검사와 사용자 일치 검사 실행.

        Problem problemEntity = problemJpaRepository.findById(problemId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 problemId의 문제 조회 실패"));

        List<Comment> comments = commentJpaRepository.findAllByProblem(problemEntity);

        return comments.stream().map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getId, Comparator.reverseOrder()))  // 문제 id 내림차순 정렬
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {  // 해당 commentId의 댓글 수정 기능.

        Comment entity = commentJpaRepository.findById(commentId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 commentId의 댓글 조회 실패"));

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() != entity.getUser().getId()) {
            // 헤더 token의 userId와 comment의 userId가 일치하는지 확인하여,
            // 해당 댓글을 작성했던 사용자가 맞는지 확인하고 이를통해, 댓글을 수정 또는 삭제할 권한이 있는 사용자인지 확인한다.
            throw new RuntimeException("ERROR - 해당 댓글에 접근할 권한이 없습니다.");
        }

        entity.updateComment(commentUpdateRequestDto.getContent());
    }

    @Transactional
    @Override
    public void updateRecommend(Long commentId) {  // 해당 commentId의 댓글 추천 기능.

        Comment entity = commentJpaRepository.findById(commentId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 commentId의 댓글 조회 실패"));

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() == entity.getUser().getId()) {
            // 헤더 token의 userId와 comment의 userId가 일치하는지 확인하여,
            // 접속한 사용자와 댓글을 작성했던 사용자가 일치하는지 확인하고
            // 만약 동일 사용자라면 본인이 본인 작성댓글에 대해서는 추천을 누를수 없도록 예외처리 시킨다.
            throw new RuntimeException("ERROR - 본인이 작성한 댓글에는 추천을 누를 수 없습니다.");
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
    public void deleteComment(Long commentId) {  // 해당 commentId의 댓글 삭제 기능.

        Comment entity = commentJpaRepository.findById(commentId).orElseThrow(
                ()->new RuntimeException("ERROR - 해당 commentId의 댓글 조회 실패"));

        UserResponseDto securityUserResponseDto = getMyInfoBySecurity();  // 헤더의 User data 가져옴.
        if (securityUserResponseDto.getId() != entity.getUser().getId()) {
            // 헤더 token의 userId와 comment의 userId가 일치하는지 확인하여,
            // 해당 댓글을 작성했던 사용자가 맞는지 확인하고 이를통해, 댓글을 수정 또는 삭제할 권한이 있는 사용자인지 확인한다.
            throw new RuntimeException("ERROR - 해당 댓글에 접근할 권한이 없습니다.");
        }

        commentJpaRepository.delete(entity);
    }
}
