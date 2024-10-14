package com.elyashevich.jdbc;

import com.elyashevich.jdbc.dao.AddressDao;
import com.elyashevich.jdbc.dao.EmplProjDao;
import com.elyashevich.jdbc.dao.EmployeeDao;
import com.elyashevich.jdbc.dao.ProjectDao;
import com.elyashevich.jdbc.dao.impl.AddressDaoImpl;
import com.elyashevich.jdbc.dao.impl.EmplProjDaoImpl;
import com.elyashevich.jdbc.dao.impl.EmployeeDaoImpl;
import com.elyashevich.jdbc.dao.impl.ProjectDaoImpl;
import com.elyashevich.jdbc.db.Util;
import com.elyashevich.jdbc.entity.Address;
import com.elyashevich.jdbc.entity.EmplProj;
import com.elyashevich.jdbc.entity.Employee;
import com.elyashevich.jdbc.entity.Project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;


public class Application {

    public static void main(String[] args) throws SQLException {
        Util util = new Util();
        Connection connection = util.getConnection();
        AddressDao addressDao = new AddressDaoImpl(connection);
        EmployeeDao employeeDao = new EmployeeDaoImpl(connection);
        ProjectDao projectDao = new ProjectDaoImpl(connection);
        EmplProjDao emplProjDao = new EmplProjDaoImpl(connection);

        Address address = new Address();
        address.setId(1L);
        address.setCountry("DC");
        address.setCity("Gotham City");
        address.setStreet("Arkham street 1");
        address.setPostCode("12345");

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstname("James");
        employee.setLastname("Gordon");

        Calendar calendar = Calendar.getInstance();
        calendar.set(1939, Calendar.MAY, 1);

        employee.setBirthDate(new Date(calendar.getTime().getTime()));
        employee.setAddressId(address.getId());

        Project project = new Project();
        project.setId(1L);
        project.setTitle("Gotham City Police Department Commissioner");

        EmplProj emplProj = new EmplProj();
        emplProj.setEmployeeId(employee.getId());
        emplProj.setProjectId(project.getId());

        try {
            addressDao.create(address);
            employeeDao.create(employee);
            projectDao.create(project);
            emplProjDao.create(emplProj);

            List<Address> addressList = addressDao.findAll();
            List<Employee> employeeList = employeeDao.findAll();
            for (Employee e : employeeList) {
                System.out.println(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
