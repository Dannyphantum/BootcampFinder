package bootcampFinder.repositories;

/**
 * Created by student on 7/17/17.
 */

import bootcampFinder.models.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MessageRepository extends CrudRepository<Message, Long> {
	
    ArrayList<Message> findAllByRecieverId(long userId);
    
    Message findOneByRecieverId(long reciverId);
}
