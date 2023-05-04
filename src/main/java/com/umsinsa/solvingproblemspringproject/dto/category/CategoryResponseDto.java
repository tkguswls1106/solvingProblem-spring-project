package com.umsinsa.solvingproblemspringproject.dto.category;

import com.umsinsa.solvingproblemspringproject.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private String name;
    private byte[] image;

    // DB에서 repository를 통해 조회하거나 가져온 entity(도메인)를 dto로 변환 용도
    public CategoryResponseDto(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.image = entity.getImage();
    }
}
