 
package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    PasswordEncoder passwordEncoder;

    @GetMapping("/signUp")
    public String list(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "signUp";
    }

    @PostMapping("/signUp")
    public String add(@RequestParam String name, @RequestParam String username, 
            @RequestParam String password, @RequestParam String profile){
        
        if (accountRepository.findByProfile(profile) != null) {
            System.out.println("!!!!!!!!!!!!!!!!!!!profile already in use!!!!!!!!!!!!!!!!!");
            return "redirect:/signUp";
        }
        
        Account a = new Account();
        System.out.println(name + username + password + profile);
         
        a.setName(name); 
        a.setUsername(username);
        a.setPassword(passwordEncoder.encode(password)); 
        a.setProfile(profile);
        accountRepository.save(a);
        
        return "index";
    }
}
