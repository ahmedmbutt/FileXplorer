/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package explorer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JOptionPane;

/**
 *
 * @author Ahmed
 */
public class Backend {

    String userName = System.getProperty("user.name");
    String userDir = System.getenv("SystemDrive");

    public void Copy(String src, String des) {
        String dest = des + src.substring(src.lastIndexOf("\\"));
        if (Files.isDirectory(Paths.get(src))) {
            try {
                Files.walk(Paths.get(src))
                        .forEach(source -> {
                            try {
                                Path destination = Paths.get(dest, source.toString().substring(src.length()));
                                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, ex.getMessage());
                            }
                        });
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            try {
                Path source = Paths.get(src);
                Path destination = Paths.get(dest);
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void Move(String src, String dest) {
        dest = dest + src.substring(src.lastIndexOf("\\"));
        try {
            Path source = Paths.get(src);
            Path destination = Paths.get(dest);
            Files.move(source, destination);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void Rename(String src, String name) {
        String dest = src.substring(0, src.lastIndexOf("\\") + 1) + name;
        try {
            Path source = Paths.get(src);
            Path destination = Paths.get(dest);
            Files.move(source, destination);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void Delete(String src) {
        try {
            Path source = Paths.get(src);
            if (Files.isDirectory(source)) {
                Files.walk(source)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } else {
                Files.delete(source);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void New(String src, String name) {
        try {
            Path source = Paths.get(src, name);
            if (name.contains(".")) {
                Files.createFile(source);
            } else {
                Files.createDirectory(source);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public ArrayList<File> Search(File file, String name) {
        ArrayList<File> foundFiles = new ArrayList<File>();
        ArrayList<File> tempFiles = new ArrayList<File>();
        if (file.listFiles() != null) {
            for (File temp : file.listFiles()) {
                if (temp.isDirectory()) {
                    if (temp.getName().toLowerCase().contains(name.toLowerCase())) {
                        System.out.println("Found:" + file.getAbsolutePath() + "\\" + temp.getName());
                        foundFiles.add(temp);
                    }
                    tempFiles = Search(temp, name);
                    if (tempFiles != null) {
                        foundFiles.addAll(tempFiles);
                    }
                } else {
                    if (temp.getName().toLowerCase().contains(name.toLowerCase())) {
                        System.out.println("Found:" + file.getAbsolutePath() + "\\" + temp.getName());
                        foundFiles.add(temp);
                    }
                }
            }
        }
        return foundFiles;
    }

    public File[] getDrives() {
        return File.listRoots();
    }

    public File getDesktop() {
        return new File(userDir + "\\Users\\" + userName + "\\Desktop");
    }

    public File getDocument() {
        return new File(userDir + "\\Users\\" + userName + "\\Documents");
    }

    public File getDownload() {
        return new File(userDir + "\\Users\\" + userName + "\\Downloads");
    }

    public File getPicture() {
        return new File(userDir + "\\Users\\" + userName + "\\Pictures");
    }

    public File getVideo() {
        return new File(userDir + "\\Users\\" + userName + "\\Videos");
    }

    public File getMusic() {
        return new File(userDir + "\\Users\\" + userName + "\\Music");
    }
}
