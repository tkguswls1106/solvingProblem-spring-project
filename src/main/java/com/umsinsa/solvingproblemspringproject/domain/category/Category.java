package com.umsinsa.solvingproblemspringproject.domain.category;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "Category")
@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @Lob
    @Column(name = "category_image", columnDefinition = "MEDIUMBLOB default null")
    private byte[] image;


    @Builder
    public Category(Long id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
