package com.example.restapi.service;

import com.example.restapi.dto.ItemDto;
import com.example.restapi.model.Item;
import com.example.restapi.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;

    @Test
    public void add() throws Exception {

        // given
        ItemDto itemDto = new ItemDto();
        itemDto.setName("옷");
        itemDto.setPrice(10000L);

        // when
        Long saveId = itemService.add(itemDto);

        // then
        Item item = itemRepository.findById(saveId).get();
        Assertions.assertEquals("옷", item.getName());
        Assertions.assertEquals(10000L, item.getPrice());
    }

    @Test
    public void update() throws Exception {

        // given
        ItemDto saveDto = new ItemDto();
        saveDto.setName("옷");
        saveDto.setPrice(10000L);

        // when
        Long saveId = itemService.add(saveDto);

        ItemDto updateDto = new ItemDto();
        updateDto.setId(saveId);
        updateDto.setName("멋진옷");
        updateDto.setPrice(20000L);
        itemService.update(updateDto);

        // then
        Item item = itemRepository.findById(saveId).get();
        Assertions.assertEquals("멋진옷", item.getName());
        Assertions.assertEquals(20000L, item.getPrice());
    }
}
