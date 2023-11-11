package com.doan.mutual.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage  extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "url_image")
    private String urlImage;
}
