package com.hnj.inventory.event;

import lombok.*;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ProductInventoryEvent {
    private Integer productId;
    private Integer quantity;
}
