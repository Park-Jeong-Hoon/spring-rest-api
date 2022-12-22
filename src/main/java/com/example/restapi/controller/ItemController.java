package com.example.restapi.controller;

import com.example.restapi.dto.ItemDto;
import com.example.restapi.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {

        this.itemService = itemService;
    }

    @PostMapping("/add") // 아이템 신규 추가 api
    public ResponseEntity<String> add(@RequestBody ItemDto itemDto) {

        String result = "success";

        try {
            itemService.add(itemDto);
        } catch (Exception e) {
            e.printStackTrace();
            result = "fail";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getById(@PathVariable Long id) {

        ItemDto itemDto = itemService.getById(id);

        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemDto>> getAll() {

        List<ItemDto> itemDtoList = itemService.getItemDtoList();

        return new ResponseEntity<>(itemDtoList, HttpStatus.OK);
    }
}