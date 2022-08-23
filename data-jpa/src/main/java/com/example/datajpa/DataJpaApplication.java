package com.example.datajpa;

import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import com.example.datajpa.repository.MemberRepository;
import com.example.datajpa.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}

	@Bean
	CommandLineRunner run(MemberRepository memberRepository){
		return args -> {


		};
	}


}
