package com.example.datajpa.repository;

import com.example.datajpa.dto.MemberDto;
import com.example.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Long> {

    List<Member> findByAgeGreaterThan(int age);

    @Query("select m from Member m where m.age >= :age")
    List<Member> findUser(@Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsername();


    //DTO 조회하기
    @Query("select new com.example.datajpa.dto.MemberDto(m.id,m.username,t.name)  from Member m join m.team t ")
    List<MemberDto> findMemberDto();

    //반환값 없을때  컬렉션은 빈 컬렉션, 단일값은 null
    //컬렉션 파라미터 바인딩
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);


    Page<Member> findByAgeGreaterThan(int age, Pageable pageable);

    @Query("select m from Member m where m.age >= :age")
    Slice<Member> findByAge(@Param("age") int age, Pageable pageable);

    //count 값을 구할때 join 적용이 필요 없을 시 성능 최적화를 위해 countQuery 분리
    @Query(value="select m from Member m left join m.team t",
            countQuery="select count(m) from Member m")
    Page<Member> findCount(Pageable pageable);


    @Modifying
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


}
