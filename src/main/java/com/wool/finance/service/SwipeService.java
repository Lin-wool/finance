package com.wool.finance.service;

import com.wool.finance.entity.Swipe;
import com.wool.finance.repository.SwipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SwipeService {
    @Autowired
    private SwipeRepository swipeRepository;


    /**
     * 通过订单ID查找订单
     *
     * @param orderId
     * @return
     */
    public Swipe findByOrderId(String orderId) {
        return swipeRepository.findByOrderId(orderId);
    }

    public Page<Swipe> findAll(Specification<Swipe> spec, PageRequest pageRequest) {
        return swipeRepository.findAll(spec,pageRequest);
    }
}
