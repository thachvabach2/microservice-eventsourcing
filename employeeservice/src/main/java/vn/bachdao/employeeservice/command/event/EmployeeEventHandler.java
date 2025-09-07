package vn.bachdao.employeeservice.command.event;

import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import vn.bachdao.employeeservice.command.data.Employee;
import vn.bachdao.employeeservice.command.data.EmployeeRepository;

@Component
@Slf4j
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
    public void on(EmployeeUpdatedEvent event) throws Exception {
        Employee employee = this.employeeRepository.findById(event.getId()).orElseThrow((() -> new Exception("Employee not found")));

        employee.setFirstName(event.getFirstName());
        employee.setLastName(event.getLastName());
        employee.setKin(event.getKin());
        employee.setIsDiscipline(event.getIsDiscipline());

        this.employeeRepository.save(employee);
    }

    @EventHandler
    @DisallowReplay //ko xử lý lại những event lỗi (event làm gián đoạn hệ thống) khi khởi restart project
    public void on(EmployeeDeletedEvent event) throws Exception {
        try {
            Employee employee = this.employeeRepository.findById(event.getId()).orElseThrow((() -> new Exception("Employee not found")));

            this.employeeRepository.delete(employee);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
