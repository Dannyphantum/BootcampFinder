package bootcampFinder.repositories;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.Application;

public interface ApplicationRepository extends CrudRepository<Application, Long> {
}
