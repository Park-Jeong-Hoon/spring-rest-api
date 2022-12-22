package com.example.restapi.service;

import com.example.restapi.dto.ItemDto;
import com.example.restapi.model.Item;
import com.example.restapi.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public Long add(ItemDto itemDto) { // 아이템 추가

        Item item = new Item();
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        itemRepository.save(item);

        return item.getId();
    }

    @Transactional
    public void update(ItemDto itemDto) { // 아이템 수정

        Item item = itemRepository.findById(itemDto.getId()).get();
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
    }

    public ItemDto getById(Long id) { // 아이템 조회

        Optional<Item> itemOptional = itemRepository.findById(id);

        if (itemOptional.isEmpty()) {
            return null;
        }

        Item item = itemOptional.get();
        ItemDto itemDto = new ItemDto(item.getId(), item.getName(), item.getPrice());

        return itemDto;
    }

    public List<ItemDto> getItemDtoList() {

        List<ItemDto> itemDtoList = itemRepository.getItemDtoList();

        return itemDtoList;
    }
}
