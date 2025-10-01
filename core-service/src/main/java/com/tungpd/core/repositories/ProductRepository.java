package com.tungpd.core.repositories;

import com.tungpd.core.documents.ProductDocument;
import com.tungpd.core.entities.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<ProductDocument, String> {

}
