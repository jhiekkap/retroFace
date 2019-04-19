package projekti;

import java.io.IOException;
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
    public String getAllFiles (Model model){
        
        model.addAttribute("photos", photoRepository.findAll()); 
        model.addAttribute("count", photoRepository.count());
        return "photos";
    }
     
    @GetMapping(path = "/photos/{id}/content", produces = "image/jpg")
    @ResponseBody
    public byte[] get(@PathVariable long id) {
         
        return photoRepository.getOne(id).getContent();
    }
     
    @PostMapping("/photos")
    public String save(@RequestParam("file") MultipartFile file,
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