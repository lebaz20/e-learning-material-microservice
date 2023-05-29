package com.hnj.inventory.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "product_id")
    @NotNull
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;
}
