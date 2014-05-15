package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.OngoingDownloads;
import util.FileData;

import communication.DownloadHandler;

public class DownloadWindow extends JFrame {
    
    private static final long serialVersionUID = -7647204230941649167L;
    private JPanel mainPanel;
    private JPanel tablePanel;
    private JPanel ongoingPanel;
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
        this.setLayout(new BorderLayout());
        this.files = files;
        // Gets the names of the files
        ArrayList<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < files.size(); i++) {
            fileNames.add(files.get(i).getName());
        }
        // Sets up the DownloadWindow using the filenames.
        mainPanel = new JPanel(new BorderLayout());
        setUpTablePanel(fileNames);
        setUpOngoingPanel();
        add(mainPanel, BorderLayout.CENTER);
        updateProgress();
    }
    
    /**
     * Private method that sets up the DownloadWindow.
     * 
     * @param data
     *            An ArrayList containing the Strings to set up the window with.
     */
    private void setUpTablePanel(ArrayList<String> data) {
        
        tablePanel = new JPanel(new BorderLayout(3, 3));
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        tablePanel.add(new JLabel("test"), BorderLayout.SOUTH);
        
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
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        
        downloadButton = new JButton("Download");
        
        JPanel flowSouth = new JPanel();
        flowSouth.add(downloadButton);
        tablePanel.add(flowSouth, BorderLayout.SOUTH);
        
        setTitle("DOWNLOAD FILES");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void setUpOngoingPanel() {
        ongoingPanel = new JPanel(new GridLayout(0, 1));
        
        mainPanel.add(ongoingPanel, BorderLayout.NORTH);
    }
    
    private void updateProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ongoingPanel.removeAll();
                    ArrayList<DownloadHandler> handlers = ongoingDownloads
                            .getOngoingDownloads();
                    if (handlers != null) {
                        for (DownloadHandler handler : handlers) {
                            if (handler.getCurrentProgress() != handler
                                    .getTotalSize()) {
                                ongoingPanel.add(new JLabel(handler.getFileID()
                                        + " (" + handler.getCurrentSpeed()
                                        + "MiB/s)"));
                                JProgressBar progress = new JProgressBar(0,
                                        handler.getTotalSize());
                                progress.setValue(handler.getCurrentProgress());
                                progress.setStringPainted(true);
                                ongoingPanel.add(progress);
                            }
                        }
                        
                    }
                    revalidate();
                    repaint();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
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
