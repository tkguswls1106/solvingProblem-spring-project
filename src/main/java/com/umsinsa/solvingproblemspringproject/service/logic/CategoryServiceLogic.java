package com.umsinsa.solvingproblemspringproject.service.logic;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import com.umsinsa.solvingproblemspringproject.domain.category.CategoryJpaRepository;
import com.umsinsa.solvingproblemspringproject.dto.category.CategoryResponseDto;
import com.umsinsa.solvingproblemspringproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class CategoryServiceLogic implements CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;


    @Transactional(readOnly = true)
    @Override
    public List<CategoryResponseDto> findAllCategories() {  // 모든 카테고리들 목록 조회해서 이름 오름차순 정렬 기능.

        List<Category> categories = categoryJpaRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

        return categories.stream().map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }
}
