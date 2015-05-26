package gui.processing;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class CommandScrollPane extends JScrollPane {


    private static final int SCROLL_SPEED = 16;
    private JPanel componentPanel;

    public CommandScrollPane() {
        super(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);

        componentPanel = new JPanel();
        componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.PAGE_AXIS));
        this.getViewport().add(componentPanel);
        this.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);

        setUpAutoScrollBehaviour();

    }

    private void setUpAutoScrollBehaviour() {
        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            int prevMax = 0;
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(e.getAdjustable().getMaximum() != prevMax) {
                    prevMax = e.getAdjustable().getMaximum();
                    e.getAdjustable().setValue(prevMax);
                }
            }
        });
    }

    public void addCommandComponent(CommandComponent commandComponent) {
        componentPanel.add((Component) commandComponent);
        Rectangle bounds = new Rectangle(0, componentPanel.getHeight() - 1 , 1, 1);
        this.scrollRectToVisible(bounds);
        this.revalidate();
    }

    public static void main(String[] args) {

        try {
            UIManager
                    .setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

        JFrame frame = new JFrame();
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
        frame.add(c);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
