package bootcampFinder.repositories;

import org.springframework.data.repository.CrudRepository;

import bootcampFinder.models.User;

/**
 * Created by student on 7/17/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
