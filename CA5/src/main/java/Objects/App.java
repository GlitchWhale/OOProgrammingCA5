package Objects;

import java.util.InputMismatchException;
import java.util.Scanner;
import Comparators.UserGradeComparator;
import DAOs.MySqlUserDao;
import DAOs.UserDaoInterface;
import DTOs.User;
import Exceptions.DaoException;
import java.util.List;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDaoInterface IUserDao = new MySqlUserDao();

    public static void main(String[] args) {
        displayMenu();
    }

    private static void displayMenu() {
        int choice;
        do {
            try {
                System.out.println("\n---- Menu ----");
                System.out.println("1. Find All Users");
                System.out.println("2. Find User By Student ID");
                System.out.println("3. Delete User By Student ID");
                System.out.println("4. Insert New User");
                System.out.println("5. Update User By Student ID");
                System.out.println("6. Find Users Using Filter");
                System.out.println("8. Convert a single user to JSON");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                // Read user choice
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // Perform action based on choice
                switch (choice) {
                    case 1:
                        findAllUsers();
                        break;
                    case 2:
                        findUserByStudentId();
                        break;
                    case 3:
                        deleteUserByStudentId();
                        break;
                    case 4:
                        insertNewUser();
                        break;
                    case 5:
                        updateUserByStudentId();
                        break;
                    case 6:
                        findUsersUsingFilter();
                        break;
                    case 8:
                        convertUserToJson();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                // Handle incorrect input
                System.out.println("Invalid input. Please enter a valid choice.");
                scanner.nextLine(); // Clear the invalid input from the scanner
                choice = -1; // Reset the choice to trigger the loop to continue
            }
        } while (choice != 0);
    }

    private static void findAllUsers() {
        try {
            System.out.println("\n--- Finding all users ---");
            List<User> users = IUserDao.findAllUsers();
            displayUsers(users);
        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findUserByStudentId() {
        try {
            System.out.println("\n--- Finding user by student ID ---");
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            User user = IUserDao.findUserByStudentId(studentId);
            displayUser(user);
        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid student ID.");
            scanner.nextLine(); // Clear the invalid input from the scanner
        }
    }

    private static void deleteUserByStudentId() {
        try {
            System.out.println("\n--- Deleting user by student ID ---");
            System.out.print("Enter student ID to delete: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            boolean deletionResult = IUserDao.deleteUserByStudentId(studentId);
            if (deletionResult) {
                System.out.println("User with student ID " + studentId + " deleted successfully.");
            } else {
                System.out.println("No user found with student ID " + studentId + ".");
            }
        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid student ID.");
            scanner.nextLine(); // Clear the invalid input from the scanner
        }
    }

    private static void insertNewUser() {
        try {
            System.out.println("\n--- Inserting new user ---");
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter course ID: ");
            int courseId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter course name: ");
            String courseName = scanner.nextLine();
            System.out.print("Enter grade: ");
            float grade = scanner.nextFloat();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter semester: ");
            String semester = scanner.nextLine();

            User newUser = new User(studentId, firstName, lastName, courseId, courseName, grade, semester);
            User insertedUser = IUserDao.insertUser(newUser);
            System.out.println("User inserted successfully. ID: " + insertedUser.getId());
        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
            scanner.nextLine(); // Clear the invalid input from the scanner
        }
    }

    private static void updateUserByStudentId() {
        try {
            System.out.println("\n--- Updating user by student ID ---");
            System.out.print("Enter student ID to update: ");
            int studentIdToUpdate = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter new first name: ");
            String newFirstName = scanner.nextLine();
            System.out.print("Enter new last name: ");
            String newLastName = scanner.nextLine();
            System.out.print("Enter new course ID: ");
            int newCourseId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new course name: ");
            String newCourseName = scanner.nextLine();
            System.out.print("Enter new grade: ");
            float newGrade = scanner.nextFloat();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new semester: ");
            String newSemester = scanner.nextLine();

            User updatedUser = new User(newFirstName, newLastName, newCourseId, newCourseName, newGrade, newSemester);
            IUserDao.updateUserByStudentId(studentIdToUpdate, updatedUser);
            System.out.println("User updated successfully.");
        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid data.");
            scanner.nextLine(); // Clear the invalid input from the scanner
        }
    }


    private static void findUsersUsingFilter() {
        try {
            System.out.println("\n--- Finding users using filter ---");
            // Call DAO method to find users using a filter
            List<User> filteredUsers = IUserDao.findUsersUsingFilter(new UserGradeComparator());
            // Display filtered users
            displayUsers(filteredUsers);
        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayUsers(List<User> users) {
        // Display header
        System.out.printf("%-10s %-10s %-15s %-15s %-10s %-20s %-10s %-10s%n", "ID", "Student ID", "First Name", "Last Name", "Course ID", "Course Name", "Grade", "Semester");
        // Display each user
        for (User user : users) {
            displayUser(user);
        }
    }

    private static void displayUser(User user) {
        if (user != null) {
            System.out.printf("%-10d %-10d %-15s %-15s %-10d %-20s %-10.2f %-10s%n",
                    user.getId(), user.getStudentId(), user.getFirstName(), user.getLastName(),
                    user.getCourseId(), user.getCourseName(), user.getGrade(), user.getSemester());
        } else {
            System.out.println("User not found.");
        }
    }

    private static void convertUserToJson() {
        try {
            System.out.println("\n--- Converting user to JSON ---");
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String userJson = IUserDao.findUserJsonByStudentId(studentId);
            System.out.println(userJson);
        } catch (DaoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid student ID.");
            scanner.nextLine(); // Clear the invalid input from the scanner
        }
    }
}
