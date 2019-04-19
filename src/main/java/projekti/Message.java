 
package projekti;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

 

 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message extends AbstractPersistable<Long>{
    
    private String content;
    private LocalDate date;
    private Account sender;
    private Account receiver;
    private List<Account> likes = new ArrayList<>();
    
    private List<Comment> comments = new ArrayList<>();
    
}
