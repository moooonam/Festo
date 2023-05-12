package com.example.festo.alert.adapter.out.persistence;

import com.example.festo.alert.application.port.out.LoadNotificationPort;
import com.example.festo.alert.application.port.out.SaveNotificationPort;
import com.example.festo.alert.domain.BoothInfo;
import com.example.festo.alert.domain.FestivalInfo;
import com.example.festo.alert.domain.Notification;
import com.example.festo.common.exception.CustomNoSuchException;
import com.example.festo.common.exception.ErrorCode;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import com.example.festo.order.adapter.out.persistence.OrderEntity;
import com.example.festo.order.adapter.out.persistence.OrderRepository;
import com.example.festo.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationAdapter implements LoadNotificationPort, SaveNotificationPort {

    private final NotificationRepository notificationRepository;

    private final OrderRepository orderRepository;

    private final MemberRepository memberRepository;

    @Override
    public List<Notification> loadAllByReceiverId(Long receiverId) {
        List<NotificationEntity> notificationEntities = notificationRepository.findAllByReceiverIdOrderByTimeStampDesc(receiverId);

        return notificationEntities.stream().map(this::mapToNotificationDomain).collect(Collectors.toList());
    }

    @Override
    public Notification saveNotification(Long memberId, Long orderId, long timestamp) {
        Member receiver = memberRepository.findById(memberId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.MEMBER_NOT_FOUND));
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new CustomNoSuchException(ErrorCode.ORDER_NOT_FOUND));
        OrderStatus orderStatus = order.getOrderStatus();

        NotificationEntity notificationEntity = NotificationEntity.builder()
                                                            .receiver(receiver)
                                                            .order(order)
                                                            .booth(order.getBooth())
                                                            .festival(order.getBooth().getFestival())
                                                            .timeStamp(timestamp)
                                                            .content(orderStatus.getMessage())
                                                            .build();
        notificationEntity = notificationRepository.save(notificationEntity);

        return mapToNotificationDomain(notificationEntity);
    }

    private Notification mapToNotificationDomain(NotificationEntity notificationEntity) {
        FestivalInfo festivalInfo = new FestivalInfo(notificationEntity.getFestival().getFestivalId(), notificationEntity.getFestival().getName());
        BoothInfo boothInfo = new BoothInfo(notificationEntity.getBooth().getBoothId(), notificationEntity.getBooth().getName());

        return new Notification(notificationEntity.getId(), notificationEntity.getReceiver().getId(), notificationEntity.getContent(), festivalInfo, boothInfo, notificationEntity.getTimeStamp());
    }
}
