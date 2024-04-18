package Client;

import Server.DTOs.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client {

    private static final int SERVER_PORT = 8080;
    private static final String SERVER_HOST = "localhost";

    private static final Map<Integer, String> COMMANDS = new HashMap<>();
    static {
        COMMANDS.put(1, "Find All Users");
        COMMANDS.put(2, "Find User By Student ID");
        COMMANDS.put(3, "Delete User By Student ID");
        COMMANDS.put(4, "Insert New User");
        COMMANDS.put(5, "Update User By Student ID");
        COMMANDS.put(6, "Find Users Using Filter");
        COMMANDS.put(7, "Convert List of Users to JSON");
        COMMANDS.put(8, "Convert a single user to JSON");
        COMMANDS.put(9, "Display Entity by Id");
        COMMANDS.put(10, "Display All Entities"); // Added for Feature 10
        COMMANDS.put(0, "Exit");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        try (
                Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Client running and Connected to Server");

            while (true) {
                printCommands();
                System.out.print("Enter the number of the command you would like to run: ");
                String command = consoleInput.readLine();
                int commandInt = Integer.parseInt(command);
                out.println(commandInt);

                if (commandInt == 0) {
                    break;  // Exit the loop if the command is 0 (Exit)
                }

                switch (commandInt) {
                    case 2:
                        findUserByStudentId(out, in, consoleInput);
                        break;
                    case 3:
                        deleteUserByStudentId(out, in, consoleInput);
                        break;
                    case 4:
                        insertNewUser(out, in, consoleInput);
                        break;
                    case 5:
                        updateUserByStudentId(out, in, consoleInput);
                        break;
                    case 7:
                        convertUserToJson(out, in, consoleInput);
                        break;
                    case 9:
                        displayEntityById(out, in, consoleInput);
                        break;
                    case 10:
                        displayAllEntities(in);
                        break;
                    case 8:
                    case 6:
                    case 1:
                        handleStandardCommand(in);
                        break;
                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
        System.out.println("Client Closed");
    }

    private void printCommands() {
        System.out.println("\n---- Commands ----");
        COMMANDS.forEach((key, value) -> System.out.println(key + ". " + value));
    }

    private void handleStandardCommand(BufferedReader in) throws IOException {
        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);
            if (response.isEmpty()) {
                break;  // Exit the loop when an empty line is received
            }
        }
    }

    private void findUserByStudentId(PrintWriter out, BufferedReader in, BufferedReader consoleInput) throws IOException {
        try {
            out.println("2"); // Send command for Find User By Student ID
            System.out.print("Enter the student ID of the user to find: ");
            String studentId = consoleInput.readLine();
            out.println(studentId);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.isEmpty()) {
                    break;  // Exit the loop when an empty line is received
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    private void deleteUserByStudentId(PrintWriter out, BufferedReader in, BufferedReader consoleInput) throws IOException {
        try {
            out.println("3"); // Send command for Delete User By Student ID
            System.out.print("Enter the student ID of the user to delete: ");
            String studentId = consoleInput.readLine();
            out.println(studentId);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.isEmpty()) {
                    break;  // Exit the loop when an empty line is received
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    private void insertNewUser(PrintWriter out, BufferedReader in, BufferedReader consoleInput) throws IOException {
        try {
            out.println("4"); // Send command for Insert New User
            System.out.print("Enter the student ID of the user to insert: ");
            String studentId = consoleInput.readLine();
            out.println(studentId);

            System.out.print("Enter the first name of the user to insert: ");
            String firstName = consoleInput.readLine();
            out.println(firstName);

            System.out.print("Enter the last name of the user to insert: ");
            String lastName = consoleInput.readLine();
            out.println(lastName);

            System.out.println("Enter a course ID of the User to insert: ");
            String courseId = consoleInput.readLine();
            out.println(courseId);

            System.out.println("Enter a course name of the User to insert: ");
            String courseName = consoleInput.readLine();
            out.println(courseName);

            System.out.println("Enter a course grade of the User to insert: ");
            String courseGrade = consoleInput.readLine();
            out.println(courseGrade);

            System.out.println("Enter the semester of the User to insert: (Semester 1, Semester 2)");
            String semester = consoleInput.readLine();
            out.println(semester);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.isEmpty()) {
                    break;  // Exit the loop when an empty line is received
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    private void updateUserByStudentId(PrintWriter out, BufferedReader in, BufferedReader consoleInput) throws IOException {
        try {
            out.println("5"); // Send command for Update User By Student ID
            System.out.print("Enter the student ID of the user to update: ");
            String studentId = consoleInput.readLine();
            out.println(studentId);

            System.out.print("Enter the first name of the user to update: ");
            String firstName = consoleInput.readLine();
            out.println(firstName);

            System.out.print("Enter the last name of the user to update: ");
            String lastName = consoleInput.readLine();
            out.println(lastName);

            System.out.println("Enter a course ID of the User to update: ");
            String courseId = consoleInput.readLine();
            out.println(courseId);

            System.out.println("Enter a course name of the User to update: ");
            String courseName = consoleInput.readLine();
            out.println(courseName);

            System.out.println("Enter a course grade of the User to update: ");
            String courseGrade = consoleInput.readLine();
            out.println(courseGrade);

            System.out.println("Enter the semester of the User to update: (Semester 1, Semester 2)");
            String semester = consoleInput.readLine();
            out.println(semester);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.isEmpty()) {
                    break;  // Exit the loop when an empty line is received
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    private void convertUserToJson(PrintWriter out, BufferedReader in, BufferedReader consoleInput) throws IOException {
        try {
            out.println("7"); // Send command for Convert List of Users to JSON
            System.out.print("Enter the student ID of the user to convert to JSON: ");
            String studentId = consoleInput.readLine();
            out.println(studentId);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.isEmpty()) {
                    break;  // Exit the loop when an empty line is received
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    private void displayEntityById(PrintWriter out, BufferedReader in, BufferedReader consoleInput) throws IOException {
        try {
            out.println("9"); // Send command for Display Entity by id
            System.out.print("Enter the ID of the entity to display: ");
            String entityId = consoleInput.readLine();
            out.println(entityId);

            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.isEmpty()) {
                    break;  // Exit the loop when an empty line is received
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    private void displayAllEntities(BufferedReader in) throws IOException {
        String json = in.readLine();
        Gson gson = new Gson();
        List<User> users = gson.fromJson(json, new TypeToken<List<User>>(){}.getType());
        displayUsers(users);
    }

    private void displayUsers(List<User> users) {
        System.out.println("Users:");
        for (User user : users) {
            System.out.println(user); // Assuming User class has a toString() method
        }
    }
}
