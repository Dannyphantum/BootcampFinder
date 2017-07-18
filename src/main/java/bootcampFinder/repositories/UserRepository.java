package bootcampFinder.repositories;

import org.springframework.data.repository.CrudRepository;

import bootcampFinder.models.User;

import java.util.List;

/**
 * Created by student on 7/17/17.
 */

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String s);
    User findByEmail(String email);
    int countByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUserName(String username);
    List<User> findByZip(long zip);

    User findOneByUserName(String student);
}
