package com.soa.shop.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCart {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "product_ids")
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> productIds;
}
