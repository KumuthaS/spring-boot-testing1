package com.example.springboottesting.springboottesting.controller;

import com.example.springboottesting.springboottesting.model.Employee;
import com.example.springboottesting.springboottesting.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.Node;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTessts {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;


    @Autowired
    ObjectMapper objectMapper;


    @DisplayName("Save Employee Controller")
    @Test
    public void givenEmployeeObject_whenCallSaveEmployee_thenReturnEmployeeSave() throws Exception {

        //given=precondition or setup
        Employee employee = Employee.builder()

                .firstName("Kumutha")
                .lastName("S")
                .email("kumu@gmail.com")
                .build();
        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when- Action
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then- Return or assertion or verify
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }


    @DisplayName("Get All Employee Controller")
    @Test
    public void givenEmployeeObject_whenCallGetAllEmployee_thenReturnAllEmployees() throws Exception {

        //given=precondition or setup
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("Kumutha").lastName("s").email("kumu@gmail.com")
                .build());
        employeeList.add(Employee.builder().firstName("Kumutha").lastName("s").email("kumu@gmail.com")
                .build());


        BDDMockito.given(employeeService.getAllEmployee())
                .willReturn(employeeList);

        //when- Action
        ResultActions response = mockMvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeList)));

        //then- Return or assertion or verify
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employeeList.size())));


    }

    @DisplayName("Get All EmployeeById Controller")
    @Test
    public void givenEmployeeObject_whenCallGetAllEmployeeById_thenReturnEmployees() throws Exception {

        //given=precondition or setup
        Long EmployeeId = 1L;
        Employee employee = Employee.builder()

                .firstName("Kumutha")
                .lastName("S")
                .email("kumu@gmail.com")
                .build();


        BDDMockito.given(employeeService.getEmpoyeeById(EmployeeId))
                .willReturn(java.util.Optional.of(employee));

        //when- Action
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", EmployeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then- Return or assertion or verify
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));


    }

    @DisplayName("Get EmployeeById NotFound Controller")
    @Test
    public void givenEmployeeObject_whenCallGetAllEmployeeByIdWhichNotPresent_thenReturnEmptyList() throws Exception {

        //given=precondition or setup
        Long EmployeeId = 1L;
        Employee employee = Employee.builder()

                .firstName("Kumutha")
                .lastName("S")
                .email("kumu@gmail.com")
                .build();


        BDDMockito.given(employeeService.getEmpoyeeById(EmployeeId))
                .willReturn(Optional.empty());

        //when- Action
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", EmployeeId));


        //then- Return or assertion or verify
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("Update Employee")
    @Test
    public void givenEmployeeObject_whenCallUpdateEmployeeById_thenReturnUpdatedEmployee() throws Exception {

        //given=precondition or setup
        Long EmployeeId = 1L;
        Employee savedemployee = Employee.builder()

                .firstName("Kumutha")
                .lastName("S")
                .email("kumu@gmail.com")
                .build();
        Employee updateemployee = Employee.builder()

                .firstName("Vino")
                .lastName("L")
                .email("Vino@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmpoyeeById(EmployeeId)).willReturn(Optional.of(savedemployee));
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));


        //when- Action
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", EmployeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateemployee)));


        //then- Return or assertion or verify
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updateemployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updateemployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updateemployee.getEmail())));


    }

    @DisplayName("Update Employee")
    @Test
    public void givenEmployeeObject_whenCallUpdateEmployeeByIdIfNotPresent_thenReturnwithSaveEmployee() throws Exception {

        //given=precondition or setup
        Long EmployeeId = 1L;
        Employee savedemployee = Employee.builder()

                .firstName("Kumutha")
                .lastName("S")
                .email("kumu@gmail.com")
                .build();
        Employee updateemployee = Employee.builder()

                .firstName("Vino")
                .lastName("L")
                .email("Vino@gmail.com")
                .build();
        BDDMockito.given(employeeService.getEmpoyeeById(EmployeeId)).willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));


        //when- Action
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", EmployeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateemployee)));


        //then- Return or assertion or verify
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());


    }

    @DisplayName("Delete Employee")
    @Test
    public void givenEmployeeObject_whenCallDeleteEmployee_thenReturnDeleteEmployee() throws Exception {

        //given=precondition or setup

        Employee employee = Employee.builder()
                .id(1L)
                .firstName("Kumutha")
                .lastName("S")
                .email("kumu@gmail.com")
                .build();

        willDoNothing().given(employeeService).deleteEmployee(employee);

        //when- Action
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}",employee));
        //then- Return or assertion or verify
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
}
