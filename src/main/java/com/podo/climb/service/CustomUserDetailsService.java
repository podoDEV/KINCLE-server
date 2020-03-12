package com.podo.climb.service;

import com.podo.climb.entity.Member;
import com.podo.climb.entity.MemberRole;
import com.podo.climb.model.MemberRoleType;
import com.podo.climb.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmailAddress(emailAddress);
        if (member == null) {
            throw new UsernameNotFoundException("Not found member : " + emailAddress);
        }

        Long memberId = member.getMemberId();
        String password = member.getPassword();
        List<GrantedAuthority> authorities = member.getMemberRole().stream().map(MemberRole::getRole)
                                                   .map(MemberRoleType::toString)
                                                   .map(SimpleGrantedAuthority::new)
                                                   .collect(Collectors.toList());

        return new User(String.valueOf(memberId), password, authorities);
    }

}
