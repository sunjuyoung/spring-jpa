package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {


    //같은 트랜잭션 안에서는 같은 entitymanager
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;

    @Test
    public void test(){
        String email = "test@email.com";

        Team t1 = new Team("testTeam1");
        Team t2 = new Team("testTeam2");
        Team t3 = new Team("testTeam3");

        Team save = teamRepository.save(t1);
        Team save1 = teamRepository.save(t2);
        Team save2 = teamRepository.save(t3);


        Member m1 = new Member("test1",email,3,save);
        Member m2 = new Member("test2",email,4,save);
        Member m3 = new Member("test3",email,5,save1);
        Member m4 = new Member("test4",email,6,save1);
        Member m5 = new Member("test5",email,7,save2);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);
        memberRepository.save(m5);

        List<Member> byAgeGreaterThan = memberRepository.findByAgeGreaterThan(4);
        for(Member member : byAgeGreaterThan){
            System.out.println(member);
        }
        List<Member> user = memberRepository.findUser(4);
        for(Member member : user){
            System.out.println(member);
        }

        List<String> username = memberRepository.findUsername();
        username.forEach(s -> System.out.println(s));
    }

    @Test
    public void test1() {
        String email = "test@email.com";

        Team t1 = new Team("testTeam1");
        Team t2 = new Team("testTeam2");
        Team t3 = new Team("testTeam3");

        Team save = teamRepository.save(t1);
        Team save1 = teamRepository.save(t2);
        Team save2 = teamRepository.save(t3);

        Member m1 = new Member("test1", email, 3, save);
        Member m2 = new Member("test2", email, 4, save);
        Member m3 = new Member("test3", email, 5, save1);
        Member m4 = new Member("test4", email, 6, save1);
        Member m5 = new Member("test5", email, 7, save2);

        memberRepository.save(m1);
        memberRepository.save(m2);
        memberRepository.save(m3);
        memberRepository.save(m4);
        memberRepository.save(m5);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for(MemberDto memberDto1 : memberDto){
            System.out.println(memberDto1);
        }

        List<Member> byNames = memberRepository.findByNames(Arrays.asList("test1", "test2"));
        for(Member member: byNames){
            System.out.println(member);
        }
    }

    @Test
    @Rollback(value = false)
    public void test2() {
        Team team = teamRepository.findById(4L).orElseThrow();
        Team teamB = teamRepository.findById(5L).orElseThrow();
        for(int i=3; i<20; i++){
            String email = "test"+i+"@email.com";
            String name = "test"+i;
            int age = (int)(Math.random()*30)+1;
            Member m1 = new Member(name, email, age, team);
            memberRepository.save(m1);
        }
        for(int i=20; i<40; i++){
            String email = "test"+i+"@email.com";
            String name = "test"+i;
            int age = (int)(Math.random()*30)+1;
            Member m1 = new Member(name, email, age, teamB);
            memberRepository.save(m1);
        }
    }
    @Test
    public void testPage() {
        int age = 20;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> memberPage = memberRepository.findByAgeGreaterThan(age, pageRequest);

        List<Member> content = memberPage.getContent();
        long total = memberPage.getTotalElements();

        for(Member members : content){
            System.out.println(members);
        }

        System.out.println("total  = " +total );
        System.out.println("totalPage  = " +memberPage.getTotalPages() );
        System.out.println("pageNumber  = " +memberPage.getNumber() );
        System.out.println("  = " +memberPage.hasNext() );
        System.out.println("  = " +memberPage.isFirst() );
    }
    @Test
    public void testPageSlice() {
        int age = 20;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //total , 총 count
        Slice<Member> memberPage = memberRepository.findByAge(age, pageRequest);
        Page<Member> count = memberRepository.findCount(pageRequest);

        List<Member> content = memberPage.getContent();
        //long total = memberPage.getTotalElements();

        Slice<MemberDto> map = memberPage.map(member -> new MemberDto(member.getId(), member.getUsername(), member.getTeam().getName()));

        for(Member members : content){
            System.out.println(members);
        }

       // System.out.println("total  = " +total );
       // System.out.println("totalPage  = " +memberPage.getTotalPages() );
        System.out.println("pageNumber  = " +memberPage.getNumber() );
        System.out.println("  = " +memberPage.hasNext() );
        System.out.println("  = " +memberPage.isFirst() );
    }

    @Test
    public void testUpdate() {
        int resultCount = memberRepository.bulkAgePlus(20);
        System.out.println(resultCount);
    }

    @Test
    public void testUpdate2() {
        Member m1 = new Member("sun", "test99@email.com", 21, null);
        memberRepository.save(m1);

        int resultCount = memberRepository.bulkAgePlus(20);
        //em.flush();  =  clearAutomatically = true
       // em.clear();

        Member member = memberRepository.findByUsername("sun");
        Member test23 = memberRepository.findByUsername("test23");

        //bulk연산은 영속성컨텍스트 무시하고 바로 db에 반영
        //bulk연산 이후 남아있는 영속성컨텍스 날려버려야한다
        //

        System.out.println(member.getAge());//22
        System.out.println(test23.getAge());//27
    }



}