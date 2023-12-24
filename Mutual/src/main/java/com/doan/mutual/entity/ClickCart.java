package com.doan.mutual.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "click_cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClickCart extends BaseEntity{
    public ClickCart(String idProduct) {
        this.idProduct = idProduct;
    }

    @Column(name = "idProduct")
    private String idProduct;

    @Column(name = "create_time")
    private String createTime;
}
