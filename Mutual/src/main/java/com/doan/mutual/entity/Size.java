package com.doan.mutual.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "size")
public class Size extends BaseEntity {

    @Column(name = "name_size")
    private String nameSize;


    @ManyToMany(mappedBy = "sizes")
    private List<Product> products = new ArrayList<>();


}
