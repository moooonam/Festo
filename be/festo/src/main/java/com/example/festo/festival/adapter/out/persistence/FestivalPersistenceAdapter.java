package com.example.festo.festival.adapter.out.persistence;

import com.example.festo.festival.application.port.out.SaveFestivalCommand;
import com.example.festo.festival.application.port.out.SaveFestivalPort;
import com.example.festo.festival.domain.FestivalStatus;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FestivalPersistenceAdapter implements SaveFestivalPort {
    private final MemberRepository memberRepository;
    private final FestivalRepository festivalRepository;
    @Override
    public Long saveFestival(SaveFestivalCommand saveFestivalCommand, Long managerId) {
        Member manager = memberRepository.findById(managerId).orElseThrow(NoSuchElementException::new);

        String inviteCode=randomCode();

        FestivalEntity festivalEntity = FestivalEntity.builder()
                .name(saveFestivalCommand.getFestivalName())
                .description(saveFestivalCommand.getDescription())
                .address(saveFestivalCommand.getLocation())
                .startDate(saveFestivalCommand.getStartDate())
                .endDate(saveFestivalCommand.getEndDate())
                .imageUrl(saveFestivalCommand.getImageUrl())
                .inviteCode(inviteCode)
                .manager(manager)
                .festivalStatus(FestivalStatus.CLOSE)
                .build();

        FestivalEntity loadFestivalEntity = festivalRepository.save(festivalEntity);

        return loadFestivalEntity.getId();
    }

    @Override
    public Long updateSetImg(Long festivalId, String imgUrl) {
        FestivalEntity festivalEntity = festivalRepository.findById(festivalId).orElseThrow(NoSuchElementException::new);
        festivalEntity.setImageUrl(imgUrl);
        festivalRepository.save(festivalEntity);
        return festivalEntity.getId();
    }

    private String randomCode(){
        boolean flag =false;
        String uniqueCode="";
        while(!flag){
            Random random = new Random();
            int randomNum = random.nextInt(900000) + 100000; // 100000 ~ 999999 범위의 랜덤한 정수

            uniqueCode = Integer.toString(randomNum);

            if(!festivalRepository.existsByInviteCode(uniqueCode)){
                flag= true;
            }
        }
        return uniqueCode;
    }


}
