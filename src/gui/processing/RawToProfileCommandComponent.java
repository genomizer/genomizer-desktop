package gui.processing;

import gui.CustomButtonFactory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import util.IconFactory;

@SuppressWarnings("serial")
public class RawToProfileCommandComponent extends CommandComponent {

    private String[] fileNames;
    private String[] genomeReleases;

    public RawToProfileCommandComponent(String commandName, String[] fileNames,
            String[] genomeReleases) {

        super(commandName);

        this.fileNames = fileNames;
        this.genomeReleases = genomeReleases;
        addInitialFileRowPanel();

    }

    @Override
    public ProcessParameters[] getProcessParameters() {

        RawToProfileParameters[] parameters = new RawToProfileParameters[commandFileRowPanelStack.size()];
        Iterator<CommandFileRowPanel> fileRowIterator = commandFileRowPanelStack.iterator();
        for(int i = 0; fileRowIterator.hasNext(); i++) {

            RawToProfileFileRow currentFileRow = (RawToProfileFileRow) fileRowIterator.next().getFileRow();
            parameters[i] = buildProcessParameters(currentFileRow);
        }

        return parameters;
    }

    private RawToProfileParameters buildProcessParameters(RawToProfileFileRow fileRow) {
        String infile = fileRow.getInFile();
        String outfile = fileRow.getOutFile();
        String flags = fileRow.getFlags();
        String genomeRelease = fileRow.getGenomeRelease();
        boolean saveSam = false;
        return new RawToProfileParameters(infile, outfile, flags, genomeRelease, saveSam);
    }

    @Override
    protected CommandFileRow buildCommandFileRow() {

        return new RawToProfileFileRow(fileNames, genomeReleases);

    }

}
