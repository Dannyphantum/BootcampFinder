package bootcampFinder.repositories;

import bootcampFinder.models.Request;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Orion Wolf_Hubbard on 7/18/2017.
 */

public interface RequestRepository extends CrudRepository<Request, Long> {
}
