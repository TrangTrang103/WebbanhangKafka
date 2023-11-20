package com.doan.mutual.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shipping")
public class Shipping extends BaseEntity{
    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private String status ;

    @OneToOne
    @MapsId
    @JoinColumn(name = "cart_id")
    private Order order;
}
