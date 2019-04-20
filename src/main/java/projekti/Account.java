 
package projekti;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
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
    
    private String name;  
    private String username;
    private String password; 
    private String profile;
//    private String authority = "USER";
//    
//    private boolean user = false;
    @OneToOne
    private PhotoObject  profilePhoto = null;
    
//    private List<Account> friends = new ArrayList<>();
//    private List<Message> receivedMessages = new ArrayList<>();
//    private List<Message> sentMessages = new ArrayList<>();
//    private List<FriendRequest> friendRequests = new ArrayList<>();
    @OneToMany
    private List<PhotoObject> photos = new ArrayList<>();
//    
    
}
