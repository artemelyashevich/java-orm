package com.elyashevich.jdbc.dao.impl;

import com.elyashevich.jdbc.dao.EmplProjDao;
import com.elyashevich.jdbc.entity.EmplProj;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmplProjDaoImpl implements EmplProjDao {

    private final Connection connection;

    private final static String SELECT_ALL_QUERY =
            "SELECT EMPLOYEE_ID, PROJECT_ID FROM EMPL_PROJ";
    private final static String SELECT_BY_ID_QUERY =
            "SELECT EMPLOYEE_ID, PROJECT_ID FROM EMPL_PROJ WHERE ID=?";
    private final static String INSERT_QUERY =
            "INSERT INTO EMPL_PROJ (EMPLOYEE_ID, PROJECT_ID) VALUES (?, ?)";

    private final static String UPDATE_QUERY = "UPDATE EMPL_PROJ SET EMPLOYEE_ID=?, PROJECT_ID=? WHERE EMPLOYEE_ID=? AND PROJECT_ID=?";
    private final static String DELETE_QUERY = "DELETE FROM EMPL_PROJ WHERE ID=?";

    public EmplProjDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<EmplProj> findAll() throws SQLException {
        List<EmplProj> result = new ArrayList<>();

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

            while (resultSet.next()) {
                EmplProj emplProj = new EmplProj();

                emplProj.setEmployeeId(resultSet.getLong("EMPLOYEE_ID"));
                emplProj.setProjectId(resultSet.getLong("PROJECT_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
        return null;
    }

    @Override
    public EmplProj findById(Long id) throws SQLException {
        EmplProj emplProj = new EmplProj();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            emplProj.setEmployeeId(resultSet.getLong("EMPLOYEE_ID"));
            emplProj.setProjectId(resultSet.getLong("PROJECT_ID"));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (this.connection != null) {
                this.connection.close();
            }
        }
        return emplProj;
    }

    @Override
    public void create(EmplProj emplProj) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setLong(1, emplProj.getEmployeeId());
            preparedStatement.setLong(2, emplProj.getProjectId());

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
    public void update(EmplProj emplProj) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setLong(1, emplProj.getEmployeeId());
            preparedStatement.setLong(2, emplProj.getProjectId());
            preparedStatement.setLong(3, emplProj.getEmployeeId());
            preparedStatement.setLong(4, emplProj.getProjectId());

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
    public void remove(EmplProj emplProj) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, emplProj.getEmployeeId());
            preparedStatement.setLong(2, emplProj.getProjectId());
            preparedStatement.setLong(3, emplProj.getEmployeeId());
            preparedStatement.setLong(4, emplProj.getProjectId());

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
