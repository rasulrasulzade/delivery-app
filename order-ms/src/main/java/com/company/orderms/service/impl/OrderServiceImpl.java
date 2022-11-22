package com.company.orderms.service.impl;

import com.company.orderms.dto.CreateOrderDto;
import com.company.orderms.dto.OrderDto;
import com.company.orderms.dto.OrderListDto;
import com.company.orderms.dto.UpdateDtoRequest;
import com.company.orderms.entity.OrderEntity;
import com.company.orderms.exception.CustomException;
import com.company.orderms.mapstruct.OrderMapStruct;
import com.company.orderms.repository.OrderRepository;
import com.company.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapStruct mapStruct;

    @Override
    public OrderDto createOrder(CreateOrderDto dto) {
        OrderEntity ent = orderRepository.save(mapStruct.mapToEntity(dto));
        return mapStruct.mapToDto(ent);
    }

    @Override
    public OrderDto getOrderById(UUID id) {
        OrderEntity ent = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order not found with id: " + id, HttpStatus.NOT_FOUND));
        return mapStruct.mapToDto(ent);
    }

    @Override
    public OrderDto updateOrder(UUID id, UpdateDtoRequest request) {
        OrderEntity ent = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException("Order not found with id: " + id, HttpStatus.NOT_FOUND));
        if (request.getStatus() != null)
            ent.setStatus(request.getStatus());
        if (request.getCourierId() != null)
            ent.setCourierId(request.getCourierId());
        return mapStruct.mapToDto(orderRepository.save(ent));
    }

    @Override
    public OrderListDto getOrders(Optional<UUID> userId, Optional<UUID> courierId, Optional<Integer> page, Optional<Integer> pageSize, Optional<String> direction, Optional<String> sortBy) {
        Sort.Direction sortOrder = direction.map(d -> {
            if (d.equals("DESC")) return Sort.Direction.DESC;
            else return Sort.Direction.ASC;
        }).orElse(Sort.Direction.ASC);
        PageRequest pageRequest = PageRequest.of(page.orElse(0), pageSize.orElse(10), sortOrder, sortBy.orElse("id"));
        String userIdStr = userId.map(UUID::toString).orElse(null);
        String courierIdStr = courierId.map(UUID::toString).orElse(null);
        Page<OrderEntity> pageData = orderRepository.findByUserIdAndCourierId(userIdStr, courierIdStr, pageRequest);
        return OrderListDto.builder()
                .orders(mapStruct.mapToDtoList(pageData.getContent()))
                .page(pageData.getNumber())
                .pageSize(pageData.getSize())
                .isLast(pageData.isLast())
                .totalCount(pageData.getTotalElements())
                .totalPages(pageData.getTotalPages())
                .build();
    }
}
