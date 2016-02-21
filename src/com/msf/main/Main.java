package com.msf.main;

import com.msf.reader.XLSX_Reader;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import com.msf.writer.osmWriter;
import com.msf.reader.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Main extends JFrame {

    private JPanel contentPane;
    private JTextField filelocation;
    public static String get;
    public static File file;
    private JTextField textOut;
    public static String out;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 511, 397);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton prepFile = new JButton("Prepare File");
        prepFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (get.substring(get.lastIndexOf("."), get.length()).equals(".xml")) {
                    readingXML.finish();
                    JOptionPane.showMessageDialog(new JFrame(), "Converted XML File", "Finished!",
                            JOptionPane.PLAIN_MESSAGE);
                } else if (get.substring(get.lastIndexOf("."), get.length()).equals(".xlsx")) {
                    try {
                        XLSX_Reader.XLSX_converter(Main.get);
                        JOptionPane.showMessageDialog(new JFrame(), "Converted Excel File", "Finished!",
                                JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Choose Valid File Type(.xlsx, .xml or .xls)", "We Encountered a Problem",
                            JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        prepFile.setBounds(152, 215, 166, 90);
        contentPane.add(prepFile);

        JButton findFile = new JButton("Find File");
        findFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.addChoosableFileFilter(new FileNameExtensionFilter(".xml", "xml"));
                jfc.showDialog(null, "open");
                if (jfc.getSelectedFile() != null) {
                    file = jfc.getSelectedFile();
                    get = jfc.getSelectedFile().toString();
                    filelocation.setText(get);
                }
            }
        });
        findFile.setBounds(279, 67, 185, 35);
        contentPane.add(findFile);

        filelocation = new JTextField();
        filelocation.setBounds(21, 68, 237, 32);
        contentPane.add(filelocation);
        filelocation.setColumns(10);

        JButton btnOut = new JButton("Output Folder");
        btnOut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser jc = new JFileChooser();
                jc.addChoosableFileFilter(new FileNameExtensionFilter(".xml", "xml"));
                jc.showDialog(null, "open");
                if (jc.getSelectedFile() != null) {
                    out = jc.getSelectedFile().toString();
                }
                textOut.setText(out);
            }
        });
        btnOut.setHorizontalAlignment(SwingConstants.RIGHT);
        btnOut.setBounds(279, 136, 185, 35);
        contentPane.add(btnOut);

        textOut = new JTextField();
        textOut.setBounds(21, 137, 237, 32);
        contentPane.add(textOut);
        textOut.setColumns(10);
    }
}
