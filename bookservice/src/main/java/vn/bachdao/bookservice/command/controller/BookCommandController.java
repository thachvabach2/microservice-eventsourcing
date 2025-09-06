package vn.bachdao.bookservice.command.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bachdao.bookservice.command.command.CreateBookCommand;
import vn.bachdao.bookservice.command.model.BookRequestModel;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {

    private final CommandGateway commandGateway;

    public BookCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addBook(@RequestBody BookRequestModel model) {
        CreateBookCommand command = new CreateBookCommand(UUID.randomUUID().toString(), model.getName(), model.getAuthor(), true);
        return commandGateway.sendAndWait(command); // return aggregate identifier
    }
}
