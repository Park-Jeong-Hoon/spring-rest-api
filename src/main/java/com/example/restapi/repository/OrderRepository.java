package com.example.restapi.repository;

import com.example.restapi.dto.OrderDto;
import com.example.restapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT new com.example.restapi.dto.OrderDto(o.id, o.price, o.orderDate, o.status) FROM Order o WHERE o.member.id = :memberId")
    List<OrderDto> getOrderDtoList(@Param(value = "memberId") Long memberId);
}
