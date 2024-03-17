package DAOs;
import DAOs.MySqlDao;
import DAOs.UserDaoInterface;
import DTOs.User;
import Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class MySqlUserDao extends MySqlDao implements UserDaoInterface {
    /**
     * Will access and return a List of all users in User database table
     * @return List of User objects
     * @throws DaoException
     */
    @Override
    public List<User> findAllUsers() throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> usersList = new ArrayList<>();

        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "SELECT * FROM StudentGrades";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int userId = resultSet.getInt("id");
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                float grade = resultSet.getFloat("grade");
                String semester = resultSet.getString("semester");
                User u = new User(userId, studentId, firstName, lastName, courseId, courseName, grade, semester);
                usersList.add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUseresultSet() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return usersList;     // may be empty
    }

    /**
     * Given a username and password, find the corresponding User
     * @param studentId
     * @return User object if found, or null otherwise
     * @throws DaoException
     */
    public User findUserByStudentId(int studentId) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM `StudentGrades` WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int userId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                float grade = resultSet.getFloat("grade");
                String semester = resultSet.getString("semester");


                user=new User(userId, studentId, firstName, lastName, courseId, courseName, grade, semester);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findUserByFirstName() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findUserByFirstName() " + e.getMessage());
            }
        }
        return user;     // reference to User object, or null value
    }

    /**
     * Given a user ID, delete the corresponding User
     * @param userId
     * @return User object if found, or null otherwise
     * @throws DaoException
     */
    public void deleteUserById(int userId) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();

            String query = "DELETE FROM StudentGrades WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DaoException("No user found with ID " + userId + " to delete.");
            }
        } catch (SQLException e) {
            throw new DaoException("deleteUserById() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("deleteUserById() " + e.getMessage());
            }
        }
    }

    /**
     * Given a User object, insert a new User into the database
     * @param user
     * @return User object with ID field set
     * @throws DaoException
     */
    public User insertUser(User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = this.getConnection();

            String query = "INSERT INTO StudentGrades (student_id, first_name, last_name, course_id, course_name, grade, semester) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, user.getStudentId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getCourseId());
            preparedStatement.setString(5, user.getCourseName());
            preparedStatement.setFloat(6, user.getGrade());
            preparedStatement.setString(7, user.getSemester());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted == 0) {
                throw new DaoException("Failed to insert user into the database.");
            }

            // Retrieve auto-generated ID
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                user.setId(userId);
            } else {
                throw new DaoException("Failed to retrieve auto-generated ID for the inserted user.");
            }
        } catch (SQLException e) {
            throw new DaoException("insertUser() " + e.getMessage());
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("insertUser() " + e.getMessage());
            }
        }

        return user;
    }

    /**
     * Given a student ID and a User object, update the corresponding User in the database
     * @param studentId
     * @param user
     * @throws DaoException
     */
    public void updateUserByStudentId(int studentId, User user) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = this.getConnection();

            String query = "UPDATE StudentGrades SET first_name = ?, last_name = ?, course_id = ?, " +
                    "course_name = ?, grade = ?, semester = ? WHERE student_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getCourseId());
            preparedStatement.setString(4, user.getCourseName());
            preparedStatement.setFloat(5, user.getGrade());
            preparedStatement.setString(6, user.getSemester());
            preparedStatement.setInt(7, studentId);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DaoException("No user found with student ID " + studentId + " to update.");
            }
        } catch (SQLException e) {
            throw new DaoException("updateUserByStudentId() " + e.getMessage());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("updateUserByStudentId() " + e.getMessage());
            }
        }
    }

    /**
     * Given a Comparator, return a List of all users in User database table, sorted using the Comparator
     * @param comparator
     * @return List of User objects
     * @throws DaoException
     */
    public List<User> findUsersUsingFilter(Comparator<User> comparator) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> filteredUsers = new ArrayList<>();

        try {
            connection = this.getConnection();

            String query = "SELECT * FROM StudentGrades";
            preparedStatement = connection.prepareStatement(query);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                int studentId = resultSet.getInt("student_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                int courseId = resultSet.getInt("course_id");
                String courseName = resultSet.getString("course_name");
                float grade = resultSet.getFloat("grade");
                String semester = resultSet.getString("semester");
                User user = new User(userId, studentId, firstName, lastName, courseId, courseName, grade, semester);
                filteredUsers.add(user);
            }

            // Apply the filter
            filteredUsers.sort(comparator);

        } catch (SQLException e) {
            throw new DaoException("findUsersUsingFilter() " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException e) {
                throw new DaoException("findUsersUsingFilter() " + e.getMessage());
            }
        }

        return filteredUsers;
    }

}
