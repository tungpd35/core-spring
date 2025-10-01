package com.tungpd.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scala.Product;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends DataTableCriteria implements Serializable {
    private String keyword;
    private String name;
    private String description;
    private Double price;
    private Double minPrice;
    private Double maxPrice;
}
