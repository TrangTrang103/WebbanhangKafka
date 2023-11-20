package com.doan.mutual.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ClickCart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClickCart extends BaseEntity{
    @Column(name = "idProduct")
    private String idProduct;
}
