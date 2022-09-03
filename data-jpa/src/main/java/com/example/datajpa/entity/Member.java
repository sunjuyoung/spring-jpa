package com.example.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@ToString(of = {"username", "email","age"})
//@NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member extends BaseTimeEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String email;
    private int age;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    protected Member(){
    }

    public Member(String username,int age){
        this.username = username;
        this.age = age;
    }

    public Member(String username, String email, int age,Team team) {
        this.username = username;
        this.email = email;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }

    }

    public void changeUserName(String username){
        this.username = username;
    }

    public Member(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMember().add(this);
    }

}
