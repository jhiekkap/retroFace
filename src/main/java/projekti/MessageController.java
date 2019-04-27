 
package projekti;
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class MessageController {
    
     
    @Autowired
    private Services services;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    
    
    @PostMapping("/writeMessageTo/{profile}")
    private String writeMessage(@PathVariable String profile, @RequestParam String content){
        
        services.writeMessageTo(profile, content); 
        return "redirect:/users/" + profile;
    }
     
    @PostMapping("/commentsMessage/{id}/by/{profile}")
    private String commentMessage(@PathVariable Long id, @PathVariable String profile, @RequestParam String content){
        
        services.commentMessage(id, profile, content); 
        return "redirect:/users/" + profile; 
    }
     
    @PostMapping("/commentsPhoto/{id}/by/{profile}")
    private String commentPhoto(@PathVariable Long id, @PathVariable String profile, @RequestParam String content){
        
        services.commentPhoto(id, profile, content); 
        return "redirect:/users/" + profile + "/photos";   
    } 
    
    @PostMapping("/likeMessage/{id}/{profile}")
    private String likeMessage(@PathVariable Long id, @PathVariable String profile){
        
        if(services.hasAlreadyLikedThisMessage(id)){
            
            services.likeMessage(id, profile); 
        } 
        return "redirect:/users/" + profile; 
    }
    
    @PostMapping("/likePhoto/{id}/{profile}")
    private String likePhoto(@PathVariable Long id, @PathVariable String profile){
        
        if(services.hasAlreadyLikedThisPhoto(id)){
            
            services.likePhoto(id, profile); 
        } 
        return "redirect:/users/" + profile + "/photos";
    }
}
