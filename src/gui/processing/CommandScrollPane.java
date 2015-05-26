package gui.processing;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class CommandScrollPane extends JScrollPane {

    public CommandScrollPane() {
        super(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void addCommandComponent(RawToProfileCommandComponent commandComponent) {
        this.getViewport().add(commandComponent);
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
        String[] fileNames = {"bild.png", "stinkern.txt", "hus.jpg"};
        String[] genomeReleases = {"hg37", "hg38", "rat"};

        RawToProfileCommandComponent comp1 = new RawToProfileCommandComponent(commandName, fileNames, genomeReleases);

        CommandScrollPane c = new CommandScrollPane();
        c.addCommandComponent(comp1);
        frame.add(c);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
