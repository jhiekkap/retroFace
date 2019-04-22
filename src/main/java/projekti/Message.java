  
package projekti;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

 

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractPersistable<Long>{
    
    
    @ManyToOne
    private Account sender;
    @ManyToOne
    private Account receiver;
    
    private LocalDate date;
    private String content;
      
    @OneToMany
    private List<Like> likes = new ArrayList<>();
    
    @OneToMany(mappedBy = "message")
    private List<Comment> comments = new ArrayList<>();
    
}
