package com.project.code;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class BookController {

    private final BookRepository bookRepository;
    //aqui tambien podriamos añadir comoa tributo nuestro Book validator tal que asi para usar su metodo validate y hasErrors de BindingResult: private final BookValidator bookValidator = new BookValidator();
    //tambien podriamos poner un logger para facilitar el debugeo:     private static final Logger logger = LoggerFactory.getLogger(BookController.class); 
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/createBook")//el return es una Book porque la anotacion RestController se encarga automaticamente de serializar el objeto en un JSON
    public Book createBook(@RequestBody Book book) { // segú Baeldung: the @RequestBody annotation maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization of the inbound HttpRequest body onto a Java object.
        return bookRepository.save(book);
    }

//    Aqui tenemos una alternativa con un validador que tendriamos que añadir comoa tributo al controller (ignorar el simbolo    porue son taburadores)
//@Controller
//public class BookController {
//  private final BookValidator bookValidator = new BookValidator(); 
//
//
//              @PostMapping(“/addBook”)
//
//              public String addBook(@ModelAttribute Book book, BindingResult result) {
//
//    bookValidator.validate(book, result); // Validate the book object.
//
//    if (result.hasErrors()) {
//
//      return “errorPage”; // Return an error page if validation fails.
//
//    }
//
//    // Process the book object if validation passes.
//
//    return “successPage”;
//
//  }
//
//}

    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() { //el return es una lista porque la anotacion RestController se encarga automaticamente de serializar el objeto en un JSON
        return bookRepository.findAll();
    }


    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) { //como es un solo
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build()); //Aqui vemos ua especie de arrow function para ahorrar código porque indica que la funcion que usamos de parçametro nodevuelve nada.
        // orElseGet es un metodo de Optional para manejar un null si no tenemos resultado. En este caso, usamos status porque el metodo ok() ya pone el status automaticamente, asi que aqui teneoms que definirlo ayudandonos del public enum HttpStatus aunque creo (confirmar) que no es necesario si usamos otros metodos como of() o ofNullable(@Nullable T body)
    }

    @PutMapping("updateBook/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {//devolvemos el libro actualizado en JSON automaticamente simplemente poniendo el objeto como return
        Book book = bookRepository.findById(id).orElseThrow(); //     // Busca el libro. Si NO existe, LANZA EXCEPCIÓN con orElseThrow. Por eso no es necesario el Optional. Es mas, findById devuelve un Optional pero el orElseThrow es un metodo de Optional que devuelve el objeto de la clase original (Book)
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        book.setGenre(bookDetails.getGenre());
        return bookRepository.save(book);
    }

    @DeleteMapping("deleteBook/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}