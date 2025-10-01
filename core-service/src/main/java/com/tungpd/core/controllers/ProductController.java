package com.tungpd.core.controllers;

import com.tungpd.core.documents.ProductDocument;
import com.tungpd.core.dto.ProductDTO;
import com.tungpd.core.entities.ProductEntity;
import com.tungpd.core.repositories.ProductRepository;
import com.tungpd.core.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @GetMapping("/search")
    public Page<ProductDocument> search(@ModelAttribute ProductDTO productDTO) {
        return productService.search(productDTO);
    }

    @PostMapping("/create")
    public ProductDocument createProduct(@RequestBody ProductDocument product) {
        product.setId(UUID.randomUUID().toString());
        productRepository.save(product);
        return product;
    }

//    @GetMapping("/get-by-name")
//    public List<ProductEntity> createProduct(@RequestParam ProductEntity name) {
//
//        return productRepository.save(name);
//    }


}
