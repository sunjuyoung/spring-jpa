package com.example.datajpa.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString(exclude = "team")
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String email;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    protected Member(){
    }

    public Member(String username, String email, int age,Team team) {
        this.username = username;
        this.email = email;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }

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
