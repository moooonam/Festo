package com.example.festo.alert.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.member.domain.Member;
import com.example.festo.order.adapter.out.persistence.OrderEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "notification")
@Getter
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String content;

    public long timeStamp;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    public Member receiver;


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    public OrderEntity order;

    @ManyToOne
    @JoinColumn(name =  "booth_id")
    public BoothEntity booth;

    @ManyToOne
    @JoinColumn
    public FestivalEntity festival;
}
