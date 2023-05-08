package com.example.festo.order.adapter.out.persistence;

import com.example.festo.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("select count(o) + 1 from OrderEntity o where o.booth.id = :boothId")
    int nextOrderNo(@Param("boothId") Long boothId);

    List<OrderEntity> findOrdersByOrdererId(Long ordererId);
}