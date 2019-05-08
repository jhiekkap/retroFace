  
package projekti;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity; 
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
    
    private LocalDateTime date;
    private String content;
      
     
    @OneToMany(mappedBy = "message")
    private List<Likes> likes = new ArrayList<>();
    
    @OneToMany(mappedBy = "message")
    private List<Comment> comments = new ArrayList<>(); 
}
