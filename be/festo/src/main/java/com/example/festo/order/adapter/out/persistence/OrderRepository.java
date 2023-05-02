package com.example.festo.order.adapter.out.persistence;

import com.example.festo.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select count(o) + 1 from Order o where o.boothId = :boothId")
    int nextOrderNo(@Param("boothId") Long boothId);
}
