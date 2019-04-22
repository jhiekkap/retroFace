package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
     List<Message> findByReceiver(Account receiver);
    Message findByContent(String content);  
}
