package com.el.material.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaterialRequest {
    private String resourceType;
    private String resourceId;
    private String url;
}
