package org.gui;

import org.domain.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.Iterator;

/**
 * Created by Marek on 2014-12-22.
 */
public class GraphicalTable extends JTable {

    static int cellRow, cellCol;

    GraphicalTable(Integer width, Integer height, Board newBoard, FigurePosition globalMove) {
        DefaultTableModel myModel = new DefaultTableModel(width, height);
        setModel(myModel);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
        setRowSelectionAllowed(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setAutoResizeMode(0);
        cellSize(newBoard.getCellWidth(), newBoard.getCellHeight());
        setDefaultRenderer(Object.class, new TableCellRenderer());
    }

    public void setCells(int row, int col) {
        cellRow = row;
        cellCol = col;
        this.getColumnModel().getColumn(col).setCellRenderer(new CustomRenderer());
    }

    @Override
    public boolean isCellEditable ( int row, int col) {
        return false;
    }

    private void cellSize(Integer cellWidth, Integer cellHeight) {
        setRowHeight(cellHeight);

        for(int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
        }
    }

    class CustomRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            label.setIcon(new ImageIcon((String)value));
            if(cellRow == row && cellCol == column)
                setBackground(Color.green);
            else {
                if ((row % 2 == 0 && column % 2 == 0) || (row % 2 == 1 && column % 2 == 1))
                    c.setBackground(new java.awt.Color(231, 176, 83));
                else
                    c.setBackground(new java.awt.Color(78, 35, 18));
            }
            return c;
        }
    }
}

class TableCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setIcon(new ImageIcon((String)value));

        if ((row % 2 == 0 && column % 2 == 0) || (row % 2 == 1 && column % 2 == 1))
            c.setBackground(new java.awt.Color(231, 176, 83));
        else
            c.setBackground(new java.awt.Color(78, 35, 18));

        return c;
    }
}


