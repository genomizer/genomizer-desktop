package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.OngoingDownloads;
import util.FileData;

public class DownloadWindow extends JFrame {
    
    private static final long serialVersionUID = -7647204230941649167L;
    private JPanel panel;
    private JTable table;
    private JButton downloadButton;
    private ImageIcon downloadIcon = new ImageIcon(
            "src/icons/DownloadButton.png");
    private ArrayList<FileData> files;
    private OngoingDownloads ongoingDownloads;
    
    /**
     * Initiates a new DownloadWindow with the files it receives.
     * 
     * @param files
     *            An ArrayList containing the FileData of the chosen files.
     */
    public DownloadWindow(ArrayList<FileData> files,
            OngoingDownloads ongoingDownloads) {
        this.ongoingDownloads = ongoingDownloads;
        this.files = files;
        // Gets the names of the files
        ArrayList<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < files.size(); i++) {
            fileNames.add(files.get(i).getName());
        }
        // Sets up the DownloadWindow using the filenames.
        setUp(fileNames);
    }
    
    /**
     * Private method that sets up the DownloadWindow.
     * 
     * @param data
     *            An ArrayList containing the Strings to set up the window with.
     */
    private void setUp(ArrayList<String> data) {
        
        panel = new JPanel(new BorderLayout(3, 3));
        add(panel);
        panel.add(new JLabel("test"), BorderLayout.NORTH);
        
        // Set up the JTable
        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        
        TableColumn fileNameColumn, formatConversionColumn;
        fileNameColumn = new TableColumn();
        formatConversionColumn = new TableColumn();
        
        tableModel.addColumn(fileNameColumn);
        tableModel.addColumn(formatConversionColumn);
        
        fileNameColumn = table.getTableHeader().getColumnModel().getColumn(0);
        formatConversionColumn = table.getTableHeader().getColumnModel()
                .getColumn(1);
        
        fileNameColumn.setHeaderValue("File name");
        formatConversionColumn.setHeaderValue("Format conversion");
        
        for (int i = 0; i < data.size(); i++) {
            tableModel.addRow(new Object[] { data.get(i),
                    "Click here to choose file format" });
        }
        
        // Add comboboxes to each row in the table.
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("RAW");
        comboBox.addItem("WIG");
        DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
        formatConversionColumn.setCellEditor(cellEditor);
        
        // table.setBackground(Color.cyan);
        table.setRowHeight(30);
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        
        downloadButton = new JButton("Download");
        
        JPanel flowSouth = new JPanel();
        flowSouth.add(downloadButton);
        panel.add(flowSouth, BorderLayout.SOUTH);
        
        setTitle("DOWNLOAD FILES");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Adds a listener for pressing the download button.
     * 
     * @param listener
     *            The listener to be added.
     */
    public void addDownloadFileListener(ActionListener listener) {
        downloadButton.addActionListener(listener);
    }
    
    /**
     * @return files An ArrayList containing the FileData representing the
     *         files.
     */
    public ArrayList<FileData> getFiles() {
        return files;
    }
}
