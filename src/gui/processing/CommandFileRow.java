package gui.processing;

import javax.swing.JComponent;

/**
 * Abstract class representing a file row of inputs for a CommandComponent
 *
 * @author oi12mlw, oi12pjn
 * @see CommandComponent
 */
@SuppressWarnings("serial")
public abstract class CommandFileRow extends JComponent {

    public static final String WIDE = "w 80:120:160";
    public static final String NARROW = "w 60:80:100";
    public static final String MEDIUM = "w 80:100:120";
    protected abstract void addInputFields();
    protected abstract void addLabels();

}
