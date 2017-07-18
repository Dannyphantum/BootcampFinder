package bootcampFinder.configurations;

import bootcampFinder.models.Role;
import bootcampFinder.models.User;
import bootcampFinder.repositories.RoleRepository;
import bootcampFinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Arrays;

@Service
public class UserService {
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RoleRepository roleRepository;
    Role adminRole;
    Role studentRole;
    Role directorRole;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        adminRole = roleRepository.findByRole("ADMIN");
        studentRole = roleRepository.findByRole("STUDENT");
        directorRole = roleRepository.findByRole("DIRECTOR");
    }

    public User findByEmail(String email) { return userRepository.findByEmail(email); }
    public int countByEmail(String email) { return userRepository.countByEmail(email); }
    public User findByUsername(String username){ return userRepository.findByUserName(username); }

    public void saveStudent(User user) {
        user.setRoles(Arrays.asList(studentRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveDirector(User user) {
        user.setRoles(Arrays.asList(directorRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList(adminRole));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}