package bootcampFInder.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.AntPathMatcher;

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
    protected void configure(HttpsSecurity http) throws Exception {

        http
                .authorizeRequests().anyRequest().authenticated();

        http
                .antMatchers("/","/login","/search").permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .and
                .logout().logoutRequestMatcher(new AntPathMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }

    @Override
    protected void configure(AuthencationManagerBuilder auth)throws Exception
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
