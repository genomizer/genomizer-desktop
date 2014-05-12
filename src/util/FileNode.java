package util;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

/**
 * A custom node implementation (For TreeTable).
 */
public class FileNode extends AbstractMutableTreeTableNode {

	private FileData file;
	
	public FileNode(FileData file) {
		super(new Object[] {file.filename, file.date, file.author});
		this.file = file;
	}

	@Override
	public Object getValueAt(int columnIndex) {
		return getData()[columnIndex];
	}

	@Override
	public int getColumnCount() {
		return getData().length;
	}

	public Object[] getData() {
		return (Object[]) super.getUserObject();
	}
	
	public FileData getFile() {
		return file;
	}

}
