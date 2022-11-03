package Clases;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class limpiar {

    public void limpiarTabla(JTable tb, DefaultTableModel md) {
        while (tb.getRowCount() > 0) {
            md.removeRow(0);
        }
    }
}
