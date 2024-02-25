package com.microservice.departmentservice.controller;

import com.microservice.departmentservice.client.EmployeeClient;
import com.microservice.departmentservice.model.Department;
import com.microservice.departmentservice.repository.DepartmentRepository;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentCOntroller {


    private static final Logger logger = LoggerFactory.getLogger(DepartmentCOntroller.class);


    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add(@RequestBody Department department) {
        logger.info("Department add: {}", department);
        return  departmentRepository.addDepartment(department);

    }

    @GetMapping
    public List<Department> findAll(){
        logger.info("Department find");
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id) {
        logger.info("Department find by id: {}", id);
        return departmentRepository.findById(id);
    }

    @GetMapping("/with-employee")
    public List<Department> findAllWithEmployee(){
        logger.info("Department findAllWithEmployee");
        List<Department> departments =  departmentRepository.findAll();

        departments.forEach(department -> department
                .setEmployees(employeeClient
                        .findByDepartment(department.getId())));
        return departments;

    }

}
