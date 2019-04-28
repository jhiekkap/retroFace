package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class DefaultController {
    
    @Autowired AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @GetMapping("/")
    public String root() { 
        
        if(accountRepository.count() == 0){
            Account pirkko = new Account();
            pirkko.setName("Pirkko");
            pirkko.setUsername("pirkko");
            pirkko.setPassword(passwordEncoder.encode("liisa"));
            pirkko.setProfile("theQueen");
            accountRepository.save(pirkko);
        
            Account palle = new Account();
            palle.setName("Björn");
            palle.setUsername("palle");
            palle.setPassword(passwordEncoder.encode("pelle"));
            palle.setProfile("BusinessMan");
            accountRepository.save(palle);
        
            Account eki = new Account();
            eki.setName("Erkki");
            eki.setUsername("eki");
            eki.setPassword(passwordEncoder.encode("liika"));
            eki.setProfile("Liikanen");
            accountRepository.save(eki);
        }
         
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
} 
     
     