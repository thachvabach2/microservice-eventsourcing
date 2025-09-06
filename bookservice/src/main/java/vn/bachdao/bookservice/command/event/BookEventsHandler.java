package vn.bachdao.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.bachdao.bookservice.command.data.Book;
import vn.bachdao.bookservice.command.data.BookRepository;

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
}

