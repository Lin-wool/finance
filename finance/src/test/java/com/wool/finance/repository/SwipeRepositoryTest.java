package com.wool.finance.repository;

import com.wool.finance.entity.Swipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SwipeRepositoryTest {

    @Autowired
    private SwipeRepository swipeRepository;

    @Test
    void findAll(){
        List<Swipe> all = swipeRepository.findAll();
        System.out.println(all);
    }
}