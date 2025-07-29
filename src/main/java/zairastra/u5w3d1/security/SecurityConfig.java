package zairastra.u5w3d1.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

//classe di configurazione per SpringSecurity - creo il filtro a mano in modo da scegliere io cosa far passare e cosa no
//conviene creare un unico filtro da usare ogni volta che serve
@Configuration
@EnableWebSecurity //obbligatoria èER L'AUTENTICAZIONE

@EnableMethodSecurity//obbligatoria per l'AUTORIZZAZIONE
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //DISABILITO COMPORTAMENTO DI DEFAULT - BLOCCA TUTTO
        httpSecurity.formLogin(formLogin -> formLogin.disable());//FORM DI LOGIN
        httpSecurity.csrf(csrf -> csrf.disable());//questa non ho capito cosa sia
        //per Spring devo settarla su stateless, non c'è sessione
        httpSecurity.sessionManagement((sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)));
        //per personalizzare gli errori e permettere di passare senza essere bloccati gli endopoint che seguono authorized
        httpSecurity.authorizeHttpRequests((hauthorized -> hauthorized.requestMatchers("/**").permitAll()));
        
        return httpSecurity.build();
    }

}
