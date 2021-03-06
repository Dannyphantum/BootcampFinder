package bootcampFinder.configurations;

/**
 * Created by Orion Wolf_Hubbard on 7/18/2017.
 */
import bootcampFinder.models.Role;
import bootcampFinder.models.User;
import bootcampFinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    public SSUserDetailsService (UserRepository userRepository) {
        this.userRepository  = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUserName(s);
            if (user == null)
                return null;
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), getAuthorities(user));
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role : user.getRoles())
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        return authorities;
    }

}
