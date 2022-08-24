package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;

import java.util.List;

/**
 * JPA 직접사용
 * mybatis사용
 * 데이터베이스 커넥션 직접사용
 * queryDsl 등..
 */

/*
* 실무에서는 주로 QueryDSL이나 SpringJdbcTemplate을 함께 사용할 때 사용자 정의
리포지토리 기능 자주 사용

* 항상 사용자 정의 리포지토리가 필요한 것은 아니다. 그냥 임의의 리포지토리를 만들어도 된다.
예를들어 MemberQueryRepository를 인터페이스가 아닌 클래스로 만들고 스프링 빈으로 등록해서
그냥 직접 사용해도 된다. 물론 이 경우 스프링 데이터 JPA와는 아무런 관계 없이 별도로 동작한다
* */
public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
