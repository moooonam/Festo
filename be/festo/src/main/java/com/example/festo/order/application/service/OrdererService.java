package com.example.festo.order.application.service;

import com.example.festo.member.adapter.out.persistence.MemberRepository;
import com.example.festo.member.domain.Member;
import com.example.festo.order.domain.Orderer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrdererService {

    private final MemberRepository memberRepository;

    public Orderer createOrderer(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(NoSuchElementException::new);

        return new Orderer(member.getId(), member.getNickname());
    }

}
