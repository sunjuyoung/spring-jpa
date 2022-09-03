package com.example.datajpa.querydsl;

import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.datajpa.entity.QMember.member;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslTests {

    @Autowired
    EntityManager em;

    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);
    }


    @Test
    public void page() throws Exception{
        //given

        //when

        //then
    }

    @Test
    public void sort() throws Exception{
        //given
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .where(member.age.between(20, 30))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();


        Member member = fetch.get(0);
        System.out.println(member.getAge());

        //when

        //then
    }

    @Test
    public void search() throws Exception{
        //given

        Member member = queryFactory
                .select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.username.eq("test10"))
                .fetchOne();

        assertThat(member.getUsername()).isEqualTo("test10");

        /*member.username.eq("member1") // username = 'member1'
member.username.ne("member1") //username != 'member1'
member.username.eq("member1").not() // username != 'member1'
member.username.isNotNull() //이름이 is not null
member.age.in(10, 20) // age in (10,20)
member.age.notIn(10, 20) // age not in (10, 20)
member.age.between(10,30) //between 10, 30
member.age.goe(30) // age >= 30
member.age.gt(30) // age > 30
member.age.loe(30) // age <= 30
member.age.lt(30) // age < 30
member.username.like("member%") //like 검색
member.username.contains("member") // like ‘%member%’ 검색
member.username.startsWith("member") //like ‘member%’ 검*/

        //when

        //then
    }

    @Test
    public void contextLoad(){
        JPAQueryFactory query = new JPAQueryFactory(em);
        List<Member> fetch = query
                .select(member)
                .from(member)
                .fetch();




    }
}
