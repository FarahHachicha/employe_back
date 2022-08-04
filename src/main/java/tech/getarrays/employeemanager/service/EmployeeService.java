package tech.getarrays.employeemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.getarrays.employeemanager.exception.UserNotFoundException;
import tech.getarrays.employeemanager.model.Employee;
import tech.getarrays.employeemanager.repo.EmployeeRepo;

import java.util.List;
import java.util.UUID;
@Service

public class EmployeeService {
	@Autowired
    private final EmployeeRepo employeeRepo; 

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    public Employee addEmployee(Employee employee){
        return employeeRepo.save(employee);
    }
    public List<Employee> findAllEmployees(){

    return employeeRepo.findAll();
    }

    public Employee findEmployeeById(Long id){
    return employeeRepo.findById(id)
            .orElseThrow(()-> new UserNotFoundException(" User by id "+id+" not found"));
    }

    public Employee updateEmployee (Employee employee){

    return employeeRepo.save(employee);
    }

    public void deleteEmployee(Long id){
        employeeRepo.deleteById(id);
    }
}
