package com.shop.adapter.out.persistence.adapter;

import com.shop.adapter.out.persistence.ItemEntity;
import com.shop.adapter.out.persistence.mapper.ItemMapper;
import com.shop.adapter.out.persistence.repository.ItemRepository;
import com.shop.adapter.out.persistence.repository.dto.ItemSearch;
import com.shop.adapter.out.persistence.repository.dto.MainItemDto;
import com.shop.application.port.out.item.CommandItemPort;
import com.shop.application.port.out.item.LoadItemPort;
import com.shop.domain.Item;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemPersistenceAdapter
        implements LoadItemPort,
        CommandItemPort {

    private final ItemRepository itemRepository;

    @Override
    public Optional<Item> loadById(Long id) {
        return itemRepository.findById(id)
                .map(ItemMapper::mapToDomain);
    }

    @Override
    public List<Item> loadAllByIn(List<Long> ids) {
        return itemRepository.findAllById(ids)
                .stream()
                .map(ItemMapper::mapToDomain)
                .toList();
    }

    @Override
    public Page<MainItemDto> loadMainItem(ItemSearch itemSearchDto, Pageable pageable) {
        return itemRepository.getMainItemPage(itemSearchDto, pageable);
    }

    @Override
    public Page<Item> loadAdminItem(ItemSearch itemSearch, Pageable pageable) {
        return itemRepository.getAdminItemPage(itemSearch, pageable)
                .map(ItemMapper::mapToDomain);
    }

    @Override
    public Item save(Item item) {
        ItemEntity itemEntity = itemRepository.save(ItemMapper.mapToEntity(item));
        return ItemMapper.mapToDomain(itemEntity);
    }

    @Override
    public void updateStock(Item item) {
        ItemEntity itemEntity = itemRepository.findById(item.getId())
                .orElseThrow(EntityNotFoundException::new);
        itemEntity.changeStockNumber(item.getStockNumber());
    }

    @Override
    public Item updateItem(Item item) {
        ItemEntity itemEntity = itemRepository.findById(item.getId())
                .orElseThrow(EntityNotFoundException::new);
        itemEntity.update(ItemMapper.mapToEntity(item));
        return ItemMapper.mapToDomain(itemEntity);
    }

}
