package com.elyashevich.jdbc.dao.impl;

import com.elyashevich.jdbc.dao.EmployeeDao;
import com.elyashevich.jdbc.entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {

    private final Connection connection;

    private final static String SELECT_ALL_QUERY =
            "SELECT ID, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS_ID FROM EMPLOYEE";
    private final static String SELECT_BY_ID_QUERY =
            "SELECT ID, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS_ID FROM EMPLOYEE WHERE ID=?";
    private final static String INSERT_QUERY =
            "INSERT INTO EMPLOYEE (ID, FIRST_NAME, LAST_NAME, BIRTHDAY, ADDRESS_ID) VALUES(?, ?, ?, ?, ?)";
    private final static String UPDATE_QUERY = "UPDATE EMPLOYEE SET FIRST_NAME=?, LAST_NAME=?, BIRTHDAY=?, ADDRESS_ID=? WHERE ID=?";
    private final static String DELETE_QUERY = "DELETE FROM EMPLOYEE WHERE ID=?";

    public EmployeeDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

            while (resultSet.next()) {
                Employee employee = new Employee();

                employee.setId(resultSet.getLong("ID"));
                employee.setFirstname(resultSet.getString("FIRST_NAME"));
                employee.setLastname(resultSet.getString("LAST_NAME"));
                employee.setAddressId(resultSet.getLong("ADDRESS_ID"));
                employee.setBirthDate(resultSet.getDate("BIRTHDATE"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
        return employees;
    }

    @Override
    public Employee findById(Long id) throws SQLException {
        Employee employee = new Employee();

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            employee.setId(resultSet.getLong("ID"));
            employee.setFirstname(resultSet.getString("FIRST_NAME"));
            employee.setLastname(resultSet.getString("LAST_NAME"));
            employee.setBirthDate(resultSet.getDate("BIRTHDATE"));
            employee.setAddressId(resultSet.getLong("ADDRESS_ID"));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
        return employee;
    }

    @Override
    public void create(Employee employee) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setLong(1, employee.getId());
            preparedStatement.setString(2, employee.getFirstname());
            preparedStatement.setString(3, employee.getLastname());
            preparedStatement.setDate(4, employee.getBirthDate());
            preparedStatement.setLong(5, employee.getAddressId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
    }

    @Override
    public void update(Employee employee) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, employee.getFirstname());
            preparedStatement.setString(2, employee.getLastname());
            preparedStatement.setDate(3, employee.getBirthDate());
            preparedStatement.setLong(4, employee.getAddressId());
            preparedStatement.setLong(5, employee.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
    }

    @Override
    public void remove(Employee employee) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, employee.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
    }
}
