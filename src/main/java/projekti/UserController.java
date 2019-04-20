 
package projekti;

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
            return "user";
        }
        return  "redirect:/index"; 
    }
    
     
    @GetMapping("/find")
    public String findUserPage(Model model) { 
         
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
