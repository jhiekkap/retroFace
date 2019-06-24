 
package projekti;

 
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestControlleri {
     
  
    @Autowired
    private Services services; 
    
    
  @PostMapping("/books")
  public void sendMessage(@RequestParam String profile, @RequestParam String content) {
      services.writeMessageTo(profile, content);  
  }  

//  @GetMapping("/books")
//  public List<Book> getBooks() {
//      return bookRepository.findAll();
//  }
//
//  @GetMapping("/books/{id}")
//  public Book getBook(@PathVariable Long id) {
//      return bookRepository.getOne(id);
//  }
//
//  @DeleteMapping("/books/{id}")
//  public Book deleteBook(@PathVariable Long id) {
//      return bookRepository.deleteById(id);
//  }
//
//  @PostMapping("/books")
//  public Book postBook(@RequestBody Book book) {
//      return bookRepository.save(book);
//  }
 
    
    
} 
