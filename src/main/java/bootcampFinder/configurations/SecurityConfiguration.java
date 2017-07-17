package bootcampFinder.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Daniel on 7/17/17.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity (prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    //AWAITING CREATION OF CLASSES TO AUTOWIRE (USERDETAILSERVICE & USERREPOSITORY)

    @Bean
    public PasswordEncoder encoder(){

        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests().anyRequest().authenticated();

        http    .authorizeRequests()
                .antMatchers("/","/login","/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/home")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
                .and()
                .withUser("root").password("password").roles("ADMIN");
        auth
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(encoder());
    }
}