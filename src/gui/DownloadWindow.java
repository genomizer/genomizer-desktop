package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    private boolean running;
    
    /**
     * Initiates a new DownloadWindow with the files it receives.
     * 
     * @param files
     *            An ArrayList containing the FileData of the chosen files.
     */
    public DownloadWindow(ArrayList<FileData> files,
            CopyOnWriteArrayList<DownloadHandler> ongoingDownloads) {
        this.ongoingDownloads = ongoingDownloads;
        this.setLayout(new BorderLayout());
        this.files = files;
        // Gets the names of the files
        ArrayList<String> fileNames = new ArrayList<String>();
        for (int i = 0; i < files.size(); i++) {
            fileNames.add(files.get(i).getName());
        }
        // Sets up the DownloadWindow using the filenames.
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                running = false;
            }
        });
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
        String[] headings = new String[] { "File Name", "Format Conversion" };
        String[][] content = new String[data.size()][2];
        for (int i = 0; i < data.size(); i++) {
            content[i][0] = data.get(i);
            content[i][1] = "Click here to choose file format";
        }
        table = new JTable(content, headings) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1 ? true : false;
            }
        };
        
        // Add comboboxes to each row in the table.
        JComboBox comboBox = new JComboBox(new String[] {"RAW", "WIG"});
        DefaultCellEditor cellEditor = new DefaultCellEditor(comboBox);
        table.getColumnModel().getColumn(1).setCellEditor(cellEditor);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        
        downloadButton = new JButton("Download");
        
        JPanel flowSouth = new JPanel();
        flowSouth.add(downloadButton);
        tablePanel.add(flowSouth, BorderLayout.SOUTH);
        
        setTitle("Download Files");
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
                running = true;
                while (running) {
                    ongoingPanel.removeAll();
                    if (ongoingDownloads != null) {
                        for (final DownloadHandler handler : ongoingDownloads) {
                            if (!handler.isFinished()
                                    && handler.getTotalSize() > 0) {
                                JPanel south = new JPanel(new BorderLayout());
                                JPanel north = new JPanel(new BorderLayout());
                                double speed = handler.getCurrentSpeed() / 1024 / 2014;
                                north.add(new JLabel(
                                        handler.getFileName()
                                                + " ("
                                                + Math.round(speed*100.0)/100.0
                                                + "Mb/s)"), BorderLayout.CENTER);
                                JProgressBar progress = new JProgressBar(0,
                                        handler.getTotalSize());
                                progress.setValue(handler.getCurrentProgress());
                                progress.setStringPainted(true);
                                south.add(progress, BorderLayout.CENTER);
                                JButton stopButton = new JButton("X");
                                stopButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        handler.setFinished(true);
                                        ongoingDownloads.remove(handler);
                                    }
                                });
                                south.add(stopButton, BorderLayout.EAST);
                                ongoingPanel.add(north);
                                ongoingPanel.add(south);
                            } else {
                                ongoingDownloads.remove(handler);
                            }
                        }
                        
                    }
                    revalidate();
                    repaint();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        running = false;
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
