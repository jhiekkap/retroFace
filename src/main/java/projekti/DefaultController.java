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
            Account janina = new Account();
            janina.setName("Janina");
            janina.setUsername("janina");
            janina.setPassword(passwordEncoder.encode("pjotr"));
            janina.setProfile("BabyJane");
            accountRepository.save(janina);
        
            Account jari = new Account();
            jari.setName("Jari");
            jari.setUsername("palle");
            jari.setPassword(passwordEncoder.encode("pelle"));
            jari.setProfile("pallepelle");
            accountRepository.save(jari);
        
            Account pjotr = new Account();
            pjotr.setName("Pjotr");
            pjotr.setUsername("pietari");
            pjotr.setPassword(passwordEncoder.encode("daappa"));
            pjotr.setProfile("pietaridaappa");
            accountRepository.save(pjotr);
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
     
     