 
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
    
    @OneToOne
    @ManyToOne
    private PhotoObject  profilePhoto = null;
    
    @OneToMany(mappedBy = "sender")
    private List<Message> messages = new ArrayList<>();
    
    @OneToMany(mappedBy = "sender")
    private List<Comment> comments = new ArrayList<>();
  
    @OneToMany(mappedBy = "to")
    private List<FriendRequest> friendRequests = new ArrayList<>();
    @OneToMany
    private List<PhotoObject> photos = new ArrayList<>();
    
    @OneToMany(mappedBy = "friend1")
    private List<Friendship> friendships = new ArrayList<>();
     
}
