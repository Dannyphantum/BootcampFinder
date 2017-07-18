package bootcampFinder.repositories;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.App;

public interface AppRepository extends CrudRepository<App, Long> {
    boolean existsByUserId(long userId);

    App findOneByUserId(long userId);
}
