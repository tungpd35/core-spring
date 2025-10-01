//package com.tungpd.core.config;
//
//import com.github.javafaker.Faker;
//import com.tungpd.core.documents.ProductDocument;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.stereotype.Component;
//
//import java.util.Locale;
//import java.util.UUID;
//
//@Component
//public class ProductSeeder implements CommandLineRunner {
//
//    @Autowired
//    private ElasticsearchOperations elasticsearchOperations;
//
//    @Override
//    public void run(String... args) {
//        Faker faker = new Faker(new Locale("vi"));
//        for (int i = 0; i < 10000; i++) {
//            try {
//                ProductDocument product = new ProductDocument();
//                product.setId(UUID.randomUUID().toString());
//                product.setName(faker.commerce().productName());
//                product.setPrice(Double.valueOf(faker.commerce().price()));
//                product.setDescription(faker.lorem().sentence(15));
//                product.setStatus((short) faker.number().numberBetween(0, 1));
//
//                elasticsearchOperations.save(product);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("✅ Inserted 10,000 fake products into Elasticsearch");
//    }
//}
