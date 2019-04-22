 
package projekti;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class MessageController {
    
    @Autowired
    private AccountRepository accountRepository; 
    @Autowired
    private FriendshipRepository friendshipRepository; 
    @Autowired
    private MessageRepository messageRepository; 
    @Autowired
    private CommentRepository commentRepository; 
    
    
    @PostMapping("/writeMessageTo/{profile}")
    private String writeMessage(@PathVariable String profile, @RequestParam String content){
        
        Message message = new Message();
        Account account = accountRepository.findByProfile(profile); 
        
        message.setSender(getloggedUser());
        message.setReceiver(account);
        message.setContent(content);
        message.setDate(LocalDate.now());
        
//        account.getMessages().add(message);
        messageRepository.save(message);
         
        return "redirect:/users/" + profile;
    }
    
    @PostMapping("/{profile}/commentsOn/")
    private String commentOn(@PathVariable String profile, @RequestParam String content){
        
        Comment comment = new Comment();
        comment.setSender(accountRepository.findByProfile(profile));
        comment.setReceiver(getloggedUser());
        comment.setDate(LocalDate.now());
        
        
        Message message = messageRepository.findByContent(content);
        message.getComments().add(comment);
        messageRepository.save(message);
        commentRepository.save(comment);
        
         return "redirect:/users/" + profile; 
    }
//    
//    
//    @GetMapping("/user")
//    public String user(Model model) {
//         
//        return "redirect:/users/"+ getloggedUserProfile();
//    }
//    
//    @GetMapping("/users/{profile}")
//    public String users(Model model, @PathVariable String profile) {
//        
//        Account account = accountRepository.findByProfile(profile);
//        if(account != null){ 
//            
//            Account  loggedUser = getloggedUser();
//            model.addAttribute("loggedUser", loggedUser); 
//            model.addAttribute("user", account); 
//            
//            if(loggedUser.equals(account)){
//                model.addAttribute("isLogged", true);
//            }    
//            model.addAttribute("friends",getMyFriends(account)); 
//            
//            model.addAttribute("friendRequests", loggedUser.getFriendRequests());
//            
//            model.addAttribute("messages", messageRepository.findByProfile(profile));
//             
//            return "user";
//        }
//        return  "redirect:/index"; 
//    }
    
     
//    @GetMapping("/users/{profile}/find")
//    public String findUserPage(Model model, @PathVariable String profile) { 
//         
//        Account userAccount = accountRepository.findByProfile(profile);
//        model.addAttribute("loggedUser", getloggedUser()); 
//        model.addAttribute("user", userAccount);
//        
//        return "find";
//    }
//    
//    
//    @PostMapping("/findUser")
//    public String findUser (@RequestParam String name){
//        if(accountRepository.findByName(name) != null){
//            
//            Account user = accountRepository.findByName(name); 
//            String profile = user.getProfile();
//            
//            return "redirect:/users/"+ profile;
//        }
//        return "redirect:/find";
//    }
    
//    @GetMapping("/users/{profile}/allUsers")
//    public String allUsers (Model model, @PathVariable String profile){
//        
//        Account account = accountRepository.findByProfile(profile);
//        model.addAttribute("loggedUser", getloggedUser()); 
//        model.addAttribute("user", account);
//        
//        if(getloggedUser().equals(account)){
//                model.addAttribute("isLogged", true);
//            }   
//        List<Account> users = new ArrayList<>();
//        accountRepository.findAll().forEach((user) ->{ 
//            if(!user.equals(account) && !isMyFriend(user)){
//            users.add(user);
//            }
//        });
//        model.addAttribute("users", users);
//        model.addAttribute("friends", getMyFriends(getloggedUser()));
//         
//        return "allUsers"; 
//    }
//    
//    
     
    
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
    
    public List<Account> getMyFriends(Account account){
        
        List<Friendship> friendships = friendshipRepository.findAll();
            List<Account> friends = new ArrayList<>();
            friendships.forEach((friendship) -> {
                if(friendship.getFriend1().equals(account)){
                    friends.add(friendship.getFriend2());
                } else if(friendship.getFriend2().equals(account)){
                    friends.add(friendship.getFriend1());
                }
            });  
            return friends; 
    }
    
    public boolean isMyFriend(Account account){
        
        return getMyFriends(getloggedUser()).contains(account);
    }
     
}
