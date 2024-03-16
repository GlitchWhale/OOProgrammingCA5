package org.example;

import org.example.App;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    // Establish a connection to the database
    String url = "jdbc:mysql://localhost/";
    String dbName = "petstore";
    String userName = "root";   // default
    String password = "";
    @Test
    public void testGetAllPets() {

        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
            System.out.println("Connected to the database");

            // Create an instance of org.example.App
            App app = new App();

            // Call getAllPets method
            app.getAllPets(conn);
        } catch (SQLException e) {
            fail("Failed to connect to the database: " + e.getMessage());
        }
    }

    @Test
    public void testGetPetById() {
        // Establish a connection to the database
        try (Connection conn = DriverManager.getConnection(url + dbName, userName, password)) {
            // Create an instance of App
            App app = new App();

            // Test case 1: Valid pet ID
            int validPetId = 1;
            Pet foundPet = app.getPetById(conn, validPetId);
            assertNotNull(foundPet);
            assertEquals(validPetId, foundPet.getPetId());

            // Test case 2: Invalid pet ID
            int invalidPetId = 100; // Assuming this pet ID doesn't exist
            Pet notFoundPet = app.getPetById(conn, invalidPetId);
            assertNull(notFoundPet);
        } catch (SQLException e) {
            fail("Failed to connect to the database: " + e.getMessage());
        }
    }
}