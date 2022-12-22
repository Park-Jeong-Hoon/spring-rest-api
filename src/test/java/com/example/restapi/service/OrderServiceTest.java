package com.example.restapi.service;

import com.example.restapi.dto.ItemDto;
import com.example.restapi.model.Member;
import com.example.restapi.model.Order;
import com.example.restapi.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired MemberService memberService;
    @Autowired ItemService itemService;

    @Test
    public void saveOrder() throws Exception {

        // given
        Member member = createMember();
        Long itemId = createItem("옷", 10000L);
        List<Long> itemIdList = new ArrayList<>();
        itemIdList.add(itemId);

        // when
        Long orderId = orderService.saveOrder(member.getId(), itemIdList);

        // then
        Order order = orderRepository.findById(orderId).get();
        Assertions.assertEquals(member.getId(), order.getMember().getId());
        Assertions.assertEquals(10000L, order.getPrice());
        Assertions.assertEquals(true, order.getStatus());
    }

    @Test
    public void cancelOrder() throws Exception {

        // given
        Member member = createMember();
        Long itemId = createItem("옷", 10000L);
        List<Long> itemIdList = new ArrayList<>();
        itemIdList.add(itemId);

        // when
        Long orderId = orderService.saveOrder(member.getId(), itemIdList);
        orderService.cancelOrder(orderId);

        // then
        Order order = orderRepository.findById(orderId).get();
        Assertions.assertEquals(member.getId(), order.getMember().getId());
        Assertions.assertEquals(10000L, order.getPrice());
        Assertions.assertEquals(false, order.getStatus());
    }

    Member createMember() {

        Member member = new Member();
        member.setUsername("test");
        member.setPassword("1234");
        member.setName("name");
        memberService.join(member);

        return member;
    }

    Long createItem(String name, Long price) {

        ItemDto itemDto = new ItemDto();
        itemDto.setName(name);
        itemDto.setPrice(price);
        Long id = itemService.add(itemDto);

        return id;
    }
}
