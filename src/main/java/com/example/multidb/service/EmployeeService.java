package com.example.multidb.service;

import com.example.multidb.master.repo.MasterEmployeeRepository;
import com.example.multidb.model.Employee;
import com.example.multidb.slave.repo.SlaveEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private MasterEmployeeRepository masterEmployeeRepository;

    @Autowired
    private SlaveEmployeeRepository slaveEmployeeRepository;

    public List<Employee> getAll() {
        return slaveEmployeeRepository.findAll();
    }

    public void addOne(Employee employee) {
        masterEmployeeRepository.save(employee);
    }
}
