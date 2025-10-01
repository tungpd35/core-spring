package com.tungpd.core.documents;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "product")
public class ProductDocument {
    @Id
    @Field(type = FieldType.Text, name = "id")
    private String id;
    @Field(name = "name", type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Double, name = "price")
    private Double price;
    @Field(type = FieldType.Text, name = "description")
    private String description;
    @Field(type = FieldType.Short, name = "status")
    private Short status;
}
