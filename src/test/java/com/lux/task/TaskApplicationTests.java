package com.lux.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lux.task.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskApplicationTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() throws JsonProcessingException {
//		1,
//				"name": "",
//				"author": ,
//				"publication": ,
//				"category": ,
//				"pages": 464,
//				"price": 22

        Book cleanCode = Book.builder()
                .id(null)
                .name("Clean Code: A Handbook of Agile Software Craftsmanship")
                .author("Robert C. Martin")
                .publication("Prentice Hall")
                .category("Programming")
                .pages(464)
                .price(22)
                .build();

        String s = objectMapper.writeValueAsString(cleanCode);
        System.out.println(s);
    }

}
