package vn.bachdao.employeeservice.command.aggregate;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;
import vn.bachdao.employeeservice.command.command.CreateEmployeeCommand;
import vn.bachdao.employeeservice.command.command.UpdateEmployeeCommand;
import vn.bachdao.employeeservice.command.event.EmployeeCreatedEvent;
import vn.bachdao.employeeservice.command.event.EmployeeUpdatedEvent;

@Aggregate
@Data
@NoArgsConstructor
public class EmployeeAggregate {

    @AggregateIdentifier
    private String id;

    private String firstName;
    private String lastName;
    private String kin;
    private Boolean isDiscipline;

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand command) {
        EmployeeCreatedEvent event = new EmployeeCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateEmployeeCommand command) {
        EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(EmployeeCreatedEvent event) {
        this.id = event.getId();
        this.firstName = event.getFirstName();;
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDiscipline = event.getIsDiscipline();
    }

    @EventSourcingHandler
    public void on(EmployeeUpdatedEvent event) {
        this.id = event.getId();
        this.firstName = event.getFirstName();;
        this.lastName = event.getLastName();
        this.kin = event.getKin();
        this.isDiscipline = event.getIsDiscipline();
    }
}
