package com.elyashevich.jdbc.dao.impl;

import com.elyashevich.jdbc.dao.ProjectDao;
import com.elyashevich.jdbc.entity.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {

    private final Connection connection;

    private final static String SELECT_ALL_QUERY = "SELECT ID, TITLE FROM PROJECT;";
    private final static String SELECT_BY_ID_QUERY = "SELECT ID, TITLE FROM PROJECT WHERE ID=?;";
    private final static String INSERT_QUERY = "INSERT INTO PROJECT (ID, TITLE) VALUES (?, ?);";
    private final static String UPDATE_QUERY = "UPDATE PROJECT SET TITLE=? WHERE ID=?;";
    private final static String DELETE_QUERY = "DELETE FROM PROJECT WHERE ID=?;";

    public ProjectDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Project> findAll() throws SQLException {
        List<Project> projects = new ArrayList<>();

        try (Statement statement = this.connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(SELECT_ALL_QUERY);

            while (resultSet.next()) {
                Project project = new Project();

                project.setId(resultSet.getLong("ID"));
                project.setTitle(resultSet.getString("TITLE"));

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Project findById(Long id) throws SQLException {
        PreparedStatement preparedStatement = null;

        Project project = new Project();

        try {
            preparedStatement = this.connection.prepareStatement(SELECT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            project.setId(resultSet.getLong("ID"));
            project.setTitle(resultSet.getString("TITLE"));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return project;
    }

    @Override
    public void create(Project project) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setLong(1, project.getId());
            preparedStatement.setString(2, project.getTitle());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Project project) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setLong(2, project.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Project project) throws SQLException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, project.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
