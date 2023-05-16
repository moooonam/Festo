package com.example.festo.alert.adapter.out.persistence;

import com.example.festo.booth.adapter.out.persistence.BoothEntity;
import com.example.festo.festival.adapter.out.persistence.FestivalEntity;
import com.example.festo.member.domain.Member;
import com.example.festo.order.adapter.out.persistence.OrderEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private long timeStamp;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;


    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name =  "booth_id")
    private BoothEntity booth;

    @ManyToOne
    @JoinColumn(name =  "festival_id")
    private FestivalEntity festival;

    @Builder
    public NotificationEntity(Long id, String content, long timeStamp, Member receiver, OrderEntity order, BoothEntity booth, FestivalEntity festival) {
        this.id = id;
        this.content = content;
        this.timeStamp = timeStamp;
        this.receiver = receiver;
        this.order = order;
        this.booth = booth;
        this.festival = festival;
    }
}
