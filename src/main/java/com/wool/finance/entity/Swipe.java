package com.wool.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Swipe {
    @Id
    private Long id;

    private Long orderId;

    private Long asin;

    private Double price;

    private Double commission;

    private String productName;

    private String description;

    private Date orderDate;

    private Date rebateDate;

}
