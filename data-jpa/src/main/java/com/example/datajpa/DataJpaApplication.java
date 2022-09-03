package com.example.datajpa;

import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import com.example.datajpa.repository.MemberRepository;
import com.example.datajpa.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing
@SpringBootApplication
public class DataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}


	@Bean
	public AuditorAware<String> auditor(){
		//세션 이나 시큐리티 컨텍스트 등 사용자 ㅇ
		return () -> Optional.of(UUID.randomUUID().toString());
	}

	@Bean
	CommandLineRunner run(MemberRepository memberRepository){
		return args -> {


		};
	}


}
