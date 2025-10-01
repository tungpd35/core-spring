package com.tungpd.core.services.impl;

import co.elastic.clients.json.JsonData;
import com.tungpd.core.documents.ProductDocument;
import com.tungpd.core.dto.ProductDTO;
import com.tungpd.core.entities.ProductEntity;
import com.tungpd.core.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Page<ProductDocument> search(ProductDTO productDTO) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("name", productDTO.getKeyword()).fuzziness(Fuzziness.AUTO))
//                .should(QueryBuilders.matchPhraseQuery("description", productDTO.getDescription()))
                .mustNot(QueryBuilders.termQuery("status", 0));

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withPageable(PageRequest.of(productDTO.getPage(), productDTO.getLength()));

        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        SearchHits<ProductDocument> searchHits =
                elasticsearchOperations.search(searchQuery, ProductDocument.class);
        List<ProductDocument> results = searchHits.stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());

        return new PageImpl<>(
                results,
                PageRequest.of(productDTO.getPage(), productDTO.getLength()),
                searchHits.getTotalHits()
        );
    }
}
