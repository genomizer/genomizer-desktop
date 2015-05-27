package gui.processing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import requests.RequestFactory;

/**
 * Scroll pane containing CommandComponents
 *
 * @author oi12mlw, oi12pjn
 * @see CommandComponent
 */
@SuppressWarnings("serial")
public class CommandScrollPane extends JScrollPane {

    private static final int SCROLL_SPEED = 16;

    private JPanel componentPanel;
    private ArrayList<CommandComponent> commandList;

    /**
     * Constructs a new instance of a CommandScrollPane
     */
    public CommandScrollPane() {

        super(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);

        commandList = new ArrayList<CommandComponent>();
        componentPanel = new JPanel();

        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.PAGE_AXIS));
        this.getViewport().add(componentPanel);
        this.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
        this.setBorder(BorderFactory.createTitledBorder("Commands"));

        setUpAutoScrollBehaviour();

    }

    private void setUpAutoScrollBehaviour() {
        this.getVerticalScrollBar().addAdjustmentListener(
                new AdjustmentListener() {
                    int prevMax = 0;

                    public void adjustmentValueChanged(AdjustmentEvent e) {
                        if (e.getAdjustable().getMaximum() != prevMax) {
                            prevMax = e.getAdjustable().getMaximum();
                            e.getAdjustable().setValue(prevMax);
                        }
                    }
                });
    }

    /**
     * Adds the CommandComponent to this scroll pane
     * @param commandComponent the CommandComponent
     */
    public void addCommandComponent(CommandComponent commandComponent) {
        if ( containsCommand(commandComponent) ) return;
        componentPanel.add( commandComponent);
        commandList.add(commandComponent);

        // Scroll to visible
        Rectangle bounds = new Rectangle(0,  commandComponent.getHeight() - 1 , 1, 1);
        this.scrollRectToVisible(bounds);
        this.revalidate();
        this.repaint();
    }

    // TODO: to be used?
    /**
     * Removes the given CommandComponent from this scroll pane
     * @param commandComponent the given CommandComponent
     */
    public void removeCommand(CommandComponent commandComponent) {
        commandList.remove(commandComponent);
        this.componentPanel.remove(commandComponent);
    }

    /**
     * Returns the list of ProcessCommands represented by the CommandComponents in this scroll pane
     * @return the list of ProcessCommands
     */
    public ProcessCommand[] getCommandList() {

        ProcessCommand[] commands = new ProcessCommand[commandList.size()];

        Iterator<CommandComponent> componentIterator = commandList.iterator();
        for(int i = 0; componentIterator.hasNext(); i++) {
            CommandComponent currentComponent = componentIterator.next();
            commands[i] = buildProcessCommand(currentComponent);
        }
        return commands;

    }

    private ProcessCommand buildProcessCommand(CommandComponent component) {
        String type = component.getCommandName();
        ProcessParameters[] parameters = component.getProcessParameters();
        ProcessCommand command = new ProcessCommand(type, parameters);
        return command;
    }

    /**
     * Removes all CommandComponents from this scroll pane
     */
    public void empty() {
        this.componentPanel.removeAll();
        commandList.clear();
        componentPanel.revalidate();
        componentPanel.repaint();
    }

    private boolean containsCommand(CommandComponent commandComponent) {

        Component[] components = componentPanel.getComponents();
        for (Component c : components) {
            if ( !CommandComponent.class.isAssignableFrom( c.getClass() )) continue;
            if (((CommandComponent) c).getCommandName().equals(
                    commandComponent.getCommandName())) return true;
        }
        return false;

    }

    public static void main(String[] args) {

        try {
            UIManager
                    .setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        String[] fileNames = { "bild.png", "stinkern.txt", "hus.jpg" };
        String[] genomeReleases = { "hg37", "hg38", "rat" };

        RawToProfileCommandComponent comp1 = new RawToProfileCommandComponent(fileNames, genomeReleases);
        SmoothingCommandComponent comp2 = new SmoothingCommandComponent(fileNames);
        RatioCommandComponent comp3 = new RatioCommandComponent(fileNames);


        CommandScrollPane c = new CommandScrollPane();
        c.addCommandComponent(comp1);
        c.addCommandComponent(comp2);
        c.addCommandComponent(comp3);
        JButton knappen = new JButton("K�R!!!");
        knappen.addActionListener(new ProcessButtonListener(c));
        frame.add(c, BorderLayout.CENTER);
        frame.add(knappen, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // TODO: refactor, move
    private static class ProcessButtonListener implements ActionListener {



        private CommandScrollPane scrollPane;

        public ProcessButtonListener(CommandScrollPane c) {
            this.scrollPane = c;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for ( ProcessCommand a : scrollPane.getCommandList() ) {
                System.out.println(a);
                for ( ProcessParameters f : a.files ){
                    System.out.println(f);
                }
            }

        }

    }

}
