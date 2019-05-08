 
package projekti;

 
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
 
 
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
 
 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
 
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Services {
    
    @Autowired
    private AccountRepository accountRepository; 
    @Autowired 
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private MessageRepository messageRepository; 
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private CommentRepository commentRepository; 
    @Autowired
    private LikeRepository likeRepository;
 
    
    
    public  List<FriendRequest> getFriendRequests(){
        
        List<FriendRequest> requests = friendRequestRepository.findByRequested(getLoggedUser());
        return requests;
    }
    
    public List<Message> getMessages(Account account){
        
        Pageable pageable = PageRequest.of(0, 25, Sort.by("date").descending());
        List<Message> messages = messageRepository.findByReceiver(account, pageable);
        for(Message message:messages){
            message.getComments().sort(Comparator.comparing(o -> o.getDate())); 
            Collections.reverse(message.getComments());
        }
        return messages;
    }
    
    public List<PhotoObject> getPhotos(String profile){
        
        List<PhotoObject> photos =  accountRepository.findByProfile(profile).getPhotos();
        for(PhotoObject photo:photos){
            photo.getComments().sort(Comparator.comparing(o -> o.getDate())); 
            Collections.reverse(photo.getComments());
        }
        return photos;
        
    }
     
    public List<Account> getOtherUsers(Account account){
        
        List<Account> otherUsers = new ArrayList<>();
        accountRepository.findAll().forEach((user) ->{ 
            if(!isMyFriend(user) && !isMyPossibleFriend(user) && !user.equals(getLoggedUser())){
            otherUsers.add(user);
            } 
        });
        return otherUsers;
    }
    
     public List<Account> getPossibleFriends(Account account){
         
         List<Account> possibleFriends = new ArrayList<>();
         accountRepository.findAll().forEach((user) ->{ 
            if (!isMyFriend(user)  
                    && isMyPossibleFriend(user) && !user.equals(getLoggedUser())){
                possibleFriends.add(user);
            }
        });
        return possibleFriends; 
     }
     
     public void makeFriendsWith(String profile){
         
        Account friend1 = getLoggedUser();
        Account friend2 = accountRepository.findByProfile(profile);
        if(!friend1.equals(friend2) && friendshipRepository.findByFriend1AndFriend2(friend1, friend2) == null
                && friendshipRepository.findByFriend1AndFriend2(friend2, friend1) == null){ 
            
            Friendship friendship = new Friendship();
            friendship.setFriend1(friend1);
            friendship.setFriend2(friend2);
            friendshipRepository.save(friendship);
            
            FriendRequest request = friendRequestRepository
                    .findByRequesterAndRequested(friend2, friend1);
            friendRequestRepository.delete(request);
        }   
     }
     
    public void removeFriend(String profile){
        
        Account friend1 = getLoggedUser();
        Account friend2 = accountRepository.findByProfile(profile);
        
        if(friendshipRepository.findByFriend1AndFriend2(friend1, friend2) != null){
            friendshipRepository.delete(friendshipRepository.findByFriend1AndFriend2(friend1, friend2));
        } else {
            friendshipRepository.delete(friendshipRepository.findByFriend1AndFriend2(friend2, friend1));
        } 
    }
    
    public void sendFriendRequestTo(String profile){
        
        if(friendRequestRepository.findByRequesterAndRequested(accountRepository
                .findByProfile(profile), getLoggedUser()) == null &&
               friendRequestRepository.findByRequesterAndRequested(getLoggedUser(),
                       accountRepository.findByProfile(profile) ) == null ){
        
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setRequester(getLoggedUser());
        friendRequest.setRequested(accountRepository.findByProfile(profile));
        friendRequest.setDate(LocalDateTime.now());
        friendRequestRepository.save(friendRequest);
        } 
    }
    
    public void declineFriendRequestOf(String profile){
        
        Account friend1 = getLoggedUser();
        Account friend2 = accountRepository.findByProfile(profile);
        FriendRequest friendRequest= friendRequestRepository.findByRequesterAndRequested(friend2, friend1);
        friendRequestRepository.delete(friendRequest);
    }
  
    public void setProfilePhoto(String profile, Long id){
        
        Account account = accountRepository.findByProfile(profile);
        PhotoObject photo = photoRepository.getOne(id);
        account.setProfilePhoto(photo);
        accountRepository.save(account); 
    }
    
    public void savePhoto(MultipartFile file, String title)throws IOException{
        
        PhotoObject fo = new PhotoObject();

        fo.setName(file.getOriginalFilename());
        fo.setContentType(file.getContentType());
        fo.setContentLength(file.getSize());
        fo.setContent(file.getBytes());
        fo.setTitle(title);
     
        photoRepository.save(fo);
        getLoggedUser().getPhotos().add(fo);
        accountRepository.save(getLoggedUser());  
    }
    
    public void deletePhoto(Long id){
        
        PhotoObject photo = photoRepository.getOne(id);
        Account account = getLoggedUser();
        account.getPhotos().remove(photo);
        accountRepository.save(account);
    }
    
  
    public void writeMessageTo(String profile, String content){
        
        Message message = new Message();
        Account account = accountRepository.findByProfile(profile); 
        
        message.setSender(getLoggedUser());
        message.setReceiver(account);
        message.setContent(content);
        message.setDate(LocalDateTime.now()); 
//        account.getMessages().add(message);
        messageRepository.save(message);
        messageRepository.findById(Long.MIN_VALUE); 
    }
    
    public void commentMessage(Long id, String profile, String content){
        
        Comment comment = new Comment();
        comment.setSender(getLoggedUser());
        comment.setReceiver(accountRepository.findByProfile(profile)); 
        comment.setDate(LocalDateTime.now());
        comment.setContent(content);
        comment.setMessage(messageRepository.getOne(id));
           
        Message message = messageRepository.getOne(id); 
        if(message.getComments().size() < 10){
            message.getComments().add(comment);
            messageRepository.save(message);
            commentRepository.save(comment); 
        }
    }
    
    public void  commentPhoto(Long id, String profile, String content){
        
        Comment comment = new Comment();
        comment.setSender(getLoggedUser());
        comment.setReceiver(accountRepository.findByProfile(profile)); 
        comment.setDate(LocalDateTime.now());
        comment.setContent(content); 
        comment.setPhotoObject(photoRepository.getOne(id));
          
        PhotoObject photo = photoRepository.getOne(id); 
        photo.getComments().add(comment);
        photoRepository.save(photo);
        commentRepository.save(comment);//???????????
    }
    
    public void likeMessage(Long id, String profile){
        
        Message message = messageRepository.getOne(id);
        Account sender = getLoggedUser();
        Account receiver = accountRepository.findByProfile(profile);
        Likes like = new Likes();
        like.setSender(sender); 
        like.setReceiver(receiver);
        like.setMessage(messageRepository.getOne(id)); 
        message.getLikes().add(like);
        likeRepository.save(like);
        messageRepository.save(message); 
    }
    
    public boolean hasAlreadyLikedThisMessage(Long id){
        
        Message message = messageRepository.getOne(id);
        Account sender = getLoggedUser();
        
        return likeRepository.findByMessageAndSender(message, sender) == null; 
    }
    
    public void likePhoto(Long id, String profile){
        
        PhotoObject photo = photoRepository.getOne(id);
        Account sender = getLoggedUser();
        Account receiver = accountRepository.findByProfile(profile);
        Likes like = new Likes();
        like.setSender(sender); 
        like.setReceiver(receiver);
        like.setPhotoObject(photoRepository.getOne(id)); 
        photo.getLikes().add(like);
        likeRepository.save(like);
        photoRepository.save(photo); 
    }
    
     public boolean hasAlreadyLikedThisPhoto(Long id){
        
        PhotoObject photo = photoRepository.getOne(id);
        Account sender = getLoggedUser();
        
        return likeRepository.findByPhotoObjectAndSender(photo, sender) == null; 
    }
    
    public String getLoggedUserProfile(){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account  loggedUser = accountRepository.findByUsername(username); 
        String profile = loggedUser.getProfile();
        
        return profile; 
    }
    
    public Account getLoggedUser(){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account  loggedUser = accountRepository.findByUsername(username);
        
        return loggedUser;
    }
    
    public List<Account> getMyFriends(Account account){
        
        List<Friendship> friendships = friendshipRepository.findAll();
            List<Account> friends = new ArrayList<>();
            friendships.forEach((friendship) -> {
                if(friendship.getFriend1().equals(account)){
                    friends.add(friendship.getFriend2());
                } else if(friendship.getFriend2().equals(account)){
                    friends.add(friendship.getFriend1());
                }
            });  
            return friends; 
    }
    
    public boolean isMyFriend(Account account){
        
        return getMyFriends(getLoggedUser()).contains(account);
    }
    
    public boolean isMyPossibleFriend(Account account){
      
        List<FriendRequest> friendRequests = friendRequestRepository.findAll();
        
        for(FriendRequest request:friendRequests){
            if(request.getRequester().equals(account) && request.getRequested().equals(getLoggedUser())){
                 return true;
            } else if (request.getRequested().equals(account) && request.getRequester().equals(getLoggedUser())){
                return true;
            }   
        }   
        return false;
    }    
    
    public boolean isLoggedUser(Account account){
        
        return getLoggedUser().equals(account);
    } 
}
