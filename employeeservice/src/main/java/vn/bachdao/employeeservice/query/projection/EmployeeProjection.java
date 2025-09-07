package vn.bachdao.employeeservice.query.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import vn.bachdao.employeeservice.command.data.Employee;
import vn.bachdao.employeeservice.command.data.EmployeeRepository;
import vn.bachdao.employeeservice.query.model.EmployeeResponseModel;
import vn.bachdao.employeeservice.query.queries.GetAllEmployeeQuery;
import vn.bachdao.employeeservice.query.queries.GetDetailEmployeeQuery;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeProjection {

    private final EmployeeRepository employeeRepository;

    public EmployeeProjection(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @QueryHandler
    public List<EmployeeResponseModel> handle(GetAllEmployeeQuery query) {
        List<Employee> list = this.employeeRepository.findAllByIsDiscipline(query.getIsDiscipline());

        return list.stream().map(employee -> {
            EmployeeResponseModel model = new EmployeeResponseModel();
            BeanUtils.copyProperties(employee, model);
            return model;
        }).collect(Collectors.toList());
    }

    @QueryHandler
    public EmployeeResponseModel handle(GetDetailEmployeeQuery query) throws Exception {
        Employee employee = this.employeeRepository.findById(query.getId()).orElseThrow(() -> new Exception("Not found Employee with employeeId: " + query.getId()));

        EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
        BeanUtils.copyProperties(employee, employeeResponseModel);
        return employeeResponseModel;
    }
}
