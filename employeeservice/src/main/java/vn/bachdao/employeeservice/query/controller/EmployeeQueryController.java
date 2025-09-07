package vn.bachdao.employeeservice.query.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;
import vn.bachdao.employeeservice.query.model.EmployeeResponseModel;
import vn.bachdao.employeeservice.query.queries.GetAllEmployeeQuery;
import vn.bachdao.employeeservice.query.queries.GetDetailEmployeeQuery;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeQueryController {

    private final QueryGateway queryGateway;

    public EmployeeQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

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
