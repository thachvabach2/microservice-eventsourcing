package vn.bachdao.employeeservice.command.event;

import jakarta.ws.rs.NotFoundException;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import vn.bachdao.employeeservice.command.data.Employee;
import vn.bachdao.employeeservice.command.data.EmployeeRepository;

@Component
public class EmployeeEventHandler {

    private final EmployeeRepository employeeRepository;

    public EmployeeEventHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @EventHandler
    public void on(EmployeeCreatedEvent event) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(event, employee);
        employeeRepository.save(employee);
    }

    @EventHandler
    public void on(EmployeeUpdatedEvent event) {
        Employee employee = this.employeeRepository.findById(event.getId()).orElseThrow((() -> new NotFoundException("Employee not found")));

        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDiscipline(event.getIsDiscipline());

        this.employeeRepository.save(employee);
    }
}
