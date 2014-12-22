/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui;
import java.awt.BorderLayout;
import org.domain.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.util.Iterator;
/**
 *
 * @author Marek
 */
public class Window extends JFrame {
    
    static Boolean drag = false;
    static Integer cnt = 0;
    public static void main(String[] args) {
        JFrame frame = new JFrame("Szachy");
        FigurePosition globalMove = new FigurePosition(0,0);
        frame.setSize(800, 700);
        Board newBoard = new Board();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTable table = new JTable(newBoard.getWidth(), newBoard.getHeight()) {
            @Override
            public boolean isCellEditable ( int row, int col) {
                return false;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table.setAutoResizeMode(0);
        cellSize(table, newBoard.getCellWidth(), newBoard.getCellHeight());
        table.setDefaultRenderer(Object.class, new YourTableCellRendererr());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);
        scrollPane.setRowHeaderView(new JLabel(new ImageIcon("src/org/icons/chess-3-xl.png")));
        JLabel label = new JLabel();
        JPanel panel = new JPanel();
        panel.add(label);
        frame.add(panel, BorderLayout.EAST);
        frame.add(scrollPane);
        frame.setVisible(true);
        Population population = new Population();
        Evaluation value = new Evaluation();

        for(int i = 0; i < population.getLength(); i++)
            for(int j = i + 1; j < population.getLength(); j++)
                population.setValues(value, i, j);

        for(int i = 0; i < 2; i++)
            population.playPopulation(newBoard, value, table, globalMove);
        System.out.print("ehh");

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Integer row = table.rowAtPoint(evt.getPoint());
                Integer col = table.columnAtPoint(evt.getPoint());                        
        
                if(!drag) {
                    drag = newBoard.choseDragFigure(row, col, globalMove);
                    label.setText(globalMove.getX().toString()+globalMove.getY().toString()+" "+globalMove.getLocalX().toString()+globalMove.getLocalY().toString());
                }

                else if(drag) {
                    Integer y = newBoard.getDragFigure().getPosition().getY();
                    Integer x = newBoard.getDragFigure().getPosition().getX();
                    Integer possY;
                    Integer possX;
                    String imagePath = newBoard.getDragFigure().getImagePath();
                    FigurePosition nowMove;
                    Iterator<FigurePosition> itMove = newBoard.getDragFigure().getPossibleMoves().iterator();
                    Figure nowFigure;

                    while(itMove.hasNext()) {
                        nowMove = itMove.next();
                        possY = nowMove.getY();
                        possX = nowMove.getX();
                        if(possY == row && possX == col) {
                            newBoard.dropFigure(row, col);
                            newBoard.setDraggedFigure(row, col);
                            table.setValueAt(null, y, x);
                            table.setValueAt(imagePath, row, col);
                        }
                    }
                    
                    Iterator<Figure> itFigure = newBoard.getFiguresOnBoard().iterator();
                    while(itFigure.hasNext()) {
                        nowFigure = itFigure.next();
                        nowFigure.getPossibleMoves().clear();
                    }
                    drag=false;
                }
            }
        });
    }
    
    
    private static void cellSize(JTable table, Integer cellWidth, Integer cellHeight) {
        table.setRowHeight(cellHeight);
        
        for(int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(cellWidth);
        }
    }
}

class YourTableCellRendererr extends DefaultTableCellRenderer {
  public Component getTableCellRendererComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus,
                                                 int row,
                                                 int column) {
              



                JLabel label = (JLabel)super.getTableCellRendererComponent(  
            table, value, isSelected, hasFocus, row, column  
        );

    Component c = 
      super.getTableCellRendererComponent(table, value,
                                          isSelected, hasFocus,
                                          row, column);
    label.setIcon(new ImageIcon((String)value)); 
    if ((row % 2 == 0 && column % 2 == 0) || (row % 2 == 1 && column % 2 == 1)) {
        
       c.setBackground(new java.awt.Color(231, 176, 83));
    }
    else {
       c.setBackground(new java.awt.Color(78, 35, 18));
    }
    return c;
  }
}
