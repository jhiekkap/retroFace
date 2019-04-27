package projekti;

import java.io.IOException; 
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class PhotoController {
    
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private Services services;
    
     
    @GetMapping("/photos")
    public String getAllPhotos (Model model){
        
        model.addAttribute("photos", photoRepository.findAll());  
        return "photos";
    }
    
    @GetMapping("/users/{profile}/photos")
    public String getAllUserPhotos (Model model,
            @PathVariable String profile){
          
        model.addAttribute("loggedUser", services.getLoggedUser()); 
        model.addAttribute("user", accountRepository.findByProfile(profile));
        model.addAttribute("photos", accountRepository.findByProfile(profile).getPhotos()); 
        model.addAttribute("count", accountRepository.findByProfile(profile).getPhotos().size());
        model.addAttribute("isLogged", services.isLoggedUser(accountRepository.findByProfile(profile)));
              
        return "photos";
    }
     
    @GetMapping(path = "/photos/{id}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] getFromAllPhotos(@PathVariable long id) {
         
        return photoRepository.getOne(id).getContent();
    }
     
    @PostMapping("/users/{profile}/photos/{id}")
    public String setProfilePhoto(@PathVariable String profile, @PathVariable Long id){
        
        services.setProfilePhoto(profile, id);
        
        return "redirect:/users/" + services.getLoggedUserProfile();   
    }
    
    @PostMapping("/photos") 
    public String savePhoto(@RequestParam("file") MultipartFile file,
            @RequestParam("title") String title) throws IOException {
 
        services.savePhoto(file, title);
         
        return "redirect:/users/" + services.getLoggedUserProfile() + "/photos";  
}
    
    @DeleteMapping("/photos/{id}")
    public String deletePhoto(@PathVariable Long id) {
        
        services.deletePhoto(id);
         
        return "redirect:/users/" + services.getLoggedUserProfile() + "/photos";     
    } 
}