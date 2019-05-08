 
package projekti;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String add(@Valid @ModelAttribute Account account, BindingResult bindingResult){
        
        if (bindingResult.hasErrors()) { 
             return "redirect:/signUp";
        } 
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
            
        return "redirect:/user";
    }
}
