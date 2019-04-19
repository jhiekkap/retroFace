 
package projekti;

import org.springframework.data.jpa.repository.JpaRepository;

 
public interface PhotoRepository extends JpaRepository<PhotoObject, Long> {
    
}
