/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gui;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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

        setSize(680, 711);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Window tempWindow = this;
        Evaluation value = new Evaluation();
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu chooseOpponent = new JMenu("Choose opponent");
        JMenu chooseColor = new JMenu("Choose color");
        JMenu loadGame = new JMenu("Load Game");
        JMenu startGame = new JMenu("Start Game");
        JMenu rewind = new JMenu("Undo elements");
        JMenu saveGame = new JMenu("Save game");
        menuBar.add(chooseOpponent);
        menuBar.add(chooseColor);
        menuBar.add(loadGame);
        menuBar.add(startGame);

        JRadioButtonMenuItem startEasy = new JRadioButtonMenuItem("Easy genetic opponent");
        JRadioButtonMenuItem startHard = new JRadioButtonMenuItem("Hard genetic opponent");
        JRadioButtonMenuItem startConst = new JRadioButtonMenuItem("Opponent based on constant values");
        JCheckBoxMenuItem showGoodMove = new JCheckBoxMenuItem("Show good moves");
        JMenuItem runGame = new JMenuItem("Run");
        JMenuItem move2 = new JMenuItem("2 moves");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        rewind.add(move2);
        saveGame.add(save);
        loadGame.add(load);
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
                Population population = new Population();

                new Thread() {
                    public void run() {
                        if(showGoodMove.isSelected())
                            goodMoves = true;
                        if(startEasy.isSelected() && (chooseWhite.isSelected() || chooseBlack.isSelected())) {
                            menuBar.removeAll();
                            menuBar.updateUI();
                            if(chooseWhite.isSelected()) {
                                newBoard.changeColor("white");
                                whoTurn = 1;
                            }
                            else {
                                newBoard.changeColor("black");
                                whoTurn = -1;
                            }
                            population.playWeak(newBoard, value, table, globalMove, tempWindow);
                        }
                        else if(startHard.isSelected() && (chooseWhite.isSelected() || chooseBlack.isSelected())) {
                            menuBar.removeAll();
                            menuBar.updateUI();
                            if(chooseWhite.isSelected()) {
                                newBoard.changeColor("white");
                                whoTurn = 1;
                            }
                            else {
                                newBoard.changeColor("black");
                                whoTurn = -1;
                            }
                            playHard(newBoard, table, globalMove, population, value);
                        }
                        else if(startConst.isSelected() && (chooseWhite.isSelected() || chooseBlack.isSelected())) {
                            menuBar.removeAll();
                            menuBar.updateUI();
                            population.setClassic(value);
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
                            menuBar.add(rewind);
                            menuBar.add(saveGame);
                            menuBar.updateUI();
                            addMouse(table, globalMove, value, newBoard);
                        }
                    }
                }.start();
            }
        });

        move2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                new Thread() {
                    public void run() {
                        if(newBoard.getMovesDone().size() > 1) {
                            Figure nowFigure;
                            for(int i = 0; i < 2; i++) {
                                FigurePosition move;
                                move = newBoard.getMovesDone().get(newBoard.getMovesDone().size() - 1);

                                Iterator<Figure> itFigure = newBoard.getFiguresOnBoard().iterator();
                                String imagePath = "";
                                while(itFigure.hasNext()) {
                                    nowFigure = itFigure.next();
                                    if(nowFigure.getPosition().getX() == move.getX() && nowFigure.getPosition().getY() == move.getY()) {
                                        imagePath = nowFigure.getImagePath();
                                        nowFigure.getPosition().setX(move.getLocalX());
                                        nowFigure.getPosition().setY(move.getLocalY());
                                        nowFigure.getPosition().setLocalX(move.getLocalX());
                                        nowFigure.getPosition().setLocalY(move.getLocalY());
                                    }
                                }

                                if(move.getBeatenPiece() != null) {
                                    newBoard.getFiguresOnBoard().add(move.getBeatenPiece());
                                    newBoard.getFiguresOffBoard().remove(move.getBeatenPiece());
                                    table.setValueAt(move.getBeatenPiece().getImagePath(), move.getBeatenPiece().getPosition().getY(), move.getBeatenPiece().getPosition().getX());
                                }

                                table.setValueAt(null, move.getY(), move.getX());
                                table.setValueAt(imagePath, move.getLocalY(), move.getLocalX());
                                newBoard.getMovesDone().remove(move);
                            }
                        }
                    }
                }.start();
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                new Thread() {
                    public void run() {
                        String fileName = "save.txt";

                        try {
                            FileWriter fileWriter = new FileWriter(fileName);

                            BufferedWriter writeBuffer = new BufferedWriter(fileWriter);

                            writeBuffer.write(whoTurn.toString());
                            writeBuffer.newLine();
                            writeBuffer.write(String.valueOf(newBoard.getFiguresOnBoard().size()));
                            writeBuffer.newLine();
                            Iterator<Figure> it = newBoard.getFiguresOnBoard().iterator();
                            while(it.hasNext()) {
                                Figure now = it.next();
                                writeBuffer.write(now.getColor());
                                writeBuffer.newLine();
                                writeBuffer.write(now.getChessPiece());
                                writeBuffer.newLine();
                                writeBuffer.write(now.getPosition().getX().toString());
                                writeBuffer.newLine();
                                writeBuffer.write(now.getPosition().getY().toString());
                                writeBuffer.newLine();
                            }
                            for(int i = 0; i <= 7; i++) {
                                for(int j = 0; j <= 7; j++) {
                                    writeBuffer.write(value.getBlackPawnValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getBlackKnightValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getBlackBishopValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getBlackQueenValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getBlackKingValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getBlackRookValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getWhitePawnValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getWhiteKnightValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getWhiteBishopValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getWhiteQueenValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getWhiteKingValue()[i][j].toString());
                                    writeBuffer.newLine();
                                    writeBuffer.write(value.getWhiteRookValue()[i][j].toString());
                                    writeBuffer.newLine();
                                }
                            }

                            writeBuffer.close();
                        }
                        catch(IOException ex) {
                            System.out.println("Error writing to file '" + fileName + "'");
                        }
                    }
                }.start();
            }
        });

        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                menuBar.removeAll();
                menuBar.updateUI();
                new Thread() {
                    public void run() {
                        try {
                            FileReader fileReader = new FileReader("save.txt");

                            BufferedReader readBuffer = new BufferedReader(fileReader);
                            whoTurn = Integer.parseInt(readBuffer.readLine());
                            Iterator<Figure> it;
                            Integer elements = Integer.parseInt(readBuffer.readLine());

                            for(int i = 0; i < elements; i++) {
                                Integer loadedNumber = 0;
                                String color = readBuffer.readLine();
                                String chessPiece = readBuffer.readLine();
                                it = newBoard.getFiguresOnBoard().iterator();
                                while(it.hasNext()) {
                                    Figure now = it.next();
                                    if(color.equals(now.getColor()) && chessPiece.equals(now.getChessPiece()) && !now.getIsLoaded() && loadedNumber == 0) {
                                        now.getPosition().setX(Integer.parseInt(readBuffer.readLine()));
                                        now.getPosition().setY(Integer.parseInt(readBuffer.readLine()));
                                        now.setIsLoaded(true);
                                        loadedNumber++;
                                    }
                                }
                            }

                            for(int i = 0; i <= 7; i++) {
                                for(int j = 0; j <= 7; j++) {
                                    value.getBlackPawnValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getBlackKnightValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getBlackBishopValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getBlackQueenValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getBlackKingValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getBlackRookValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getWhitePawnValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getWhiteKnightValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getWhiteBishopValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getWhiteQueenValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getWhiteKingValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                    value.getWhiteRookValue()[i][j] = Integer.parseInt(readBuffer.readLine());
                                }
                            }

                            readBuffer.close();
                            it = newBoard.getFiguresOnBoard().iterator();
                            Figure now;
                            while(it.hasNext()) {
                                now = it.next();
                                Integer x = now.getPosition().getX();
                                Integer y = now.getPosition().getY();
                                String imagePath = now.getImagePath();
                                table.setValueAt(imagePath, y, x);
                            }
                            menuBar.removeAll();
                            menuBar.updateUI();
                            menuBar.add(rewind);
                            menuBar.add(saveGame);
                            menuBar.updateUI();
                            addMouse(table, globalMove, value, newBoard);
                        }
                        catch(IOException ex) {
                            System.out.println("Error reading file");
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

    public static void playHard(Board newBoard, MyTable table, FigurePosition globalMove, Population population, Evaluation value) {
        for (int i = 0; i < 2; i++)
            population.playPopulation(newBoard, value, table, globalMove);
        newBoard.getMovesDone().clear();
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
                        Integer possY;
                        Integer possX;
                        FigurePosition nowMove;
                        int checkCounter = 0;

                        Figure nowCheck;
                        Iterator<Figure> itCheck = newBoard.getFiguresOnBoard().iterator();

                        while(itCheck.hasNext()) {
                            nowCheck = itCheck.next();
                            Iterator<FigurePosition> itCheckMove = nowCheck.getPossibleMoves().iterator();
                            if(nowCheck.getColor() == newBoard.whosTurn(whoTurn)) {
                                while (itCheckMove.hasNext()) {
                                    nowMove = itCheckMove.next();
                                    possY = nowMove.getY();
                                    possX = nowMove.getX();

                                    if (!newBoard.checkHumanCheck(possY, possX, newBoard, whoTurn, newBoard.whosKing(newBoard, whoTurn))) {
                                        checkCounter++;
                                    }
                                }
                            }
                        }

                        if(checkCounter == 0 && newBoard.checkCheck(newBoard, whoTurn, newBoard.whosKing(newBoard, whoTurn)))
                            JOptionPane.showMessageDialog(null, "Check Mate!");
                        else if(checkCounter == 0 && !newBoard.checkCheck(newBoard, whoTurn, newBoard.whosKing(newBoard, whoTurn)))
                            JOptionPane.showMessageDialog(null, "Pat!");
                        Integer y = newBoard.getDragFigure().getPosition().getY();
                        Integer x = newBoard.getDragFigure().getPosition().getX();
                        String imagePath = newBoard.getDragFigure().getImagePath();
                        Figure nowFigure;
                        Boolean validPosition = false;

                        Iterator<FigurePosition> itMove = newBoard.getDragFigure().getPossibleMoves().iterator();

                        while (itMove.hasNext()) {
                            nowMove = itMove.next();
                            possY = nowMove.getY();
                            possX = nowMove.getX();
                            if (possY == row && possX == col) {
                                if(!newBoard.checkHumanCheck(row, col, newBoard, whoTurn, newBoard.whosKing(newBoard, whoTurn))) {
                                    newBoard.dropFigure(row, col);
                                    newBoard.setDraggedFigure(row, col);
                                    table.setValueAt(null, y, x);
                                    table.setValueAt(imagePath, row, col);
                                    validPosition = true;
                                    FigurePosition humanMove = new FigurePosition(col, row);
                                    humanMove.setLocalX(x);
                                    humanMove.setLocalY(y);
                                    newBoard.addMadeMove(humanMove);
                                } else
                                    JOptionPane.showMessageDialog(null, "Check after move!!");
                            }
                        }

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
