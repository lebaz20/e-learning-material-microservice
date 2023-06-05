package com.el.material.model;

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
@Entity(name = "material")
public class Material {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "resource_type")
    @NotNull
    private String resourceType;

    @Column(name = "resource_id")
    @NotNull
    private String resourceId;

    @Column(name = "url")
    private String url;

    @Column(name = "comments_count", columnDefinition="integer default 0")
    private Integer commentsCount;
}
