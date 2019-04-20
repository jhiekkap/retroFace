package projekti;

import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
//     FriendRequest FindByRequester(Account requester);
}
