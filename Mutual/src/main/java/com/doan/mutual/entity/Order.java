package com.doan.mutual.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


@Entity
@Getter
@Setter// lombok giúp generate các hàm constructor, get, set v.v.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`order`")
public class Order extends BaseEntity {

	@Column(name = "total")
	private Double total;
	
	@Column(name = "booking_Date")
	private Date bookingDate;
	
	@Column(name = "payment_Method", columnDefinition = "nvarchar(1111)")
	private String paymentMethod;
	
	@Column(name = "status", columnDefinition = "nvarchar(1111)")
	private String status;

	@Column(name = "note", columnDefinition = "nvarchar(1111)")
	private String note;
	
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItem;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToOne(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
	@PrimaryKeyJoinColumn
	private Shipping shipping;
}
