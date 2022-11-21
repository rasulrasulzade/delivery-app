package com.company.orderms.mapstruct;

import com.company.orderms.dto.CreateOrderDto;
import com.company.orderms.dto.LocationDto;
import com.company.orderms.dto.OrderDto;
import com.company.orderms.entity.OrderEntity;
import com.company.orderms.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, OrderStatus.class})
public abstract class OrderMapStruct {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "pickupLat", expression = "java(dto.getPickup().getLatitude())")
    @Mapping(target = "pickupLong", expression = "java(dto.getPickup().getLongitude())")
    @Mapping(target = "destinationLat", expression = "java(dto.getDestination().getLatitude())")
    @Mapping(target = "destinationLong", expression = "java(dto.getDestination().getLatitude())")
    @Mapping(target = "status", expression = "java(OrderStatus.NEW)")
    public abstract OrderEntity mapToEntity(CreateOrderDto dto);

    @Mapping(target = "pickupLat", expression = "java(dto.getPickup().getLatitude())")
    @Mapping(target = "pickupLong", expression = "java(dto.getPickup().getLongitude())")
    @Mapping(target = "destinationLat", expression = "java(dto.getDestination().getLatitude())")
    @Mapping(target = "destinationLong", expression = "java(dto.getDestination().getLatitude())")
    public abstract OrderEntity mapDtoToEntity(OrderDto dto);

    @Mapping(target = "pickup", source = "dto", qualifiedByName = "toPickup")
    @Mapping(target = "destination", source = "dto", qualifiedByName = "toDestination")
    public abstract OrderDto mapToDto(OrderEntity dto);

    public abstract List<OrderDto> mapToDtoList(List<OrderEntity> dtoList);

    @Named("toPickup")
    protected LocationDto toPickup(OrderEntity ent) {
        return new LocationDto(ent.getPickupLat(), ent.getPickupLong());
    }

    @Named("toDestination")
    protected LocationDto toDestination(OrderEntity ent) {
        return new LocationDto(ent.getDestinationLat(), ent.getDestinationLong());
    }
}
