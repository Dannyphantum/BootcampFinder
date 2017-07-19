package bootcampFinder.repositories;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.Testimonial;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TestimonialRepository extends CrudRepository<Testimonial, Long> {
    Testimonial[] findAllByBootcampId(long id);

    ArrayList<Testimonial> findAllByStudent(String name);
}
