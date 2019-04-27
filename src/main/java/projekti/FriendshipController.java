 
package projekti;
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; 
 

@Controller 
public class FriendshipController {
     
    @Autowired
    private Services services;
    
    
    @PostMapping("/makingFriendsWith/{profile}")
    public String makingFriends(@PathVariable String profile){
         
        services.makeFriendsWith(profile);
        
        return "redirect:/users/" + services.getLoggedUserProfile(); 
    }
     
    @PostMapping("/removeFriend1/{profile}")
    public String removeFriend1 (@PathVariable String profile){
        
        services.removeFriend(profile);
       
        return "redirect:/allUsers"; 
    }
    
    @PostMapping("/removeFriend2/{profile}")
    public String removeFriend2 (@PathVariable String profile){
        
        services.removeFriend(profile); 
        
        return "redirect:/users/" + services.getLoggedUserProfile();  
    }
    
     
    @PostMapping("/sendFriendRequestTo/{profile}")
    public String sendFriendRequestTo(@PathVariable String profile){
        
        services.sendFriendRequestTo(profile);
        
        return "redirect:/allUsers"; 
    }
    
    @PostMapping("/declineFriendRequestOf/{profile}")
    public String declineFriendRequestOf(@PathVariable String profile){
        
        services.declineFriendRequestOf(profile);
        
        return "redirect:/users/" + services.getLoggedUserProfile(); 
    } 
}
