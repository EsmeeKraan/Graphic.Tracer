import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

class SwingListTransferHandler extends TransferHandler {
    @Override
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent source) {
        JList<String> Webservers = (JList<String>) source;
        String data = Webservers.getSelectedValue();
        Transferable t = new StringSelection(data);
        return t;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        @SuppressWarnings("unchecked")
        JList<String> JLWebservers = (JList<String>) source;
        String movedItem = JLWebservers.getSelectedValue();
        if (action == TransferHandler.MOVE) {
            DefaultListModel<String> listModel = (DefaultListModel<String>) JLWebservers
                    .getModel();
            listModel.removeElement(movedItem);
        }
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!this.canImport(support)) {
            return false;
        }
        Transferable t = support.getTransferable();
        String data = null;
        try {
            data = (String) t.getTransferData(DataFlavor.stringFlavor);
            if (data == null) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        JList.DropLocation dropLocation = (JList.DropLocation) support
                .getDropLocation();
        int dropIndex = dropLocation.getIndex();
        JList<String> targetList = (JList<String>) support.getComponent();
        DefaultListModel<String> listModel = (DefaultListModel<String>) targetList
                .getModel();
        if (dropLocation.isInsert()) {
            listModel.add(dropIndex, data);
        } else {
            listModel.set(dropIndex, data);
        }
        return true;
    }
}

