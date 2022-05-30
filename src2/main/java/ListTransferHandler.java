
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ListTransferHandler extends TransferHandler {
    public ListTransferHandler() {
    }

    public int getSourceActions(JComponent c) {
        return 3;
    }

    protected Transferable createTransferable(JComponent source) {
        JList<String> Webservers = (JList)source;
        String data = (String)Webservers.getSelectedValue();
        Transferable t = new StringSelection(data);
        return t;
    }

    protected void exportDone(JComponent source, Transferable data, int action) {
        JList<String> Webservers = (JList)source;
        String movedItem = (String)Webservers.getSelectedValue();
        if (action == 2) {
            DefaultListModel<String> listModel = (DefaultListModel)Webservers.getModel();
            listModel.removeElement(movedItem);
        }

    }

    public boolean canImport(TransferSupport support) {
        return !support.isDrop() ? false : support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    public boolean importData(TransferSupport support) {
        if (!this.canImport(support)) {
            return false;
        } else {
            Transferable t = support.getTransferable();
            String data = null;

            try {
                data = (String) t.getTransferData(DataFlavor.stringFlavor);
                if (data == null) {
                    return false;
                }
            } catch (Exception var8) {
                var8.printStackTrace();
                return false;
            }

//            DropLocation dropLocation = (DropLocation)support.getDropLocation();
//            int dropIndex = dropLocation.getIndex();
//            JList<String> targetList = (JList)support.getComponent();
//            DefaultListModel<String> listModel = (DefaultListModel)targetList.getModel();
//            if (dropLocation.isInsert()) {
//                listModel.add(dropIndex, data);
//            } else {
//                listModel.set(dropIndex, data);
//            }
//
            return true;
//        }
        }
    }
}
