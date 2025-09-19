package vn.bachdao.borrowingservice.command.saga;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import vn.bachdao.borrowingservice.command.command.DeleteBorrowingCommand;
import vn.bachdao.borrowingservice.command.event.BorrowingCreatedEvent;
import vn.bachdao.borrowingservice.command.event.BorrowingDeletedEvent;
import vn.bachdao.commonservice.command.RollBackStatusBookCommand;
import vn.bachdao.commonservice.command.UpdateStatusBookCommand;
import vn.bachdao.commonservice.event.BookRollBackStatusEvent;
import vn.bachdao.commonservice.event.BookUpdateStatusEvent;
import vn.bachdao.commonservice.model.BookResponseCommonModel;
import vn.bachdao.commonservice.model.EmployeeResponseCommonModel;
import vn.bachdao.commonservice.queries.GetBookDetailQuery;
import vn.bachdao.commonservice.queries.GetDetailEmployeeQuery;

@Slf4j
@Saga
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowingCreatedEvent event) {
        log.info("BorrowingCreatedEvent in saga for BookId: {} : EmployeeId: {}", event.getBookId(), event.getEmployeeId());

        try {
            GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(event.getBookId());
            BookResponseCommonModel bookResponseCommonModel = this.queryGateway.query(getBookDetailQuery,
                    ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();

            if (!bookResponseCommonModel.getIsReady()) {
                throw new Exception("Sách đã có người mượn");
            } else {
                SagaLifecycle.associateWith("bookId", event.getBookId());
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false, event.getEmployeeId(), event.getId());
                this.commandGateway.sendAndWait(command);
            }
        } catch (Exception ex) {
            rollbackBorrowingRecord(event.getId());
            log.error(ex.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handler(BookUpdateStatusEvent event) {
        log.info("BookUpdateStatusEvent in Sage for bookId: {}", event.getBookId());

        try {
            GetDetailEmployeeQuery query = new GetDetailEmployeeQuery(event.getEmployeeId());
            EmployeeResponseCommonModel employeeModel = this.queryGateway.query(query, ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();

            if(employeeModel.getIsDiscipline()) {
                throw new Exception("Nhân viên đã bị kĩ luật");
            } else {
                log.info("Đã mượn sách thành công");
                SagaLifecycle.end();
            }

            SagaLifecycle.end();
        } catch (Exception ex) {
            rollBackBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowingId());
            log.error(ex.getMessage());
        }
    }

    private void rollbackBorrowingRecord(String id) {
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        this.commandGateway.sendAndWait(command);
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowingId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollBackStatusBookCommand command = new RollBackStatusBookCommand(bookId,true,employeeId,borrowingId);
        this.commandGateway.sendAndWait(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookRollBackStatusEvent event) {
        log.info("BookRollBackStatusEvent in saga for bookId: {}", event.getBookId());
        rollbackBorrowingRecord(event.getBorrowingId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    private void handle(BorrowingDeletedEvent event) {
        log.info("BorrowDeletedEvent in Saga for Borrowing Id: {} ", event.getId());
    }
}
