package bootcampFinder.repositories;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.Bootcamp;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BootcampRepository extends CrudRepository<Bootcamp, Long> {
    List<Bootcamp> findByZipCode(long zip);
    List<Bootcamp> findByBootcampId(long id);
}
