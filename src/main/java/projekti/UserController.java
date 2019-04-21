 
package projekti;

import java.util.ArrayList;
import java.util.List;
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
public class UserController {
    
    @Autowired
    private AccountRepository accountRepository; 
    
    
    
    @GetMapping("/user")
    public String user(Model model) {
         
        return "redirect:/users/"+ getloggedUserProfile();
    }
    
    @GetMapping("/users/{profile}")
    public String users(Model model, @PathVariable String profile) {
        
        Account account = accountRepository.findByProfile(profile);
        if(account != null){ 
            
            Account  loggedUser = getloggedUser();
            model.addAttribute("loggedUser", loggedUser); 
            model.addAttribute("user", account); 
            if(loggedUser.equals(account)){
                model.addAttribute("isLogged", true);
            }  
            List<Friendship> friendships = account.getFriendships();
            List<Account> friends = new ArrayList<>();
            friendships.forEach((friendship) -> {
                if(friendship.getFriend1().equals(getloggedUser())){
                    friends.add(friendship.getFriend2());
                } else {
                    friends.add(friendship.getFriend1());
                }
            });
            model.addAttribute("friends", friends);
            
            List<FriendRequest> requests = account.getFriendRequests();
            model.addAttribute("friendRequests", requests);
            
             
            return "user";
        }
        return  "redirect:/index"; 
    }
    
     
    @GetMapping("/users/{profile}/find")
    public String findUserPage(Model model, @PathVariable String profile) { 
         
        Account userAccount = accountRepository.findByProfile(profile);
        model.addAttribute("loggedUser", getloggedUser()); 
        model.addAttribute("user", userAccount);
        
        return "find";
    }
    
    
    @PostMapping("/findUser")
    public String findUser (@RequestParam String name){
        if(accountRepository.findByName(name) != null){
            
            Account user = accountRepository.findByName(name); 
            String profile = user.getProfile();
            
            return "redirect:/users/"+ profile;
        }
        return "redirect:/find";
    }
    
    @GetMapping("/users/{profile}/allUsers")
    public String allUsers (Model model, @PathVariable String profile){
        
        Account userAccount = accountRepository.findByProfile(profile);
        model.addAttribute("loggedUser", getloggedUser()); 
        model.addAttribute("user", userAccount);
        
        List<Account> users = new ArrayList<>();
        accountRepository.findAll().forEach(user ->{
            users.add(user);
        });
        model.addAttribute("users", users);
        
        return "allUsers";
        
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
