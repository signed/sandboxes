package jxtreetable.own;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

class RootNode extends AbstractMutableTreeTableNode {



    @Override
    public Object getValueAt(int column) {
        switch (column){
            case 0:
                return "Root Node";
            default:
                return null;
        }
    }

    @Override
    public int getColumnCount() {
        return 5;
    }
}
