package com.cglia.springboot.webservices.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cglia.springboot.webservices.entity.Employee;
import com.cglia.springboot.webservices.exceptionHandling.GlobalNotFoundException;
import com.cglia.springboot.webservices.service.EmployeeService;


@RestController
@RequestMapping("/v1.0/api")
@CrossOrigin("*")
public class BackendController {

    private EmployeeService employeeService;

    @Autowired
    public BackendController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") int empId) {
        Employee employee = employeeService.findById(empId);
        if (employee == null) {
            throw new GlobalNotFoundException("Employee Id: " + empId + " not found");
        }
            return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        int result = employeeService.save(employee);
        if (result > 0) {
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int empId, @RequestBody Employee employee) {
        employee.setEmpId(empId);
        int result = employeeService.update(empId, employee);
        if (result > 0) {
            return new ResponseEntity<>(employee, HttpStatus.NO_CONTENT);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable("id") int empId) {
    	Employee tempEmployee = employeeService.findById(empId);
		if(tempEmployee == null) {
			throw new GlobalNotFoundException ("Employee Id : " + empId + " not Found ");
		}
		   employeeService.deleteById(empId);
		   Map<String,Boolean> response = new HashMap();
		   response.put("deleted", Boolean.TRUE);
		   return ResponseEntity.ok(response);
	}
}
