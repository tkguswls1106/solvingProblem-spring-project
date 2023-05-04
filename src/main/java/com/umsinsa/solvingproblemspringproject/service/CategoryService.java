package com.umsinsa.solvingproblemspringproject.service;

import com.umsinsa.solvingproblemspringproject.dto.category.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> findAllCategories();  // 모든 카테고리들 목록 조회해서 이름 오름차순 정렬 기능.
}
