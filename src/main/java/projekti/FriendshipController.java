 
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
    
    
    
    @PostMapping("/makingFriendsWith1/{profile}")
    public String makingFriends1(@PathVariable String profile){
         
        Account friend1 = getloggedUser();
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
        return "redirect:/users/" + getloggedUserProfile(); 
    }
    
//    @PostMapping("/makingFriendsWith2/{profile}")
//    public String makingFriends2(@PathVariable String profile){
//        
//         
//        Account friend1 = getloggedUser();
//        Account friend2 = accountRepository.findByProfile(profile);
//        if(!friend1.equals(friend2) && friendshipRepository.findByFriend1AndFriend2(friend1, friend2) == null
//                && friendshipRepository.findByFriend1AndFriend2(friend2, friend1) == null){
//         
//            Friendship friendship = new Friendship();
//            friendship.setFriend1(friend1);
//            friendship.setFriend2(friend2);
//            friendshipRepository.save(friendship);
//            
//            FriendRequest request = friendRequestRepository
//                    .findByRequesterAndRequested(friend2, friend1);
//            friendRequestRepository.delete(request);
//        } 
//         return "redirect:/users/" + getloggedUserProfile(); 
//    }
    
    @PostMapping("/removeFriend1/{profile}")
    public String removeFriend1 (@PathVariable String profile){
        
        Account friend1 = getloggedUser();
        Account friend2 = accountRepository.findByProfile(profile);
        
        if(friendshipRepository.findByFriend1AndFriend2(friend1, friend2) != null){
            friendshipRepository.delete(friendshipRepository.findByFriend1AndFriend2(friend1, friend2));
        } else {
            friendshipRepository.delete(friendshipRepository.findByFriend1AndFriend2(friend2, friend1));
        } 
         return "redirect:/allUsers"; 
    }
    
    @PostMapping("/removeFriend2/{profile}")
    public String removeFriend2 (@PathVariable String profile){
        
        Account friend1 = getloggedUser();
        Account friend2 = accountRepository.findByProfile(profile);
        
        if(friendshipRepository.findByFriend1AndFriend2(friend1, friend2) != null){
            friendshipRepository.delete(friendshipRepository.findByFriend1AndFriend2(friend1, friend2));
        } else {
            friendshipRepository.delete(friendshipRepository.findByFriend1AndFriend2(friend2, friend1));
        } 
        return "redirect:/users/" + getloggedUserProfile();  
    }
    
    
    
    @PostMapping("/sendFriendRequestTo/{profile}")
    public String sendFriendRequest(@PathVariable String profile){
        
        if(friendRequestRepository.findByRequesterAndRequested(accountRepository
                .findByProfile(profile), getloggedUser()) == null &&
               friendRequestRepository.findByRequesterAndRequested(getloggedUser(),
                       accountRepository.findByProfile(profile) ) == null ){
        
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setRequester(getloggedUser());
        friendRequest.setRequested(accountRepository.findByProfile(profile));
        friendRequest.setDate(LocalDate.now());
        friendRequestRepository.save(friendRequest);
        } 
        return "redirect:/allUsers"; 
    }
    
    @PostMapping("/declineFriendRequestOf/{profile}")
    public String decline(@PathVariable String profile){
        
        Account friend1 = getloggedUser();
        Account friend2 = accountRepository.findByProfile(profile);
        FriendRequest friendRequest= friendRequestRepository.findByRequesterAndRequested(friend2, friend1);
        friendRequestRepository.delete(friendRequest);
        
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
