package vn.bachdao.borrowingservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.bachdao.borrowingservice.command.data.Borrowing;
import vn.bachdao.borrowingservice.command.data.BorrowingRepository;

import java.util.Optional;

@Component
public class BorrowingEventsHandler {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @EventHandler
    public void on(BorrowingCreatedEvent event) {
        Borrowing model = new Borrowing();
        model.setId(event.getId());
        model.setBorrowingDate(event.getBorrowingDate());
        model.setBookId(event.getBookId());
        model.setEmployeeId(event.getEmployeeId());
        this.borrowingRepository.save(model);
    }

    @EventHandler
    public void on(BorrowingDeletedEvent event) {
        Optional<Borrowing> oldEntity = this.borrowingRepository.findById(event.getId());
        oldEntity.ifPresent(this.borrowingRepository::delete);
    }
}
