 
package projekti;

 
 
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import static org.aspectj.lang.reflect.DeclareAnnotation.Kind.Type;
import static org.aspectj.weaver.loadtime.definition.Definition.DeclareAnnotationKind.Type;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class PhotoObject extends AbstractPersistable<Long> {
    
    private String name; 
    private String contentType;
    private Long contentLength;
    private String title;
//     
    
    @OneToMany(mappedBy = "photoObject")
    private List<Likes> likes = new ArrayList<>();
    @OneToMany(mappedBy = "photoObject")
    private List<Comment> comments = new ArrayList<>();
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content; 
}
