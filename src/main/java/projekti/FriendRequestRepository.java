package projekti;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
//     FriendRequest FindByRequester(Account requester);
    FriendRequest findByRequesterAndTo (Account requester, Account to);
    List<FriendRequest> findByRequester(Account requester);
}
