package com.doan.mutual.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter// lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart extends BaseEntity {

	@Column(name = "count", nullable = false)
	private Integer count;

	@Column(name = "size",nullable = true)
	private String size;
	@ManyToOne
	@JoinColumn(name = "customer_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Product product;

}
