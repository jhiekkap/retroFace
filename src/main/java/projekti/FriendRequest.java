 
package projekti;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;



@Entity 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest extends AbstractPersistable<Long>{

//    @ManyToMany(mappedBy = "friendRequests")
//    private List<Account> accounts;
    
//    @ManyToOne 
    @OneToOne
    private Account requester; 
//    @ManyToOne
    @OneToOne
    private Account to;
    private LocalDate date; 
     
}
