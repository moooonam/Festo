package com.example.festo.order.adapter.out.persistence;

import com.example.festo.order.domain.Order;
import com.example.festo.order.domain.OrderNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public interface OrderRepository extends JpaRepository<Order, OrderNo> {

    default OrderNo nextOrderNo() {
        int randomNo = ThreadLocalRandom.current()
                                        .nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
        return new OrderNo(number);
    }
}
