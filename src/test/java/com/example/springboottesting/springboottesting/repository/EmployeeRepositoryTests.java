package com.example.springboottesting.springboottesting.repository;

import com.example.springboottesting.springboottesting.model.Employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    EmployeeRepository employeeRepository;

    //junit test for save employee operation
    @DisplayName("Saved Employee Test")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("kumutha")
                .lastName("Srikanth")
                .email("kumutha@gmail.com")
                .build();


        //when - action or the behaviout that we are going test
        Employee savedEmployee = employeeRepository.save(employee);
        //then-verify the output

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    @DisplayName("Find All EmployeeList")
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnAllEmployeeList() {

        //given-precondition or setup

        Employee employee = Employee.builder()
                .firstName("kumutha")
                .lastName("Srikanth")
                .email("kumutha@gmail.com")
                .build();

        Employee employee1 = Employee.builder()
                .firstName("Vino")
                .lastName("Loganthan")
                .email("vino@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);


        //when - action or the behaviout that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        //then-verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);


    }

    @DisplayName("Find EmployeeId")
    @Test
    public void givenEmployeeList_whenGiveId_thenReturnEmploeeDetails() {

        //given-precondition or setup

        Employee employee = Employee.builder()
                .firstName("kumutha")
                .lastName("Srikanth")
                .email("kumutha@gmail.com")
                .build();


        employeeRepository.save(employee);


        //when - action or the behaviout that we are going test

        Employee findEmployee = employeeRepository.findById(employee.getId()).get();

        //then-verify the output
        assertThat(findEmployee).isNotNull();
        assertThat(findEmployee.getFirstName()).isEqualTo("vino");


    }

    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ezhil")
                .lastName("Vino")
                .email("ezhil@gmail.com")
                .build();

        employeeRepository.save(employee);
        //when - action or the behaviout that we are going test
        Employee employeeEmail = employeeRepository.findByEmail(employee.getEmail()).get();
        //then-verify the output
        assertThat(employeeEmail).isNotNull();
        assertThat(employeeEmail.getEmail()).isEqualTo("ezhil@gmail.com");

    }

    @DisplayName("Update Employee")
    @Test
    public void givenEmployee_whenUpdateEmail_thenReturnEmployeeObject() {

        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ezhil")
                .lastName("Vino")
                .email("ezhil@gmail.com")
                .build();

        employeeRepository.save(employee);
        //when - action or the behaviout that we are going test
        Employee employeeEmail = employeeRepository.findById(employee.getId()).get();
        employeeEmail.setEmail("Arulezhilan@gmail.com");
        employeeEmail.setFirstName("Arulezhilan");
        employeeRepository.save(employeeEmail);
        //then-verify the output
        assertThat(employeeEmail.getEmail()).isEqualTo("Arulezhilan@gmail.com");
        assertThat(employeeEmail.getFirstName()).isEqualTo("Arulezhilan");

    }

    @DisplayName("Delete Employee")
    @Test
    public void givenEmployee_whenDeleteEmployee_thenReturnEmployeeObjectShouldDelete() {

        //given-precondition or setup
        Employee employee = Employee.builder()
                .firstName("Ezhil")
                .lastName("Vino")
                .email("ezhil@gmail.com")
                .build();

        employeeRepository.save(employee);
        //when - action or the behaviout that we are going test

        employeeRepository.delete(employee);
        Optional<Employee> emplist = employeeRepository.findById(employee.getId());
        //Employee employeeEmail = employeeRepository.findById(employee.getId()).get();

        //then-verify the output
        assertThat(emplist.isEmpty());


    }


}
