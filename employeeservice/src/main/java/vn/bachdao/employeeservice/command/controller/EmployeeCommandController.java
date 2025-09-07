package vn.bachdao.employeeservice.command.controller;

import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bachdao.employeeservice.command.command.CreateEmployeeCommand;
import vn.bachdao.employeeservice.command.model.CreateEmployeeModel;

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
}
