package com.lux.task.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lux.task.serializer.PriceJsonSerializer;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String name;
    private String author;
    private String publication;
    private String category;
    private long pages;
    @JsonSerialize(using = PriceJsonSerializer.class)
    private Double price;
}
