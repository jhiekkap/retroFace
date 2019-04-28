 
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class UserController {
    
    @Autowired
    private AccountRepository accountRepository; 
    @Autowired
    private Services services;
    
     
    @GetMapping("/user")
    public String user(Model model) {
         
        return "redirect:/users/"+ services.getLoggedUserProfile();
    }
    
    @GetMapping("/users/{profile}")
    public String users(Model model, @PathVariable String profile) {
        
        Account account = accountRepository.findByProfile(profile);
        if(account != null){ 
            
            Account  loggedUser = services.getLoggedUser();
            model.addAttribute("loggedUser", loggedUser); 
            model.addAttribute("user", account);  
            model.addAttribute("isLogged", services.isLoggedUser(account)); 
            model.addAttribute("friends",services.getMyFriends(account));  
            model.addAttribute("friendRequests", services.getFriendRequests()); 
            model.addAttribute("messages", services.getMessages(account));
             
            return "user";
        }
        return  "redirect:/index"; 
    } 
     
    @GetMapping("/find")
    public String findUserPage(Model model) { 
          
        model.addAttribute("loggedUser", services.getLoggedUser());  
        return "find";
    }
     
    @PostMapping("/findUser")
    public String findUser (@RequestParam String name){
       
        if(accountRepository.findByName(name) != null){ 
            return "redirect:/users/" +  accountRepository.findByName(name).getProfile();
        }
        return "redirect:/find";
    }
    
    @GetMapping("/allUsers")
    public String allUsers (Model model){
       
        model.addAttribute("loggedUser", services.getLoggedUser());  
        model.addAttribute("otherUsers", services.getOtherUsers(services.getLoggedUser()));
        model.addAttribute("possibleFriends", services.getPossibleFriends(services.getLoggedUser()));
        model.addAttribute("friends", services.getMyFriends(services.getLoggedUser()));
         
        return "allUsers"; 
    } 
}
