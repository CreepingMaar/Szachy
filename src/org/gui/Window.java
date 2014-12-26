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
import javax.swing.table.TableColumn;

/**
 *
 * @author Marek
 */

public class Window extends JFrame {
    static Boolean drag = false;
    static Boolean goodMoves = false;
    static Integer whoTurn = 1;
    static Integer notMultipleDrags = 0;

    Window(Board newBoard, MyTable table, FigurePosition globalMove) {

        setSize(800, 730);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Window tempWindow = this;

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu chooseOpponent = new JMenu("Choose opponent");
        JMenu chooseColor = new JMenu("Choose color");
        JMenu loadGame = new JMenu("Load Game");
        JMenu startGame = new JMenu("Start Game");
        menuBar.add(chooseOpponent);
        menuBar.add(chooseColor);
        menuBar.add(loadGame);
        menuBar.add(startGame);

        JRadioButtonMenuItem startEasy = new JRadioButtonMenuItem("Easy genetic opponent");
        JRadioButtonMenuItem startHard = new JRadioButtonMenuItem("Hard genetic opponent");
        JRadioButtonMenuItem startConst = new JRadioButtonMenuItem("Opponent based on constant values");
        JCheckBoxMenuItem showGoodMove = new JCheckBoxMenuItem("Show good moves");
        JMenuItem runGame = new JMenuItem("Run");
        ButtonGroup game = new ButtonGroup();
        game.add(startEasy);
        game.add(startHard);
        game.add(startConst);
        chooseOpponent.add(startEasy);
        chooseOpponent.add(startHard);
        chooseOpponent.addSeparator();
        chooseOpponent.add(startConst);
        startGame.add(showGoodMove);
        startGame.add(runGame);

        JRadioButtonMenuItem chooseWhite = new JRadioButtonMenuItem("Choose white");
        JRadioButtonMenuItem chooseBlack = new JRadioButtonMenuItem("Choose black");

        ButtonGroup bg = new ButtonGroup();
        bg.add(chooseWhite);
        bg.add(chooseBlack);
        chooseColor.add(chooseWhite);
        chooseColor.add(chooseBlack);

        runGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                menuBar.removeAll();
                menuBar.repaint();
                new Thread() {
                    public void run() {
                        if(showGoodMove.isSelected())
                            goodMoves = true;
                        if(startEasy.isSelected() && (chooseWhite.isSelected() || chooseBlack.isSelected())) {
                            Population population = new Population();
                            Evaluation value = new Evaluation();
                            if(chooseWhite.isSelected()) {
                                newBoard.changeColor("white");
                                whoTurn = 1;
                            }
                            else {
                                newBoard.changeColor("black");
                                whoTurn = -1;
                            }
                            for(int i = 0; i < population.getLength(); i++)
                                for(int j = i + 1; j < population.getLength(); j++)
                                    population.setValues(value, i, j);
                            population.playWeak(newBoard, value, table, globalMove, tempWindow);
                        }
                        else if(startHard.isSelected() && (chooseWhite.isSelected() || chooseBlack.isSelected())) {
                            if(chooseWhite.isSelected()) {
                                newBoard.changeColor("white");
                                whoTurn = 1;
                            }
                            else {
                                newBoard.changeColor("black");
                                whoTurn = -1;
                            }
                            playHard(newBoard, table, globalMove);
                        }
                        else if(startConst.isSelected() && (chooseWhite.isSelected() || chooseBlack.isSelected())) {
                            Population population = new Population();
                            Evaluation values = new Evaluation();
                            population.setClassic(values);
                            Iterator<Figure> it = newBoard.getFiguresOnBoard().iterator();
                            Figure now;
                            if(chooseWhite.isSelected()) {
                                newBoard.changeColor("white");
                                whoTurn = 1;
                            }
                            else {
                                newBoard.changeColor("black");
                                whoTurn = -1;
                            }
                            while(it.hasNext()) {
                                now = it.next();
                                Integer x = now.getPosition().getX();
                                Integer y = now.getPosition().getY();
                                String imagePath = now.getImagePath();
                                table.setValueAt(imagePath, y, x);
                            }
                            addMouse(table, globalMove, values, newBoard);
                        }
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
                MyTable table = new MyTable(newBoard.getWidth(), newBoard.getHeight(), newBoard, globalMove);
                final Window frame = new Window(newBoard, table, globalMove);
                JScrollPane scrollPane = new JScrollPane(table);

                scrollPane.setRowHeaderView(new JLabel(new ImageIcon("src/org/icons/rowsNumbers.png")));
                JPanel panel = new JPanel();
                frame.add(panel, BorderLayout.EAST);
                frame.add(scrollPane);
                frame.setVisible(true);
            }
        });
    }

    public static void playHard(Board newBoard, MyTable table, FigurePosition globalMove) {
        Population population = new Population();
        Evaluation value = new Evaluation();

        for (int i = 0; i < population.getLength(); i++)
            for (int j = i + 1; j < population.getLength(); j++)
                population.setValues(value, i, j);

        for (int i = 0; i < 2; i++)
            population.playPopulation(newBoard, value, table, globalMove);
    }

    public static void addMouse(MyTable table, FigurePosition globalMove, Evaluation values, Board newBoard) {
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Integer row = table.rowAtPoint(evt.getPoint());
                Integer col = table.columnAtPoint(evt.getPoint());
                FigurePosition repeatedMove = null;

                    if (!drag) {
                        if(whoTurn == -1 && notMultipleDrags == 0) {
                            newBoard.playTurnAi(globalMove, table, values, repeatedMove);
                        }
                        if(newBoard.checkCheck(newBoard, whoTurn, newBoard.whosKing(newBoard, whoTurn)))
                            JOptionPane.showMessageDialog(null, "Check!");
                        drag = newBoard.choseDragFigure(row, col, globalMove, values);
                        if (goodMoves)
                            table.setCells(globalMove.getY(), globalMove.getX());
                        notMultipleDrags++;
                    } else if (drag) {
                        Integer y = newBoard.getDragFigure().getPosition().getY();
                        Integer x = newBoard.getDragFigure().getPosition().getX();
                        Integer possY;
                        Integer possX;
                        String imagePath = newBoard.getDragFigure().getImagePath();
                        FigurePosition nowMove;
                        Iterator<FigurePosition> itMove = newBoard.getDragFigure().getPossibleMoves().iterator();
                        Figure nowFigure;
                        Boolean validPosition = false;

                        while (itMove.hasNext()) {
                            nowMove = itMove.next();
                            possY = nowMove.getY();
                            possX = nowMove.getX();
                            if (possY == row && possX == col) {
                                newBoard.dropFigure(row, col);
                                newBoard.setDraggedFigure(row, col);
                                table.setValueAt(null, y, x);
                                table.setValueAt(imagePath, row, col);
                                validPosition = true;
                            }
                        }

                        if(validPosition && newBoard.checkCheck(newBoard, whoTurn, newBoard.whosKing(newBoard, whoTurn)))
                            JOptionPane.showMessageDialog(null, "Check Mate!");

                        if (!validPosition)
                            JOptionPane.showMessageDialog(null, "Invalid move!");

                        Iterator<Figure> itFigure = newBoard.getFiguresOnBoard().iterator();
                        while (itFigure.hasNext()) {
                            nowFigure = itFigure.next();
                            nowFigure.getPossibleMoves().clear();
                        }
                        drag = false;

                        if(validPosition && whoTurn == -1)
                            notMultipleDrags = 0;

                        if(validPosition && whoTurn == 1)
                            newBoard.playTurnAi(globalMove, table, values, repeatedMove);

                    }


            }
        });
    }

}
