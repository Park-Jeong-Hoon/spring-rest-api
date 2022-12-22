package com.example.restapi.service;

import com.example.restapi.dto.OrderDto;
import com.example.restapi.model.Item;
import com.example.restapi.model.Member;
import com.example.restapi.model.Order;
import com.example.restapi.model.OrderItem;
import com.example.restapi.repository.ItemRepository;
import com.example.restapi.repository.MemberRepository;
import com.example.restapi.repository.OrderItemRepository;
import com.example.restapi.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, MemberRepository memberRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.memberRepository = memberRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Long saveOrder(Long memberId, List<Long> itemIdList) {

        Member member = memberRepository.findById(memberId).get();
        Long price = 0L;

        Order order = new Order();
        order.setMember(member);

        for (int i = 0; i < itemIdList.size(); i++) {
            Item item = itemRepository.findById(itemIdList.get(i)).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItemRepository.save(orderItem);
            price += item.getPrice();
        }

        order.setPrice(price);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(true);
        orderRepository.save(order);

        return order.getId();
    }

    public OrderDto getById(Long id) {

        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            return null;
        }

        Order order = orderOptional.get();

        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setPrice(order.getPrice());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());

        return orderDto;
    }

    public List<OrderDto> getOrderDtoList(Long memberId) {

        List<OrderDto> orderDtoList = orderRepository.getOrderDtoList(memberId);

        return orderDtoList;
    }
}
