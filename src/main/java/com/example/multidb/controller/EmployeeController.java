package com.example.multidb.controller;

import com.example.multidb.model.Employee;
import com.example.multidb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/all")
    public List<Employee> getAllemployees() {
        return employeeService.getAll();
    }

    @PostMapping("/add")
    public void addOneEmployees(@RequestBody Employee employee) {
        employeeService.addOne(employee);
    }

}
