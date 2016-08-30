package com.msf.main;



import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.javatuples.Pair;

import com.msf.reader.XLSX_Reader;
import com.msf.reader.readingXML;

import regression.RMath;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;

public class Main extends JFrame {

    
	private JPanel contentPane;
    private JTextField filelocation;
    public static String get;
    public static File file;
    private JTextField textOut;
    public static String out;
    private JTable table;
    private JTable table_1;
	private String[] columnNames;
	private JComboBox columnBox;
	private JComboBox latBox;
	private JComboBox lonBox;
	public static JButton prepFile;
	private static String latColName;
	private static String lonColName;
	
	public static String getLatColName() {
		return latColName;
	}
	public static String getLonColName() {
		return lonColName;
	}
	
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
	 * @wbp.parser.entryPoint
	 */
        public Main() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1138, 591);
        
        columnNames = new String[0];
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{137, 159, 216, 194, 80, 148, 0};
        gbl_contentPane.rowHeights = new int[]{35, 35, 48, 32, 182, 0, 182, 0, 130, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        
                
                        filelocation = new JTextField();
                        GridBagConstraints gbc_filelocation = new GridBagConstraints();
                        gbc_filelocation.gridwidth = 2;
                        gbc_filelocation.fill = GridBagConstraints.HORIZONTAL;
                        gbc_filelocation.insets = new Insets(0, 0, 5, 5);
                        gbc_filelocation.gridx = 0;
                        gbc_filelocation.gridy = 0;
                        contentPane.add(filelocation, gbc_filelocation);
                        filelocation.setColumns(10);
                
                        
                        
                textOut = new JTextField();
                GridBagConstraints gbc_textOut = new GridBagConstraints();
                gbc_textOut.gridwidth = 2;
                gbc_textOut.fill = GridBagConstraints.HORIZONTAL;
                gbc_textOut.insets = new Insets(0, 0, 5, 5);
                gbc_textOut.gridx = 0;
                gbc_textOut.gridy = 1;
                contentPane.add(textOut, gbc_textOut);
                textOut.setColumns(10);
        
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
                GridBagConstraints gbc_btnOut = new GridBagConstraints();
                gbc_btnOut.anchor = GridBagConstraints.NORTHWEST;
                gbc_btnOut.insets = new Insets(0, 0, 5, 5);
                gbc_btnOut.gridx = 2;
                gbc_btnOut.gridy = 1;
                contentPane.add(btnOut, gbc_btnOut);
        
        JLabel lblLatitude = new JLabel("Latitude");
        GridBagConstraints gbc_lblLatitude = new GridBagConstraints();
        gbc_lblLatitude.anchor = GridBagConstraints.EAST;
        gbc_lblLatitude.insets = new Insets(0, 0, 5, 5);
        gbc_lblLatitude.gridx = 0;
        gbc_lblLatitude.gridy = 3;
        contentPane.add(lblLatitude, gbc_lblLatitude);
        
        latBox = new JComboBox();
        GridBagConstraints gbc_latBox = new GridBagConstraints();
        gbc_latBox.fill = GridBagConstraints.BOTH;
        gbc_latBox.insets = new Insets(0, 0, 5, 5);
        gbc_latBox.gridx = 1;
        gbc_latBox.gridy = 3;
        contentPane.add(latBox, gbc_latBox);
        
        JLabel lblLongitude = new JLabel("Longitude");
        GridBagConstraints gbc_lblLongitude = new GridBagConstraints();
        gbc_lblLongitude.anchor = GridBagConstraints.EAST;
        gbc_lblLongitude.insets = new Insets(0, 0, 5, 5);
        gbc_lblLongitude.gridx = 2;
        gbc_lblLongitude.gridy = 3;
        contentPane.add(lblLongitude, gbc_lblLongitude);
        
        lonBox = new JComboBox();
        GridBagConstraints gbc_lonBox = new GridBagConstraints();
        gbc_lonBox.insets = new Insets(0, 0, 5, 5);
        gbc_lonBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_lonBox.gridx = 3;
        gbc_lonBox.gridy = 3;
        contentPane.add(lonBox, gbc_lonBox);
        
        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridheight = 3;
        gbc_scrollPane.gridwidth = 4;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 4;
        contentPane.add(scrollPane, gbc_scrollPane);
        
        table_1 = new JTable();
        table_1.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null},
        	},
        	new String[] {
        		"Column", "Key", "Value"
        	}
        ));
        table_1.getModel().addTableModelListener(new ColumnTableListener());
        columnBox = new JComboBox(columnNames);
        scrollPane.setViewportView(table_1);
        
        JLabel lblNewLabel = new JLabel("New label");
        scrollPane.setColumnHeaderView(lblNewLabel);
        
        JButton btnAddRow = new JButton("Add Row");
        btnAddRow.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table_1.getModel();
				model.addRow(new String[3]);
			}
        	
        });
        GridBagConstraints gbc_btnAddRow = new GridBagConstraints();
        gbc_btnAddRow.insets = new Insets(0, 0, 5, 5);
        gbc_btnAddRow.gridx = 0;
        gbc_btnAddRow.gridy = 5;
        contentPane.add(btnAddRow, gbc_btnAddRow);
        
        JButton findFile = new JButton("Find File");
        findFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                jfc.addChoosableFileFilter(new FileNameExtensionFilter(".xml", "xml"));
                jfc.addChoosableFileFilter(new FileNameExtensionFilter(".xlsx","xlsx"));
                
                jfc.showDialog(null, "open");
                if (jfc.getSelectedFile() != null) {
                    latBox.setVisible(false);
                    lonBox.setVisible(false);

                    file = jfc.getSelectedFile();
                    get = jfc.getSelectedFile().toString();
                    filelocation.setText(get);
                    columnNames= getColNames(get);
                    columnBox = new JComboBox<String>(columnNames);
                    table_1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(columnBox));

                    Pair<String[],String[]>colSorted = rankColNames(columnNames);
                    latBox.setModel(new DefaultComboBoxModel(colSorted.getValue1()));
                    lonBox.setModel(new DefaultComboBoxModel(colSorted.getValue0()));

                    latBox.setVisible(true);
                    lonBox.setVisible(true);
                    
                }
               
            }
        });
        
        GridBagConstraints gbc_findFile = new GridBagConstraints();
        gbc_findFile.anchor = GridBagConstraints.NORTHWEST;
        gbc_findFile.insets = new Insets(0, 0, 5, 5);
        gbc_findFile.gridx = 2;
        gbc_findFile.gridy = 0;
        contentPane.add(findFile, gbc_findFile);

        
        prepFile = new JButton("Prepare File");
        prepFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	latColName = latBox.getSelectedItem().toString();
            	lonColName = lonBox.getSelectedItem().toString();
            	
                if (get.substring(get.lastIndexOf("."), get.length()).equals(".xml")) {
                    readingXML.finish();
                    JOptionPane.showMessageDialog(new JFrame(), "Converted XML File", "Finished!",
                            JOptionPane.PLAIN_MESSAGE);
                } else if (get.substring(get.lastIndexOf("."), get.length()).equals(".xlsx")) {
                	Pair<ArrayList<String>, HashMap<String, HashMap<String, String>>> att = ColumnTableListener.getAttributes();
                	try {
                        XLSX_Reader.XLSX_converter(Main.get,att.getValue0(),att.getValue1());
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
        
       
        
        GridBagConstraints gbc_prepFile = new GridBagConstraints();
        gbc_prepFile.fill = GridBagConstraints.BOTH;
        gbc_prepFile.insets = new Insets(0, 0, 0, 5);
        gbc_prepFile.gridx = 2;
        gbc_prepFile.gridy = 8;
        contentPane.add(prepFile, gbc_prepFile);
        
    }
    public Pair<String[],String[]> rankColNames(String[] columnNames){
    	HashMap<Character,Integer> count = new HashMap<Character,Integer>();
		HashMap<Character,Integer> lat=new HashMap<Character,Integer>();
		HashMap<Character,Integer> lon=new HashMap<Character,Integer>();
		HashMap<Double,String> placesLat = new HashMap<Double, String>();
		HashMap<Double,String> placesLon = new HashMap<Double, String>();

		
		for(char c: "abcdefghijklmnopqrstuvwxyz".toCharArray()){
			count.put(c,0);
		}
		lat.putAll(count);
		lon.putAll(count);
		for(char c:"latitude".toCharArray()){
			int j = lat.get(c);
			j++;
			lat.put(c,j);
		}
		for(char c:"longitude".toCharArray()){
			int j = lat.get(c);
			j++;
			lon.put(c,j);
		}

    	for(String str:columnNames){
    		
    		for(char c:str.toLowerCase().toCharArray()){
    			if(Character.isAlphabetic(c)){
    			int j = count.get(c);
    			j++;
    			count.put(c,j);
    			}
    		}
			  placesLon.put(RMath.cossim(new ArrayList(Arrays.asList(lon.values().toArray(new Integer[0]))),new ArrayList(Arrays.asList(count.values().toArray(new Integer[0])))),str);	
			  placesLat.put(RMath.cossim(new ArrayList(Arrays.asList(lat.values().toArray(new Integer[0]))),new ArrayList(Arrays.asList(count.values().toArray(new Integer[0])))),str);	

    	}
    	Double[] latVals = placesLat.keySet().toArray(new Double[0] );
    	Arrays.sort(latVals,Collections.reverseOrder());
    	Double[] lonVals = placesLon.keySet().toArray(new Double[0] );
    	Arrays.sort(lonVals,Collections.reverseOrder());

    	int h =0;
    	String[] latSorted = new String[latVals.length];
    	for(Double d:latVals){
    		latSorted[h] = placesLat.get(d);
    		h++;
    	}
    	h =0;
    	String[] lonSorted = new String[lonVals.length];
    	for(Double d:lonVals){
    		lonSorted[h] = placesLon.get(d);
    	h++;
    	}
    	return(new Pair<String[],String[]>(lonSorted,latSorted));
    }
	public String[] getColNames(String arg) {
		try {
			FileInputStream ExcelFileToRead = new FileInputStream(arg);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			XSSFCell cell;
			ArrayList<String> col_names= new ArrayList<String>();
			for(int h=0; h <wb.getNumberOfSheets();h++){
				XSSFSheet sheet = wb.getSheetAt(h);
				XSSFRow fstRow = sheet.getRow(0);
				Iterator<Cell> cells1 = sheet.getRow(0).cellIterator();
				int j =0;
				while(cells1.hasNext()){
					cell = (XSSFCell) cells1.next();
						if(fstRow.getCell(j, XSSFRow.RETURN_BLANK_AS_NULL)!=null){
								col_names.add(fstRow.getCell(j).toString());	
						}
						j++;
					}
				}
			return col_names.toArray(new String[0]);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
