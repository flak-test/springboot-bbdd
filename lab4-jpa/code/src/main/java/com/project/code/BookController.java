package com.project.code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;
    // Create a new book

    /*Como se trata de una REST API  no sacamos los datos a través de un formulario,
    sino que recibiremos una peticion HTTP con la info que sea y devolveremos precisamente eso e lugar de un String como ocurre si queremos hacer una vista tras un form*/
    @PostMapping("/addBook")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook); //manda una respuesta con código 200
    }
    // Get all books
    @GetMapping("/getBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Get a book by ID
    @GetMapping("/getBook/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    // Update a book
    @PutMapping("/updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        return ResponseEntity.ok(updatedBook);
    }
    // Delete a book
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}