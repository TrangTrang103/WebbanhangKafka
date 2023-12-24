package com.doan.mutual.entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product extends BaseEntity{

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code" , length = 100, nullable = false,unique = true)
    private String code;

    @Column(name = "description", nullable = true)
    private String description;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
   private List<ProductImage> productImage = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "product_size",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private List<Size> sizes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "import_price" , nullable = false , length = 40)
    private Double importPrice ;

    @Column(name = "sale_price", nullable = false, length = 40)
    private Double salePrice;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "sold", nullable = true)
    private Long sold;

    private Date createdTime;

    private Date updatedTime;

    private String createdBy;

    private String updatedBy;

}
