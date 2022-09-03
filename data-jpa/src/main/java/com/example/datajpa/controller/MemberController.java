package com.example.datajpa.controller;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size = 5, sort = "username") Pageable pageable){
        Page<Member> all = memberRepository.findCount(pageable);
       /* Page<MemberDto> map = all.map(member -> new MemberDto(member.getId(), member.getUsername(), null));*/
        Page<MemberDto> map = all.map(MemberDto::new);
        return map;
    }

}
