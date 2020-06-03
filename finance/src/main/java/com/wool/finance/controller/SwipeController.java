package com.wool.finance.controller;

import com.wool.finance.entity.Swipe;
import com.wool.finance.repository.SwipeRepository;
import com.wool.finance.service.SwipeService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class SwipeController {
    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private SwipeService swipeService;

    @GetMapping("/list")
    public Resp list(@RequestParam(defaultValue = "1") Integer pageIndex,
                     @RequestParam(defaultValue = "10") Integer pageSize,
                     final @RequestParam(defaultValue = "") String asin,
                     final @RequestParam(defaultValue = "") String orderId,
                     final @RequestParam(defaultValue = "") String productName,
                     final @RequestParam(defaultValue = "") String orderDate,
                     final @RequestParam(defaultValue = "") String rebateDate) {
        PageRequest pageRequest = PageRequest.of(pageIndex - 1, pageSize, Sort.Direction.DESC, "orderDate");

        Specification<Swipe> spec = new Specification<Swipe>() {
            List<Predicate> list = new ArrayList();

            @Override
            public Predicate toPredicate(Root<Swipe> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (StringUtils.isNotBlank(asin)) {
                    list.add(criteriaBuilder.equal(root.get("asin").as(String.class), asin));
                }
                if (StringUtils.isNotBlank(orderId)) {
                    list.add(criteriaBuilder.equal(root.get("orderId").as(String.class), orderId));
                }
                if (StringUtils.isNotBlank(productName)) {
                    list.add(criteriaBuilder.like(root.get("productName").as(String.class), "%" + productName + "%"));
                }
                if (StringUtils.isNotBlank(orderDate)) {
                    Date orderDateBegin = null;
                    Date orderDateEnd = null;
                    try {
                        orderDateBegin = DateUtils.parseDate(orderDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                        orderDateEnd = DateUtils.parseDate(orderDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (orderDateBegin != null && orderDateEnd != null) {
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("orderDate").as(Date.class), orderDateBegin));
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("orderDate").as(Date.class), orderDateEnd));
                    }
                }
                if (StringUtils.isNotBlank(rebateDate)) {
                    Date rebateDateBegin = null;
                    Date rebateDateEnd = null;
                    try {
                        rebateDateBegin = DateUtils.parseDate(rebateDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                        rebateDateEnd = DateUtils.parseDate(rebateDate + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (rebateDateBegin != null && rebateDateEnd != null) {
                        list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rebateDate").as(Date.class), rebateDateBegin));
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("orderDate").as(Date.class), rebateDateEnd));
                    }
                }
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<Swipe> swipes = swipeService.findAll(spec, pageRequest);
        return Resp.success(swipes);
    }

    @PostMapping("/save")
    public Resp save(@RequestBody Swipe swipe) {
        // 判断是否存在id
        if (swipe.getId() != null) {
            // 修改订单
            Swipe one = swipeRepository.getOne(swipe.getId());
            if (one == null) {
                return Resp.fail("未找到该订单！");
            }
            Swipe original = new Swipe();
            BeanUtils.copyProperties(one, original);
            swipeRepository.save(swipe);
            System.out.println("修改订单，修改前订单信息为：" + original + " ------ 修改后订单信息为：" + swipe);
        } else {
            // 添加订单
            Swipe orderId = swipeService.findByOrderId(swipe.getOrderId());
            if (orderId != null) {
                // 以存在该订单
                return Resp.fail("已存在该订单，请根据订单号查询！");
            }
            Swipe save = swipeRepository.save(swipe);
            System.out.println("新增订单：" + swipe);
        }
        return Resp.success();
    }

}
