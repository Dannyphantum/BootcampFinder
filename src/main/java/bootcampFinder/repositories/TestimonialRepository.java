package bootcampFinder.repositories;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.Testimonial;
import org.springframework.data.repository.CrudRepository;

public interface TestimonialRepository extends CrudRepository<Testimonial, Long> {
}
