package com.example.springboottesting.springboottesting.service;

import com.example.springboottesting.springboottesting.exception.ResourceNotFoundException;
import com.example.springboottesting.springboottesting.model.Employee;
import com.example.springboottesting.springboottesting.repository.EmployeeRepository;
import com.example.springboottesting.springboottesting.service.impl.EmployeeServiceImpl;
import org.aspectj.weaver.patterns.IVerificationRequired;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.DoesNothing;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    /*
        private EmployeeRepository employeeRepository;
        private EmployeeService employeeService;
    */
    @BeforeEach
    public void setup() {


        // employeeRepository= Mockito.mock(EmployeeRepository.class);
        //  employeeService=new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .email("kumu@gmail.com")
                .firstName("Kumutha")
                .lastName("Srikanth")
                .build();
    }


    //junit test for savedEmployee method
    @DisplayName("Save Employee")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        //given-precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        //when - action or the behaviout that we are going test
        Employee saveEmployee = employeeService.saveEmployee(employee);

        //then-verify the output
        Assertions.assertThat(saveEmployee).isNotNull();


    }
    //junit test for savedEmployee method


    @DisplayName("If email isaPresent then throw an Exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when -  action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }
    @DisplayName("Find All Employee EmptyList")
    @Test
    public void givenEmployeeObject_whenFindall_thenReturnEmployeeList() {

        //given-precondition or setup
        //BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
       Employee employee1 = Employee.builder()
                .id(1L)
                .email("Vino@gmail.com")
                .firstName("Vino")
                .lastName("Loganathan")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        //when - action or the behaviout that we are going test
        List<Employee> EmployeeList = employeeService.getAllEmployee();

        //then-verify the output
        Assertions.assertThat(EmployeeList).isNotNull();
        Assertions.assertThat(EmployeeList.size()).isEqualTo(2);


    }
    @DisplayName("FindAll Employee")
    @Test
    public void givenEmployeeEmptyObject_whenFindall_thenReturnEmployeeEmployeeList() {

        //given-precondition or setup
        //BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        Employee employee1 = Employee.builder()
                .id(1L)
                .email("Vino@gmail.com")
                .firstName("Vino")
                .lastName("Loganathan")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or the behaviout that we are going test
        List<Employee> EmployeeList = employeeService.getAllEmployee();

        //then-verify the output
        Assertions.assertThat(EmployeeList).isEmpty();
        Assertions.assertThat(EmployeeList.size()).isEqualTo(0);


    }
    @DisplayName("FindById Employee")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployee() {

        //given-precondition or setup
        //BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());

        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or the behaviout that we are going test
        Employee employeeById  = employeeService.getEmpoyeeById(this.employee.getId()).get();

        //then-verify the output
        Assertions.assertThat(employeeById).isNotNull();
        Assertions.assertThat(employeeById.getFirstName()).isEqualTo("Kumutha");


    }
@DisplayName("Update Employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given-precondition or setup
        //BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        //System.out.println(employee);
        employee.setFirstName("KUMS");
        employee.setEmail("KUMS@gmail.com");
        given(employeeRepository.save(employee)).willReturn(employee);
        //System.out.println(employee);
        //when - action or the behaviout that we are going test
        Employee Updateemployee = employeeService.updateEmployee(employee);

        //then-verify the output
        Assertions.assertThat(Updateemployee).isNotNull();
        Assertions.assertThat(Updateemployee.getFirstName()).isEqualTo("KUMS");


    }
    @DisplayName("Delete Employee")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenReturnRemainingEmployee() {

        //given-precondition or setup
        willDoNothing().given(employeeRepository).delete(employee);
        //when - action or the behaviout that we are going test
        employeeService.deleteEmployee(employee);
        //then-verify the output
        verify(employeeRepository,times(1)).delete(employee);

    }


}
