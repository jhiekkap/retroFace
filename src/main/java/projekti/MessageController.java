 
package projekti;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class MessageController {
    
    @Autowired
    private AccountRepository accountRepository; 
    @Autowired
    private FriendshipRepository friendshipRepository; 
    @Autowired
    private MessageRepository messageRepository; 
    @Autowired
    private CommentRepository commentRepository; 
    @Autowired
    private PhotoRepository photoRepository; 
    
    
    @PostMapping("/writeMessageTo/{profile}")
    private String writeMessage(@PathVariable String profile, @RequestParam String content){
        
        Message message = new Message();
        Account account = accountRepository.findByProfile(profile); 
        
        message.setSender(getloggedUser());
        message.setReceiver(account);
        message.setContent(content);
        message.setDate(LocalDate.now());
        
//        account.getMessages().add(message);
        messageRepository.save(message);
        messageRepository.findById(Long.MIN_VALUE);
         
        return "redirect:/users/" + profile;
    }
    
     
    
    
    
    @PostMapping("/commentsMessage/{id}/by/{profile}")
    private String commentOn(@PathVariable Long id, @PathVariable String profile, @RequestParam String content){
        
        Comment comment = new Comment();
        comment.setSender(getloggedUser());
        comment.setReceiver(accountRepository.findByProfile(profile)); 
        comment.setDate(LocalDate.now());
        comment.setContent(content);
        comment.setMessage(messageRepository.getOne(id));
           
        Message message = messageRepository.getOne(id); 
        message.getComments().add(comment);
        messageRepository.save(message);
        commentRepository.save(comment);//???????????
        
         return "redirect:/users/" + profile; 
    }
    
    
    @PostMapping("/commentsPhoto/{id}/by/{profile}")
    private String commentPhoto(@PathVariable Long id, @PathVariable String profile, @RequestParam String content){
        
        Comment comment = new Comment();
        comment.setSender(getloggedUser());
        comment.setReceiver(accountRepository.findByProfile(profile)); 
        comment.setDate(LocalDate.now());
        comment.setContent(content); 
        comment.setPhotoObject(photoRepository.getOne(id));
          
        PhotoObject photo = photoRepository.getOne(id); 
        photo.getComments().add(comment);
        photoRepository.save(photo);
        commentRepository.save(comment);//???????????
        
         return "redirect:/users/" + profile + "/photos";   
    }
 
    
    
    public String getloggedUserProfile(){
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account  loggedUser = accountRepository.findByUsername(username); 
        String profile = loggedUser.getProfile();
        
        return profile; 
    }
    
    public Account getloggedUser(){
        
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
        
        return getMyFriends(getloggedUser()).contains(account);
    }
     
}
