package com.example.restapi.repository;

import com.example.restapi.dto.ItemDto;
import com.example.restapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("UPDATE Item i SET i.name = :name, i.price = :price WHERE i.id = :id")
    void updateItem(
            @Param(value = "id") Long id,
            @Param(value = "name") String name,
            @Param(value = "price") Long price
    );

    @Query("SELECT new com.example.restapi.dto.ItemDto(i.id, i.name, i.price) FROM Item i")
    List<ItemDto> getItemDtoList();
}
