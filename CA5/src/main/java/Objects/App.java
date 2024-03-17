package Objects;



import DAOs.MySqlDao;
import DAOs.MySqlUserDao;
import DAOs.UserDaoInterface;
import DTOs.User;
import Exceptions.DaoException;
import java.util.List;

public class App
{
    public static void main(String[] args)
    {
        UserDaoInterface IUserDao = new MySqlUserDao();  //"IUserDao" -> "I" stands for for



        try
        {
            System.out.println("\nCall findAllUsers()");
            List<User> users = IUserDao.findAllUsers();     // call a method in the DAO

            if( users.isEmpty() )
                System.out.println("There are no Users");
            else {
                for (User user : users)
                    System.out.println("User: " + user.toString());
            }

            // test dao - with username and password that we know are present in the database
            System.out.println("\nCall: findUserByUsernamePassword()");
            String first_name = "smith";
            String student_grade = "password";
            User user = IUserDao.findUserByUsernamePassword(first_name, student_grade);

            if( user != null ) // null returned if userid and password not valid
                System.out.println("User found: " + user);
            else
                System.out.println("Username with that password not found");

            // test dao - with an invalid username (i.e. not in database)
            first_name = "madmax";
            student_grade = "thunderdome";
            user = IUserDao.findUserByUsernamePassword(first_name, student_grade);
            if(user != null)
                System.out.println("Username: " + first_name + " was found: " + user);
            else
                System.out.println("Username: " + first_name + ", password: " + student_grade +" is not valid.");

            // code to delete a user by ID
            System.out.println("\nDeleting user with ID 123...");
            try {
                IUserDao.deleteUserById(123);
                System.out.println("User deleted successfully.");
            } catch (DaoException deleteException) {
                System.out.println("Error deleting user: " + deleteException.getMessage());
            }

            try {
                UserDaoInterface userDao = new MySqlUserDao();
                User newUser = new User(110, "John", "Doe", 304, "Math", 95.5f, "Semester 1");
                User insertedUser = userDao.insertUser(newUser);
                System.out.println("User inserted successfully. ID: " + insertedUser.getId());
            } catch (DaoException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }

    }


}