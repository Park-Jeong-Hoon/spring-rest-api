package com.example.restapi.repository;

import com.example.restapi.dto.ItemDto;
import com.example.restapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT new com.example.restapi.dto.ItemDto(i.id, i.name, i.price) FROM Item i")
    List<ItemDto> getItemDtoList();
}
