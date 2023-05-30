package com.example.springboottesting.springboottesting.controller;

import com.example.springboottesting.springboottesting.model.Employee;
import com.example.springboottesting.springboottesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee)
    {
        return employeeService.saveEmployee(employee);
    }


    @GetMapping

    public List<Employee> getAllEmployee()
    {
        return employeeService.getAllEmployee();
    }


    @GetMapping("{id}")

    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id)
    {
        return employeeService.getEmpoyeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,@RequestBody Employee employee){
        return employeeService.getEmpoyeeById(id)
                .map(savedEmployee->{
                    savedEmployee.setFirstName(employee.getFirstName());
                    savedEmployee.setLastName(employee.getLastName());
                    savedEmployee.setEmail(employee.getEmail());

                    Employee updateemployee  = employeeService.updateEmployee(savedEmployee);
                    return new ResponseEntity<>(updateemployee,HttpStatus.OK);
                })
                .orElseGet(()->ResponseEntity.notFound().build());

    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Employee employee)
    {
        employeeService.deleteEmployee(employee);

        return new ResponseEntity<String>("The Employee deleted Successfully",HttpStatus.OK);
    }

}
