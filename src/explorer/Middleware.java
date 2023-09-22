/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package explorer;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Ahmed
 */
public class Middleware {

    private final Backend BE = new Backend();
    private String oper = new String();
    private static String path = new String();
    private String source = new String();

    public static String getPath() {
        return path;
    }

    public void setPath(String pth) {
        String src;
        if (new File(pth).isDirectory()) {
            src = pth;
        } else {
            src = new File(pth).getParent();
        }
        path = src;
    }

    public void copyMW() {
        oper = "copy";
        source = path;
    }

    public void moveMW() {
        oper = "move";
        source = path;
    }

    public void pasteMW() {
        if (oper.equals("copy")) {
            BE.Copy(source, path);
        } else if (oper.equals("move")) {
            BE.Move(source, path);
        }
        Frontend.startAgain(new File(path).listFiles());
    }

    public void renameMW() {
        String ext = null;
        String oldName = new File(path).getName();
        if (oldName.lastIndexOf(".") != -1) {
            ext = oldName.substring(oldName.lastIndexOf("."));
            oldName = oldName.substring(0, oldName.lastIndexOf("."));
        }
        String newName = JOptionPane.showInputDialog("New Name:", oldName);
        if (newName != null) {
            if (ext != null) {
                BE.Rename(path, newName + ext);
            } else {
                BE.Rename(path, newName);
            }
        }
        setPath(path);
        Frontend.startAgain(new File(path).listFiles());
    }

    public void newMW() {
        setPath(path);
        String name = JOptionPane.showInputDialog("Name:");
        if (name != null) {
            BE.New(path, name);
        }
        Frontend.startAgain(new File(path).listFiles());
    }

    public void backMW() {
        setPath(path);
        path = new File(path).getParent();
        if (path != null) {
            Frontend.startAgain(new File(path).listFiles());
        } else {
            path = "This PC";
            Frontend.start(BE.getDrives());
        }
    }

    public void deleteMW() {
        BE.Delete(path);
        setPath(path);
        Frontend.startAgain(new File(path).listFiles());
    }

    public void searchMW(String url, String fileName) {
        ArrayList<File> foundFiles = new ArrayList<>();
        switch (url) {
            case "This PC" -> {
                for (File file : BE.getDrives()) {
                    foundFiles.addAll(BE.Search(file, fileName));
                }
            }
            case "Desktop" ->
                foundFiles.addAll(BE.Search(BE.getDesktop(), fileName));
            case "Music" ->
                foundFiles.addAll(BE.Search(BE.getMusic(), fileName));
            case "Documents" ->
                foundFiles.addAll(BE.Search(BE.getDocument(), fileName));
            case "Downloads" ->
                foundFiles.addAll(BE.Search(BE.getDownload(), fileName));
            case "Videos" ->
                foundFiles.addAll(BE.Search(BE.getVideo(), fileName));
            case "Pictures" ->
                foundFiles.addAll(BE.Search(BE.getPicture(), fileName));
            default ->
                foundFiles.addAll(BE.Search(new File(url), fileName));
        }
        if (foundFiles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "File has not been found.");
        } else {
            File[] files = foundFiles.toArray(File[]::new);
            Frontend.start(files);
        }
    }

    public void pc() {
        path = "This PC";
        Frontend.start(BE.getDrives());
    }

    public void desktop() {
        path = BE.getDesktop().toString();
        Frontend.startAgain(new File(path).listFiles());
    }

    public void download() {
        path = BE.getDownload().toString();
        Frontend.startAgain(new File(path).listFiles());
    }

    public void document() {
        path = BE.getDocument().toString();
        Frontend.startAgain(new File(path).listFiles());
    }

    public void picture() {
        path = BE.getPicture().toString();
        Frontend.startAgain(new File(path).listFiles());
    }

    public void music() {
        path = BE.getMusic().toString();
        Frontend.startAgain(new File(path).listFiles());
    }

    public void video() {
        path = BE.getVideo().toString();
        Frontend.startAgain(new File(path).listFiles());
    }

    public static JButton makeButton(File file, boolean isSearch) {
        String text;
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
        if (!"".equals(file.getName()) && isSearch) {
            text = file.getName();
        } else {
            text = file.getPath();
        }
        JButton btn = new JButton(icon);
        btn.setText(text);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        btn.setSize(40, 40);
        btn.setForeground(Color.black);
        btn.setBackground(Color.white);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    path = file.getAbsolutePath();
                    System.out.println("Path:" + path);
                } else if (e.getClickCount() == 2) {
                    if (!(file.isDirectory())) {
                        try {
                            Desktop.getDesktop().open(file);
                        } catch (IOException ex) {
                            Logger.getLogger(Frontend.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        Frontend.startAgain(file.listFiles());
                    }
                }
            }
        });
        return btn;
    }
}
