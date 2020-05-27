package com.wool.finance.controller;

import com.wool.finance.repository.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class SwipeController {
    @Autowired
    private SwipeRepository swipeRepository;





}
