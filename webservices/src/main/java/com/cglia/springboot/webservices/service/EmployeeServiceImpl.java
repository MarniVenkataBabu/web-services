package com.cglia.springboot.webservices.service;

import com.cglia.springboot.webservices.service.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.cglia.springboot.webservices.dao.BackendServiceClient;
import com.cglia.springboot.webservices.entity.Employee;
import com.cglia.springboot.webservices.exceptionHandling.GlobalErrorResponse;
import com.cglia.springboot.webservices.exceptionHandling.GlobalNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class EmployeeServiceImpl implements EmployeeService {
   
    private BackendServiceClient backendServiceClient;
    
    public EmployeeServiceImpl(BackendServiceClient backendServiceClient) {
        this.backendServiceClient = backendServiceClient;
    }
    
    @Override
    public List<Employee> findAll() {
        try {
            String responseData = backendServiceClient.getData();
            // Assuming the response data is in a specific format (e.g., JSON)
            // You need to parse the response data and convert it into a list of Employee objects
            return parseResponseData(responseData);
        } catch (IOException e) {
            // Handle exception appropriately
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
    @Override
    public Employee findById(int id) {
        try {
            String responseData = backendServiceClient.getSingleData(id);
            if (responseData != null) {
                return parseResponseDataTwo(responseData);
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int save(Employee employee) {
        try {
            // Convert the Employee object to JSON
            String employeeData = convertEmployeeToJson(employee);
            
            backendServiceClient.insertData(employeeData);
        } catch (IOException e) {
            // Handle exception appropriately
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public void deleteById(int id) {
    	try {
			String responseData = backendServiceClient.getSingleData(id);
			if(responseData != null) {
				backendServiceClient.deleteData(id);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @Override
	public int update(int id, Employee employee) {
        try {
            // Convert the Employee object to JSON
            String employeeData = convertEmployeeToJson(employee);
            
            backendServiceClient.updateData(id, employeeData);
        } catch (IOException e) {
            // Handle exception appropriately
            e.printStackTrace();
        }
        return 1;
    }
    //Convert response data to List
    private List<Employee> parseResponseData(String responseData) {
        Gson gson = new Gson();
        return gson.fromJson(responseData, new TypeToken<List<Employee>>() {}.getType());
    }
    //Convert Employee to JSON
    private String convertEmployeeToJson(Employee employee) {
        Gson gson = new Gson();
        return gson.toJson(employee);
    }
    
    //convert response data to Employee
    private Employee parseResponseDataTwo(String responseData) {
        Gson gson = new Gson();
        if(responseData == null) {
        	throw new GlobalNotFoundException("Employee id not found");
        }
        return gson.fromJson(responseData, Employee.class);
    }
}
