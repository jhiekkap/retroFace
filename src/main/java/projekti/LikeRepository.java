 
package projekti;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface LikeRepository extends JpaRepository<Likes, Long>{
    
    Likes findByMessageAndSender(Message message, Account sender);
    Likes findByPhotoObjectAndSender(PhotoObject photoObject, Account sender);
}
