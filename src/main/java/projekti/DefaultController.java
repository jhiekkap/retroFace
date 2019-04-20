package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class DefaultController {
    
    @Autowired AccountRepository accountRepository;
    
    @GetMapping("/")
    public String root() { 
        return "redirect:/index"; 
    }
    
    @GetMapping("/index")
    public String index() { 
        return "index"; 
    }
    
    @GetMapping("/logout")
    public String logout() { 
        return "index"; 
    }
    
    @GetMapping("/asd")
    public String asd(Model model) { 
        
        Account janina = accountRepository.findByProfile("BabyJane");
        model.addAttribute("user", janina);
        model.addAttribute("requests", janina.getFriendRequests());
        
        
        return "asd"; 
    }
} 
     
     