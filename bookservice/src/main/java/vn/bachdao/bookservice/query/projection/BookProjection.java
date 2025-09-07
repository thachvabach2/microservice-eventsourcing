package vn.bachdao.bookservice.query.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import vn.bachdao.bookservice.command.data.Book;
import vn.bachdao.bookservice.command.data.BookRepository;
import vn.bachdao.bookservice.query.model.BookResponseModel;
import vn.bachdao.bookservice.query.queries.GetAllBookQuery;
import vn.bachdao.bookservice.query.queries.GetBookDetailQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookProjection {

    private final BookRepository bookRepository;

    public BookProjection(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query) {
        List<Book> list = this.bookRepository.findAll();

        List<BookResponseModel> result = list.stream().map(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book, model);
            return model;
        }).toList();
        return result;
    }

    @QueryHandler
    public BookResponseModel handle(GetBookDetailQuery query) {
        BookResponseModel bookResponseModel = new BookResponseModel();
        this.bookRepository.findById(query.getId()).ifPresent(book -> {
             BeanUtils.copyProperties(book , bookResponseModel);
        });
        return bookResponseModel;
    }
}
