/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.Startup to edit this template
 */
package explorer;

/**
 *
 * @author muham
 */
import java.io.File;

public class Startup {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Frontend().setVisible(true);
            Frontend.start(File.listRoots());
        });
    }
}
