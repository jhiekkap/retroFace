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
public class PhotoController {
    
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private AccountRepository accountRepository;
     
    
     
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
        if(loggedUser.equals(account)){
                model.addAttribute("isLogged", true);
            }
        
        return "photos";
    }
     
    @GetMapping(path = "/photos/{id}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] getFromAllPhotos(@PathVariable long id) {
         
        return photoRepository.getOne(id).getContent();
    }
    
    
    
//    ----------------- EI EHKÄ EDES TARVITA ---------------------------------------
//    @GetMapping(path = "users/{profile}/photos/{id}/content", produces = "image/jpg")
//    @ResponseBody
//    public byte[] getFromUserPhotos(@PathVariable String profile, @PathVariable long id) {
//        
//        Account account = accountRepository.findByProfile(profile);
//        List<PhotoObject> userPhotos = account.getPhotos();
//        
//        return photoRepository.getOne(id).getContent();
//    }
//----------------------------------    EI TOIMI VIELÄ   -------------------
//    @PostMapping("users/{profile}/photos")
//     public String saveUserPhoto(@RequestParam("file") MultipartFile file,
//            @RequestParam("title") String title,
//            @PathVariable String profile) throws IOException {
//        
//         
//         System.out.println("PÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖÖ");
//        PhotoObject photo = new PhotoObject();
//
//        photo.setName(file.getOriginalFilename());
//        photo.setContentType(file.getContentType());
//        photo.setContentLength(file.getSize());
//        photo.setContent(file.getBytes());
//        photo.setTitle(title);
//        
//        photoRepository.save(photo);
//        Account account = accountRepository.findByProfile(profile);
//        account.getPhotos().add(photo);
//        accountRepository.save(account);
//        
//        return "redirect:/users/{" + profile + "}/photos";
//     }
    
    @PostMapping("/users/{profile}/photos/{id}")
    public String setProfilePhoto(@PathVariable String profile, @PathVariable Long id){
        
        Account account = accountRepository.findByProfile(profile);
        PhotoObject photo = photoRepository.getOne(id);
        account.setProfilePhoto(photo);
        accountRepository.save(account);
        
        return "redirect:/users/" + getloggedUserProfile();   
    }
    
    @PostMapping("/photos") // PARANNETU VANHA.........
    public String savePhoto(@RequestParam("file") MultipartFile file,
            @RequestParam("title") String title) throws IOException {
 
        PhotoObject fo = new PhotoObject();

        fo.setName(file.getOriginalFilename());
        fo.setContentType(file.getContentType());
        fo.setContentLength(file.getSize());
        fo.setContent(file.getBytes());
        fo.setTitle(title);
     
        photoRepository.save(fo);
        getloggedUser().getPhotos().add(fo);
        accountRepository.save(getloggedUser()); 
        
        return "redirect:/users/" + getloggedUserProfile() + "/photos";    
//    return "redirect:/photos";
}
    
    @DeleteMapping("/photos/{id}")
    public String delete(@PathVariable Long id) {
        
        PhotoObject photo = photoRepository.getOne(id);
        Account account = getloggedUser();
        account.getPhotos().remove(photo);
        accountRepository.save(account);
        
        return "redirect:/users/" + getloggedUserProfile() + "/photos";    
//        return "redirect:/photos";
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