package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface MessageRepository extends JpaRepository<Message, Long> {
    
     List<Message> findByReceiver(Account receiver, Pageable pageable);
    Message findByContent(String content);  
}
