package vn.bachdao.employeeservice.query.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;
import vn.bachdao.employeeservice.query.model.EmployeeResponseModel;
import vn.bachdao.employeeservice.query.queries.GetAllEmployeeQuery;
import vn.bachdao.employeeservice.query.queries.GetDetailEmployeeQuery;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Query")
public class EmployeeQueryController {

    private final QueryGateway queryGateway;

    public EmployeeQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @Operation(
            summary = "Get List Employee",
            description = "Get endpoint for employee with filter",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized / Invalid Token"
                    )
            }
    )
    @GetMapping
    public List<EmployeeResponseModel> getAllEmployees(@RequestParam(required = false, defaultValue = "false") Boolean isDiscipline) {
        GetAllEmployeeQuery query = new GetAllEmployeeQuery(isDiscipline);

        return this.queryGateway.query(query, ResponseTypes.multipleInstancesOf(EmployeeResponseModel.class)).join();
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponseModel getDetailEmployee(@PathVariable("employeeId") String employeeId) {
        GetDetailEmployeeQuery query = new GetDetailEmployeeQuery(employeeId);

        return this.queryGateway.query(query, ResponseTypes.instanceOf(EmployeeResponseModel.class)).join();
    }
}
