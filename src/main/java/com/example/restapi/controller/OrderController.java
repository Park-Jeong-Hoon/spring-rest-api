package com.example.restapi.controller;

import com.example.restapi.auth.PrincipalDetails;
import com.example.restapi.dto.OrderDto;
import com.example.restapi.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveOrder(@RequestBody List<Long> itemIdList, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long memberId = principalDetails.getMember().getId();
        String result = "success";

        try {
            orderService.saveOrder(memberId, itemIdList);
        } catch (Exception e) {
            e.printStackTrace();
            result = "fail";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable Long id) {

        OrderDto orderDto = orderService.getById(id);

        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getOrderDtoList(@AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long memberId = principalDetails.getMember().getId();
        List<OrderDto> orderDtoList = orderService.getOrderDtoList(memberId);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }
}
