package com.umsinsa.solvingproblemspringproject.controller;

import com.umsinsa.solvingproblemspringproject.dto.category.CategoryResponseDto;
import com.umsinsa.solvingproblemspringproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor  // 이걸로 private final 되어있는걸 자동으로 생성자 만들어줘서 @Autowired와 this 없이 의존관계 DI 주입시켜줌.
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/categories")  // 헤더 토큰정보 불필요함 X
    public List<CategoryResponseDto> findAllCategories() {  // 모든 카테고리 목록들 조회
        List<CategoryResponseDto> categoryResponseDtos = categoryService.findAllCategories();
        return categoryResponseDtos;
    }

}
