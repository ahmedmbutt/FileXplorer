package testing;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import explorer.Backend;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Ahmed
 */
public class SystemTesting {

    public SystemTesting() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test.
    //
    @Test
    public void testFileExplorer() {
        // Create a new file explorer instance
        Backend fileExplorer = new Backend();

        // Test the functionality of creating a new directory
        String location = "D:\\";
        String newDirectoryName = "TestDirectory";
        fileExplorer.New(location, newDirectoryName);
        Path newDirectoryPath = Paths.get(location, newDirectoryName);
        assertTrue(Files.exists(newDirectoryPath));

        // Test the functionality of creating a new file
        String newFileName = "TestFile.txt";
        fileExplorer.New(location, newFileName);
        Path newFilePath = Paths.get(location, newFileName);
        assertTrue(Files.exists(newFilePath));

        // Test the functionality of copying a file
        String destinationDirectory = "D:\\Destination";
        fileExplorer.Copy(newFilePath.toString(), destinationDirectory);
        Path copiedFilePath = Paths.get(destinationDirectory, newFileName);
        assertTrue(Files.exists(copiedFilePath));

        // Test the functionality of copying a directory
        fileExplorer.Copy(newDirectoryPath.toString(), destinationDirectory);
        Path copiedDirectoryPath = Paths.get(destinationDirectory, newDirectoryName);
        assertTrue(Files.exists(copiedDirectoryPath));

        // Test the functionality of moving a file
        String newLocation = "D:\\NewLocation";
        fileExplorer.Move(copiedFilePath.toString(), newLocation);
        Path movedFilePath = Paths.get(newLocation, newFileName);
        assertTrue(Files.exists(movedFilePath));
        Path originalFilePath = Paths.get(destinationDirectory, newFileName);
        assertFalse(Files.exists(originalFilePath));

        // Test the functionality of moving a directory
        fileExplorer.Move(copiedDirectoryPath.toString(), newLocation);
        Path movedDirectoryPath = Paths.get(newLocation, newDirectoryName);
        assertTrue(Files.exists(movedDirectoryPath));
        Path originalDirectoryPath = Paths.get(destinationDirectory, newDirectoryName);
        assertFalse(Files.exists(originalDirectoryPath));

        // Test the functionality of renaming a file
        String newFileName2 = "NewTestFile.txt";
        fileExplorer.Rename(newLocation + "\\" + newFileName, newFileName2);
        Path renamedFilePath = Paths.get(newLocation, newFileName2);
        assertTrue(Files.exists(renamedFilePath));
        Path oldFilePath = Paths.get(newLocation, newFileName);
        assertFalse(Files.exists(oldFilePath));

        // Test the functionality of renaming a directory
        String newDirectoryName2 = "NewTestDirectory";
        fileExplorer.Rename(newLocation + "\\" + newDirectoryName, newDirectoryName2);
        Path renamedDirectoryPath = Paths.get(newLocation, newDirectoryName2);
        assertTrue(Files.exists(renamedDirectoryPath));
        Path oldDirectoryPath = Paths.get(newLocation, newDirectoryName);
        assertFalse(Files.exists(oldDirectoryPath));

        // Test the functionality of deleting a file
        fileExplorer.Delete(newLocation + "\\" + newFileName2);
        Path deletedFilePath = Paths.get(newLocation, newFileName2);
        assertFalse(Files.exists(deletedFilePath));

        // Test the functionality of deleting a directory
        fileExplorer.Delete(newLocation + "\\" + newDirectoryName2);
        Path deletedDirectoryPath = Paths.get(newLocation, newDirectoryName2);
        assertFalse(Files.exists(deletedDirectoryPath));
    }

    public static void main(String[] args) {
        new SystemTesting().testFileExplorer();
    }
}
