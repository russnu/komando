package com.russel.komando.util;

import com.russel.komando.entity.EmployeeData;
import com.russel.komando.model.Employee;

public class EmployeeMapper {
    public static Employee toModel(EmployeeData employeeData) {
        if (employeeData == null) return null;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeData.getEmployeeId());
        employee.setFirstName(employeeData.getFirstName());
        employee.setLastName(employeeData.getLastName());
        employee.setEmployeeTitle(employeeData.getEmployeeTitle());
        return employee;
    }

    public static EmployeeData toEntity(Employee employee) {
        if (employee == null) return null;
        EmployeeData employeeData = new EmployeeData();
        employeeData.setEmployeeId(employee.getEmployeeId());
        employeeData.setFirstName(employee.getFirstName());
        employeeData.setLastName(employee.getLastName());
        employeeData.setEmployeeTitle(employee.getEmployeeTitle());

        return employeeData;
    }
}
