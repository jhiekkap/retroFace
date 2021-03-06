package projekti;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    
    List<Friendship> findByFriend1 (Account friend1);
    List<Friendship> findByFriend2 (Account friend2);
    Friendship findByFriend1AndFriend2 (Account friend1, Account friend2); 
//    Friendship findByFriend2AndFriend1 (Account friend2, Account friend1);

        
}
