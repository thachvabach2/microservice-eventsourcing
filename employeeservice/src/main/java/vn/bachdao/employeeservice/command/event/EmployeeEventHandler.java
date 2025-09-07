package vn.bachdao.employeeservice.command.event;

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
}
