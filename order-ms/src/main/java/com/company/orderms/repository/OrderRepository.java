package com.company.orderms.repository;

import com.company.orderms.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query(nativeQuery = true, value = "SELECt * FROM order_schema.order_table o WHERE (:userId IS NULL OR CAST(o.user_id AS varchar) = :userId) AND (:courierId IS NULL OR CAST(o.courier_id AS varchar) = :courierId)")
    Page<OrderEntity> findByUserIdAndCourierId(@Param("userId") String userId, @Param("courierId") String courierId, PageRequest pageRequest);
}
