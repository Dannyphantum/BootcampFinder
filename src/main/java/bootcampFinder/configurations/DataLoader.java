package bootcampFinder.configurations;

import bootcampFinder.models.App;
import bootcampFinder.models.Bootcamp;
import bootcampFinder.models.Role;
import bootcampFinder.models.User;
import bootcampFinder.repositories.AppRepository;
import bootcampFinder.repositories.BootcampRepository;
import bootcampFinder.repositories.RoleRepository;
import bootcampFinder.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner{
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BootcampRepository bootcampRepository;
    private AppRepository appRepository;

    @Autowired
    public DataLoader(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository
            ,BootcampRepository bootcampRepository, AppRepository appRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bootcampRepository = bootcampRepository;
        this.appRepository = appRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

/*
        //only do if database hasn't been built yet
        //if (!roleRepository.existsByRole("ADMIN")) {

            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("STUDENT"));
            roleRepository.save(new Role("DIRECTOR"));

            Role adminRole = roleRepository.findByRole("ADMIN");
            Role studentRole = roleRepository.findByRole("STUDENT");
            Role directorRole = roleRepository.findByRole("DIRECTOR");

            //hard coded student user: student, password: password
            User user = new User("Dummy", "student", "student@name.com", "password", "1234 street st.", "cityville", "CA", "90210", true, new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            App app = new App();
            app.makeNew(user.getUserName());
            appRepository.save(app);
            user.setRoles(Arrays.asList(studentRole));
            userRepository.save(user);

            //hard coded ADMIN user: ADMIN, password: password
            user = new User("ADMIN", "ADMIN", "ADMIN@ADMIN.ADMIN", "password", "1337 ADMIN st.", "ADMINville", "ADMIN", "11011", true, new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList(adminRole));
            userRepository.save(user);

            //hard coded director user: director, password: password
            user = new User("sir director", "director", "director@ADMIN.dir", "password", "666 evil st.", "evilcity", "MD", "666", true, new Date());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Bootcamp camp = new Bootcamp();
            camp.makeNew(user.getUserName());
            bootcampRepository.save(camp);
            user.setRoles(Arrays.asList(directorRole));
            userRepository.save(user);

        userRepository.save(userRepository.findOneByUserName("student").setRole("student"));
        userRepository.save(userRepository.findOneByUserName("director").setRole("director"));
        userRepository.save(userRepository.findOneByUserName("ADMIN").setRole("admin"));
*/
    }
    //}
}