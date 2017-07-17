package bootcampFinder.repositories;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.Bootcamp;
import org.springframework.data.repository.CrudRepository;

public interface BootcampRepository extends CrudRepository<Bootcamp, Long> {
}
