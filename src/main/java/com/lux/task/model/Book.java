package com.lux.task.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id;
    public String name;
    public String author;
    public String publication;
    public String category;
    public long pages;
    public double price;
}
