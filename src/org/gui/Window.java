/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;

import org.domain.*;
import javax.swing.*;
/**
 *
 * @author Marek
 */

public class Window extends JFrame {
    static Boolean drag = false;

    Window(Board newBoard, MyTable table, FigurePosition globalMove, JLabel label) {

        setSize(800, 730);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu chooseOpponent = new JMenu("Choose opponent");
        JMenu chooseColor = new JMenu("Choose color");
        menuBar.add(chooseOpponent);
        menuBar.add(chooseColor);

        JMenuItem startEasy = new JMenuItem("Easy genetic opponent");
        JMenuItem startHard = new JMenuItem("Hard genetic opponent");
        JMenuItem startConst = new JMenuItem("Opponent based on constant values");
        chooseOpponent.add(startEasy);
        chooseOpponent.add(startHard);
        chooseOpponent.addSeparator();
        chooseOpponent.add(startConst);

        JRadioButtonMenuItem chooseWhite = new JRadioButtonMenuItem("Choose white");
        JRadioButtonMenuItem chooseBlack = new JRadioButtonMenuItem("Choose black");

        ButtonGroup bg = new ButtonGroup();
        bg.add(chooseWhite);
        bg.add(chooseBlack);
        chooseColor.add(chooseWhite);
        chooseColor.add(chooseBlack);

        startHard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                menuBar.removeAll();
                menuBar.repaint();
                new Thread() {
                    public void run() {
                        playHard(newBoard, table, globalMove);
                    }
                }.start();
            }
        });
        startEasy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                menuBar.removeAll();
                menuBar.repaint();
                new Thread() {
                    public void run() {
                        Population population = new Population();
                        Evaluation values = new Evaluation();
                        population.setClassic(values);
                        Iterator<Figure> it = newBoard.getFiguresOnBoard().iterator();
                        Figure now;
                        while(it.hasNext()) {
                            now = it.next();
                            Integer x = now.getPosition().getX();
                            Integer y = now.getPosition().getY();
                            String imagePath = now.getImagePath();
                            table.setValueAt(imagePath, y, x);
                        }
                        table.addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mousePressed(java.awt.event.MouseEvent evt) {
                                Integer row = table.rowAtPoint(evt.getPoint());
                                Integer col = table.columnAtPoint(evt.getPoint());

                                if (!drag) {
                                    drag = newBoard.choseDragFigure(row, col, globalMove, values);
                                    label.setText(globalMove.getX().toString() + globalMove.getY().toString() + " " + globalMove.getLocalX().toString() + globalMove.getLocalY().toString());
                                } else if (drag) {
                                    Integer y = newBoard.getDragFigure().getPosition().getY();
                                    Integer x = newBoard.getDragFigure().getPosition().getX();
                                    Integer possY;
                                    Integer possX;
                                    String imagePath = newBoard.getDragFigure().getImagePath();
                                    FigurePosition nowMove;
                                    Iterator<FigurePosition> itMove = newBoard.getDragFigure().getPossibleMoves().iterator();
                                    Figure nowFigure;

                                    while (itMove.hasNext()) {
                                        nowMove = itMove.next();
                                        possY = nowMove.getY();
                                        possX = nowMove.getX();
                                        if (possY == row && possX == col) {
                                            newBoard.dropFigure(row, col);
                                            newBoard.setDraggedFigure(row, col);
                                            table.setValueAt(null, y, x);
                                            table.setValueAt(imagePath, row, col);
                                        }
                                    }

                                    Iterator<Figure> itFigure = newBoard.getFiguresOnBoard().iterator();
                                    while (itFigure.hasNext()) {
                                        nowFigure = itFigure.next();
                                        nowFigure.getPossibleMoves().clear();
                                    }
                                    drag = false;
                                }
                            }
                        });
                    }
                }.start();
            }
        });
    }

    public static void main(String[] args)
    {
        FigurePosition globalMove = new FigurePosition(0,0);
        Board newBoard = new Board();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JLabel label = new JLabel();
                MyTable table = new MyTable(newBoard.getWidth(), newBoard.getHeight(), label, newBoard, globalMove);
                final Window frame = new Window(newBoard, table, globalMove, label);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setViewportView(table);
                scrollPane.setRowHeaderView(new JLabel(new ImageIcon("src/org/icons/chess-3-xl.png")));
                JPanel panel = new JPanel();
                panel.add(label);
                frame.add(panel, BorderLayout.EAST);
                frame.add(scrollPane);
                frame.setVisible(true);
            }
        });
    }

    public static void playHard(Board newBoard, MyTable table, FigurePosition globalMove) {
        Population population = new Population();
        Evaluation value = new Evaluation();

        for(int i = 0; i < population.getLength(); i++)
            for(int j = i + 1; j < population.getLength(); j++)
                population.setValues(value, i, j);

        for(int i = 0; i < 2; i++)
            population.playPopulation(newBoard, value, table, globalMove);
    }
}
