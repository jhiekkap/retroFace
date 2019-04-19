 
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class HomeController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    
    @GetMapping("/home")
    public String home(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account  user = accountRepository.findByUsername(username);
          
        model.addAttribute("user", user); 
        
        return "home";
    }
    
      
    @GetMapping("/find")
    public String find() {
        
        return "find";
    }
    
    
    @PostMapping("/find")
    public String findUser (@RequestParam String name, Model model){
        if(accountRepository.findByName(name) != null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            Account  realUser = accountRepository.findByUsername(username);
            model.addAttribute("realUser", realUser); 
            Account user = accountRepository.findByName(name);
            model.addAttribute("user", user);
            return "home";
        }
        return("redirect:/find");
    }
}
