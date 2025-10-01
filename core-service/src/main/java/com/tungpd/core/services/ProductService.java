package com.tungpd.core.services;

import com.tungpd.core.documents.ProductDocument;
import com.tungpd.core.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDocument> search(ProductDTO productDTO);
}
