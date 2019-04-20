package projekti;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class DefaultController {
    
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AccountRepository accountRepository;
     
    
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
     
    @GetMapping("/photos")
    public String getAllPhotos (Model model){
        
        model.addAttribute("photos", photoRepository.findAll());  
        return "photos";
    }
    
    @GetMapping("/users/{profile}/photos")
    public String getAllUserPhotos (Model model,
            @PathVariable String profile){
        
        Account account = accountRepository.findByProfile(profile);
        List<PhotoObject> userPhotos = account.getPhotos();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account  loggedUser = accountRepository.findByUsername(username);
        model.addAttribute("loggedUser", loggedUser); 
        model.addAttribute("user", account);
        model.addAttribute("photos", userPhotos); 
        model.addAttribute("count", userPhotos.size());
        return "photos";
    }
     
    @GetMapping(path = "/photos/{id}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] getFromAllPhotos(@PathVariable long id) {
         
        return photoRepository.getOne(id).getContent();
    }
    
//    @GetMapping(path = "users/{profile}/photos/{id}/content", produces = "image/jpg")
//    @ResponseBody
//    public byte[] getFromUserPhotos(@PathVariable String profile, @PathVariable long id) {
//        
//        Account account = accountRepository.findByProfile(profile);
//        List<PhotoObject> userPhotos = account.getPhotos();
//        
//        return photoRepository.getOne(id).getContent();
//    }
    
    @PostMapping("users/{profile}/photos")
     public String saveUserPhoto(@RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @PathVariable String profile) throws IOException {
           
        PhotoObject photo = new PhotoObject();

        photo.setName(file.getOriginalFilename());
        photo.setContentType(file.getContentType());
        photo.setContentLength(file.getSize());
        photo.setContent(file.getBytes());
        photo.setTitle(title);
        
        photoRepository.save(photo);
        Account account = accountRepository.findByProfile(profile);
        account.getPhotos().add(photo);
        accountRepository.save(account);
        System.out.println(profile); 
        return "redirect:/users/{" + profile + "}/photos";
     }
    
    @PostMapping("/photos")
    public String savePhoto(@RequestParam("file") MultipartFile file,
            @RequestParam("title") String title) throws IOException {
 
        PhotoObject fo = new PhotoObject();

        fo.setName(file.getOriginalFilename());
        fo.setContentType(file.getContentType());
        fo.setContentLength(file.getSize());
        fo.setContent(file.getBytes());
        fo.setTitle(title);

        photoRepository.save(fo);

    return "redirect:/photos";
}
    
    @DeleteMapping("/photos/{id}")
    public String delete(@PathVariable Long id) {
        
        photoRepository.delete(photoRepository.getOne(id));
        
        return "redirect:/photos";
    }
    
}