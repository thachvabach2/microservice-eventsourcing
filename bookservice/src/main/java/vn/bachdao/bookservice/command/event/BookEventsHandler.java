package vn.bachdao.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.bachdao.bookservice.command.data.Book;
import vn.bachdao.bookservice.command.data.BookRepository;
import vn.bachdao.commonservice.event.BookRollBackStatusEvent;
import vn.bachdao.commonservice.event.BookUpdateStatusEvent;

import java.util.Optional;

@Component
public class BookEventsHandler {

    private final BookRepository bookRepository;

    public BookEventsHandler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventHandler
    public void on(BookCreatedEvent event){
        Book book = new Book();
        BeanUtils.copyProperties(event,book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent event) {
        Optional<Book> oldBook = bookRepository.findById(event.getId());

        if(oldBook.isPresent()) {
            Book book = oldBook.get();
            book.setAuthor(event.getAuthor());
            book.setName(event.getName());
            book.setIsReady(event.getIsReady());

            bookRepository.save(book);
        }
    }

    @EventHandler
    public void on(BookDeletedEvent event) {
        Optional<Book> oldBook = bookRepository.findById(event.getId());
        oldBook.ifPresent(bookRepository::delete);
    }

    @EventHandler
    public void on(BookUpdateStatusEvent event) {
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());

        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            this.bookRepository.save(book);
        });
    }

    @EventHandler
    public void on(BookRollBackStatusEvent event) {
        Optional<Book> oldBook = bookRepository.findById(event.getBookId());
        oldBook.ifPresent(book -> {
            book.setIsReady(event.getIsReady());
            this.bookRepository.save(book);
        });
    }
}
