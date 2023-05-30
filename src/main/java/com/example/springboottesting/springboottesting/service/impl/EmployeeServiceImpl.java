package com.example.springboottesting.springboottesting.service.impl;

import com.example.springboottesting.springboottesting.exception.ResourceNotFoundException;
import com.example.springboottesting.springboottesting.model.Employee;
import com.example.springboottesting.springboottesting.repository.EmployeeRepository;
import com.example.springboottesting.springboottesting.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee=employeeRepository.findByEmail(employee.getEmail());
        //20 and 28 is enought for saveEmployee
        //simply condition check
        if(savedEmployee.isPresent())
        {
            throw new ResourceNotFoundException("This email id is already present"+ employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {


        return  employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmpoyeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);

    }




    @Override
    public Employee updateEmployee(Employee updateemployee) {
        return employeeRepository.save(updateemployee);
    }
}
