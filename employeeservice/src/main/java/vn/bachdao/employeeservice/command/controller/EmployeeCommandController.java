package vn.bachdao.employeeservice.command.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;
import vn.bachdao.employeeservice.command.command.CreateEmployeeCommand;
import vn.bachdao.employeeservice.command.command.DeleteEmployeeCommand;
import vn.bachdao.employeeservice.command.command.UpdateEmployeeCommand;
import vn.bachdao.employeeservice.command.model.CreateEmployeeModel;
import vn.bachdao.employeeservice.command.model.UpdateEmployeeModel;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {

    private final CommandGateway commandGateway;

    public EmployeeCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addEmployee(@Valid @RequestBody CreateEmployeeModel model) {
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                UUID.randomUUID().toString(),
                model.getFirstName(),
                model.getLastName(),
                model.getKin(),
                false);

        return commandGateway.sendAndWait(command);
    }

    @PutMapping("/{employeeId}")
    public String updateEmployee(@Valid @RequestBody UpdateEmployeeModel model, @PathVariable("employeeId") String employeeId) {
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(employeeId, model.getFirstName(),
                model.getLastName(),
                model.getKin(),
                model.getIsDiscipline());
        return commandGateway.sendAndWait(command);
    }

    @Hidden
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") String employeeId) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
        return commandGateway.sendAndWait(command);
    }
}