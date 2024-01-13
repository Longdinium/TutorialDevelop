package com.techacademy;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    /** 認証・認可設定 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login // 一つ目の設定：ログイン
            .loginProcessingUrl("/login")    // ユーザー名・パスワードの送信先
            .loginPage("/login")             // ログイン画面
            .defaultSuccessUrl("/user/list") // ログイン成功後のリダイレクト先
            .failureUrl("/login?error")      // ログイン失敗時のリダイレクト先
            .permitAll()                     // ログイン画面は未ログインでアクセス可
        ).logout(logout -> logout // 二つ目の設定：ログアウト
            .logoutSuccessUrl("/login")      // ログアウト後のリダイレクト先
        ).authorizeHttpRequests(auth -> auth // 三つ目の設定：認可（アクセス制御）
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .permitAll()                 // css等は未ログインでアクセス可
            .anyRequest().authenticated()    // その他はログイン必要
        );

        return http.build();
    }

    /** ハッシュ化したパスワードの比較に使用する */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}