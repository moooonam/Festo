package com.example.festo.global.auth;

import com.example.festo.member.domain.Member;
import com.example.festo.member.adapter.out.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername username: {}", username);
        Member member = memberRepository.findById(Long.parseLong(username)).orElseThrow(() -> new NoSuchElementException("없는 회원입니다."));
        if (member != null) {
            return new CustomUserDetails(member);
        }
        return null;
    }
}

