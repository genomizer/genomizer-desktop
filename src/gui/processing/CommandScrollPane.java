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
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import requests.RequestFactory;

@SuppressWarnings("serial")
public class CommandScrollPane extends JScrollPane {

    private static final int SCROLL_SPEED = 16;

    private JPanel componentPanel;
    private ArrayList<CommandComponent> commandList;

    public CommandScrollPane() {
        super(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
        commandList = new ArrayList<CommandComponent>();
        componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel,
                BoxLayout.PAGE_AXIS));
        this.getViewport().add(componentPanel);
        this.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);

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

    public void addCommandComponent(CommandComponent commandComponent) {
        if ( containsCommand(commandComponent) ) return;
        componentPanel.add((Component) commandComponent);
        commandList.add(commandComponent);

        // Scroll to visible
        Rectangle bounds = new Rectangle(0, ((Component) commandComponent).getHeight() - 1 , 1, 1);
        this.scrollRectToVisible(bounds);
        this.revalidate();
    }

    public void removeCommand(CommandComponent commandComponent) {

    }

    public ProcessCommand[] getCommandList() {

        ProcessCommand[] commands = new ProcessCommand[commandList.size()];

        Iterator<CommandComponent> componentIterator = commandList.iterator();
        for(int i = 0; componentIterator.hasNext(); i++) {
            System.out.println(i);
            CommandComponent currentComponent = componentIterator.next();
            commands[i] = buildProcessCommand(currentComponent);
        }
        return commands;

    }

    private ProcessCommand buildProcessCommand(CommandComponent component) {
        String name = component.getName();
        ProcessParameters[] parameters = component.getProcessParameters();
        ProcessCommand command = new ProcessCommand(name, parameters);
        return command;
    }

    public void empty() {
        Iterator<CommandComponent> componentIterator = commandList.iterator();
        while(componentIterator.hasNext()) {
            CommandComponent currentComponent = componentIterator.next();
            this.remove((Component)currentComponent);
            commandList.remove(currentComponent);
        }
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

    public void reset() {
        componentPanel.removeAll();
        componentPanel.revalidate();
        componentPanel.repaint();
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
        String commandName = "Glass e gott";
        String[] fileNames = { "bild.png", "stinkern.txt", "hus.jpg" };
        String[] genomeReleases = { "hg37", "hg38", "rat" };

        RawToProfileCommandComponent comp1 = new RawToProfileCommandComponent(
                commandName, fileNames, genomeReleases);
        RawToProfileCommandComponent comp2 = new RawToProfileCommandComponent(
                commandName, fileNames, genomeReleases);

        CommandScrollPane c = new CommandScrollPane();
        c.addCommandComponent(comp1);
        c.addCommandComponent(comp2);
        JButton knappen = new JButton("KÖR!!!");
        knappen.addActionListener(new ProcessButtonListener(c));
        frame.add(c, BorderLayout.CENTER);
        frame.add(knappen, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static class ProcessButtonListener implements ActionListener {


        private CommandScrollPane scrollPane;

        public ProcessButtonListener(CommandScrollPane scrollPane) {
            this.scrollPane = scrollPane;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ProcessCommand[] commandList = scrollPane.getCommandList();
            for(int i = 0; i < commandList.length; i++) {

                System.out.println(RequestFactory.makeProcessCommandRequest("0", commandList).toJson());

            }

        }

    }

}
