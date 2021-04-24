package br.com.llealcruz.SpringSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity /*Essa anotação, em conjunto com a herança WebSecurityConfigurerAdapter, traz para a classe algumas configurações relacionadas à segurança de aplicações web com o Spring Security, assim como viabiliza a integração com o Spring MVC.*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /*Com o método configure() indicamos ao Spring Security o que ele deve fazer com as requisições HTTP direcionadas à nossa aplicação.*/
    @Override
    protected void configure(HttpSecurity http) throws Exception { /*O Spring já nos fornece uma instância de HttpSecurity por parâmetro */
        http.
                authorizeRequests()
                .antMatchers("/webjars/**").permitAll()/*Permite que os componentes do bootstrap sejam carregados na página de Login*/
                .antMatchers("/dados-acesso").hasAnyRole("EDITOR")
                .antMatchers("/lista-usuarios").hasAnyRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .rememberMe();  /*Cria um cookie "remember-me" que expira em duas semanas por padrão.*/
    }

    /*Fornece a opção de configurar usuários em memória, definindo o login, a senha, assim como os papéis que esse usuário possui na aplicação*/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder)
            throws Exception {
        builder
                .inMemoryAuthentication()
                .withUser("lua.cruz").password("$2a$10$XdOoWYcm.88x1eiftLFI1eM.6UVykTmutUZt7x3qFUI.5D7YF2Fzm").roles("EDITOR", "ADMIN")
                .and()
                .withUser("anna.araujo").password("$2a$10$XdOoWYcm.88x1eiftLFI1eM.6UVykTmutUZt7x3qFUI.5D7YF2Fzm").roles("EDITOR");

        /*Além desse metódo é necessário definir um PasswordEncoder (este projeto não se aprofunda nesse tema, utiliza apenas o básico para o funcionamento)*/
    }


    /*Cria uma instância do encoder BCrypt e deixa o controle dessa instância como responsabilidade do Spring. Agora, sempre que o Spring Security necessitar disso, ele já terá o que precisa configurado.*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
