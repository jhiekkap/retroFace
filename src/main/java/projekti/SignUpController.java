 
package projekti;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

 
@Controller
public class SignUpController {
     
    @Autowired
    AccountRepository accountRepository;  
    @Autowired
    private Services services;

    @GetMapping("/signUp")
    public String list(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String add(@RequestParam String name, @RequestParam String username, 
            @RequestParam String password, @RequestParam String profile){
        
        if (accountRepository.findByProfile(profile) != null) { 
            return "redirect:/signUp";
        } 
        services.signUp(name, username, password, profile); 
            
        return "redirect:/user";
    }
}
