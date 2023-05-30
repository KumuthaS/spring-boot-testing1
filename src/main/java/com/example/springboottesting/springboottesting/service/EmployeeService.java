package com.example.springboottesting.springboottesting.service;

import com.example.springboottesting.springboottesting.model.Employee;
import com.example.springboottesting.springboottesting.service.impl.EmployeeServiceImpl;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployee();
    Optional<Employee> getEmpoyeeById(Long id);

    void deleteEmployee(Employee employee);

    Employee updateEmployee(Employee updateemployee);


}
