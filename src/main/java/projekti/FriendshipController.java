 
package projekti;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class FriendshipController {
    
    @Autowired
    private AccountRepository accountRepository; 
    @Autowired 
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;
    
    
    
    @PostMapping("/makingFriendsWith/{profile}")
    public String makingFriends(@PathVariable String profile){
        
         Account friend2 = accountRepository.findByProfile(profile);
         Friendship friendship = new Friendship();
         friendship.setFriend1(getloggedUser());
         friendship.setFriend2(friend2);
         friendshipRepository.save(friendship);
          
         Account loggedUser = getloggedUser();
 
            FriendRequest request = friendRequestRepository
                    .findByRequesterAndRequested(friend2, getloggedUser());
            friendRequestRepository.delete(request);
             
        return "redirect:/users/" + getloggedUserProfile();
    }
    
    @PostMapping("/sendFriendRequestTo/{profile}")
    public String sendFriendRequest(@PathVariable String profile){
        
        if(friendRequestRepository.findByRequesterAndRequested(accountRepository
                .findByProfile(profile), getloggedUser()) == null){
        
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setRequester(getloggedUser());
        friendRequest.setRequested(accountRepository.findByProfile(profile));
        friendRequest.setDate(LocalDate.now());
        friendRequestRepository.save(friendRequest);
        } 
        return "redirect:/users/" + getloggedUserProfile();
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
     
}
