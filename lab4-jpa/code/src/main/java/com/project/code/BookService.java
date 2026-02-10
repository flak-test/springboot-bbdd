package com.project.code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    /**
     * Guarda el libro en la BD
     * @param book
     * @return Book
     */
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    /**
     * Metodo para conseguir una List con todos los libros de la BD
     * @return
     */
    public List<Book>  getAllBooks(){

        return bookRepository.findAll();

    }

    /**
     * Metodo que devuelve un solo objeto de tipo libro al especificar su ID
     * @param id
     * @return
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    /**
     * Metodo para actualizar los parametros de un libro especificando su ID
     * @param id
     * @return
     */
    public Book updateBook(Long id, Book newBookDetails){
        Book book=this.bookRepository.findById(id).orElseThrow(); //orElseThrow es otra forma de usar un Optional porque findById devuelve un optional. Con este ultimo metodo  se asegura de que se lance una excepcion si nos da un null
        book.setAuthor(newBookDetails.getAuthor());
        book.setPrice(newBookDetails.getPrice());
        book.setTitle(newBookDetails.getTitle());

        return this.saveBook(book);
    }

    /**
     * Método para borrar un libro de la BD especificando un ID de tipo Long
     * @param id id de un libro con formato Long
     */
    public void deleteBook(Long id){
        this.bookRepository.deleteById(id); // metodo de la interfaz CrudRepository
    }

}


//
//@Service
//public class BookService {
//    @Autowired
//    private BookRepository bookRepository;
//    public Book saveBook(Book book) {
//        return bookRepository.save(book);
//    }
//}