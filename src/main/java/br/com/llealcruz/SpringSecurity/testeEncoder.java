package br.com.llealcruz.SpringSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/*Classe criada apenas para pegar o encode do password dos usuarios cadastrados na classe WebSecurityConfig, o encode sera usado no método configureGlobal da mesma classe.*/
public class testeEncoder {
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
