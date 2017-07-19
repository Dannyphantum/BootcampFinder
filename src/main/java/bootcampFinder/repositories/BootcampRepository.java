package bootcampFinder.repositories;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.Bootcamp;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface BootcampRepository extends CrudRepository<Bootcamp, Long> {
    boolean existsByBootcampDirector(String userName);
    Bootcamp findOneByBootcampDirector(String userName);

    Bootcamp findByBootcampId(long bootcampId);

    List<Bootcamp> findByZipCode(long i);

    List<Bootcamp> findAllByDescriptionContaining(String search);

    Bootcamp[] findAllByTopicsContaining(String bootcampName);
}
