 
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long>{

    @ManyToOne
    private Account account;

    @OneToOne(mappedBy = "requester")
    private FriendRequest friendRequest;
    
    private String name;  
    private String username;
    private String password; 
    private String profile;
//    private String authority = "USER";
//    
//    private boolean user = false;
    @OneToOne
    private PhotoObject  profilePhoto = null;
  
    //____________----------------????????????????????????-----------------------------
//    @ManyToMany(mappedBy = "account")
//    private List<Account> friends = new ArrayList<>();
//    private List<Message> receivedMessages = new ArrayList<>();
//    private List<Message> sentMessages = new ArrayList<>();
//    @OneToMany(mappedBy="requester")
    
   
    @OneToMany(mappedBy = "to")
    private List<FriendRequest> friendRequests = new ArrayList<>();
    @OneToMany
    private List<PhotoObject> photos = new ArrayList<>();
    
    @OneToMany(mappedBy = "friend1")
    private List<Friendship> friendships = new ArrayList<>();
//    
    
}
