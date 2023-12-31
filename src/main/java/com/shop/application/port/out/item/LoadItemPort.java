package com.shop.application.port.out.item;

import com.shop.adapter.out.persistence.repository.dto.ItemSearch;
import com.shop.adapter.out.persistence.repository.dto.MainItemDto;
import com.shop.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LoadItemPort {
    Optional<Item> loadById(Long id);

    List<Item> loadAllByIn(List<Long> ids);

    Page<MainItemDto> loadMainItem(ItemSearch itemSearchDto, Pageable pageable);

    Page<Item> loadAdminItem(ItemSearch itemSearch, Pageable pageable);
}
