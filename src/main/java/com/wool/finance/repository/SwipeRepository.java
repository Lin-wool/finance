package com.wool.finance.repository;

import com.wool.finance.entity.Swipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SwipeRepository extends JpaRepository<Swipe, Long>, JpaSpecificationExecutor<Swipe> {

    /**
     * 通过订单ID查找订单
     *
     * @param orderId
     * @return
     */
    Swipe findByOrderId(String orderId);
}
