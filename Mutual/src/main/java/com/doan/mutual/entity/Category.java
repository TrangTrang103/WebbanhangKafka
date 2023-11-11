package com.doan.mutual.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {

    @Column(name = "name",length = 255, nullable = false)
    private String name;

    @Column(name = "enable",nullable = true)
    private boolean enable;

    @Column(name = "note",nullable = true)
    private String note;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Product> product = new ArrayList<>();


}
