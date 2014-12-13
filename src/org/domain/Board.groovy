/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.domain

import javax.swing.JTable

/**
 *
 * @author Marek
 */
class Board {
    
    Integer width
    Integer height
    Integer cellHeight
    Integer cellWidth
    Set<Figure> figuresOnBoard = new HashSet<Figure>()
    Set<Figure> figuresOffBoard = new HashSet<Figure>()
    Figure dragFigure
    Integer turn
    String turnColor
    Integer endOfGame
    
    
    public Board () {
        turn = 1
        width = 8
        height = 8
        cellHeight = 80
        cellWidth = 80
        dragFigure = new Figure("blank", "", "", new FigurePosition(0, 0))
        for(int i = 0; i < width; i++) {
            figuresOnBoard.add(new Figure("black", "src/org/icons/chess-666-xl.png", "pawn", new FigurePosition(i, 1)))
            figuresOnBoard.add(new Figure("white", "src/org/icons/chess-8-xl.png", "pawn", new FigurePosition(i, 6)))
        }
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-3-xl.png", "rook", new FigurePosition(0, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-3-xl.png", "rook", new FigurePosition(7, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-41-xl.png", "rook", new FigurePosition(0, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-41-xl.png", "rook", new FigurePosition(7, 7)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-44-xl.png", "knight", new FigurePosition(1, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-44-xl.png", "knight", new FigurePosition(6, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-5-xl.png", "knight", new FigurePosition(1, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-5-xl.png", "knight", new FigurePosition(6, 7)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-21-xl.png", "bishop", new FigurePosition(2, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-21-xl.png", "bishop", new FigurePosition(5, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-30-xl.png", "bishop", new FigurePosition(2, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-30-xl.png", "bishop", new FigurePosition(5, 7)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-16-xl.png", "queen", new FigurePosition(3, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-26-xl.png", "king", new FigurePosition(4, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-2-xl.png", "queen", new FigurePosition(3, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-18-xl.png", "king", new FigurePosition(4, 7)))
    }
    
    public String whosTurn(Integer turn) {
        if(turn == 1)
            return "white"
        else if(turn == -1)
            return "black"
    }
    
    public void resetBoard() {
        figuresOnBoard.clear()
        figuresOffBoard.clear()
        turn = 1
        width = 8
        height = 8
        cellHeight = 80
        cellWidth = 80
        dragFigure = new Figure("blank", "", "", new FigurePosition(0, 0))
        for(int i = 0; i < width; i++) {
            figuresOnBoard.add(new Figure("black", "src/org/icons/chess-666-xl.png", "pawn", new FigurePosition(i, 1)))
            figuresOnBoard.add(new Figure("white", "src/org/icons/chess-8-xl.png", "pawn", new FigurePosition(i, 6)))
        }
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-3-xl.png", "rook", new FigurePosition(0, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-3-xl.png", "rook", new FigurePosition(7, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-41-xl.png", "rook", new FigurePosition(0, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-41-xl.png", "rook", new FigurePosition(7, 7)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-44-xl.png", "knight", new FigurePosition(1, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-44-xl.png", "knight", new FigurePosition(6, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-5-xl.png", "knight", new FigurePosition(1, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-5-xl.png", "knight", new FigurePosition(6, 7)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-21-xl.png", "bishop", new FigurePosition(2, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-21-xl.png", "bishop", new FigurePosition(5, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-30-xl.png", "bishop", new FigurePosition(2, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-30-xl.png", "bishop", new FigurePosition(5, 7)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-16-xl.png", "queen", new FigurePosition(3, 0)))
        figuresOnBoard.add(new Figure("black", "src/org/icons/chess-26-xl.png", "king", new FigurePosition(4, 0)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-2-xl.png", "queen", new FigurePosition(3, 7)))
        figuresOnBoard.add(new Figure("white", "src/org/icons/chess-18-xl.png", "king", new FigurePosition(4, 7)))
    }
    
    public void playAi(FigurePosition globalMove, JTable table, Integer moveCounter) {
        String end = "Valid";
        Population population = new Population()
        Evaluation value = new Evaluation()
        FigurePosition repeatedPosition
        population.setValues(value, 0, 1)
        def lastMoves = new Integer[6][2]
        
        while(end=="Valid") {
            end = this.playTurnAi(globalMove, table, value, repeatedPosition);
            moveCounter++
            repeatedPosition = null;
            repeatedPosition = reapetedMoves(globalMove, lastMoves)
            
            if(moveCounter > 200)
                end = "Pat";
            if(end != "Valid")
                System.out.println(end);
        }
        population.bubbleSort()
    }
    
    public FigurePosition reapetedMoves(FigurePosition globalMove, Integer[][] lastMoves) {
        FigurePosition repeatedPosition
        lastMoves[0][0] = lastMoves[1][0]
        lastMoves[1][0] = lastMoves[2][0]
        lastMoves[2][0] = lastMoves[3][0]
        lastMoves[3][0] = lastMoves[4][0]
        lastMoves[4][0] = lastMoves[5][0]
        lastMoves[5][0] = globalMove.getLocalX()
        lastMoves[0][1] = lastMoves[1][1]
        lastMoves[1][1] = lastMoves[2][1]
        lastMoves[2][1] = lastMoves[3][1]
        lastMoves[3][1] = lastMoves[4][1]
        lastMoves[4][1] = lastMoves[5][1]
        lastMoves[5][1] = globalMove.getLocalY()
        if(lastMoves[0][0] == lastMoves[4][0] && lastMoves[1][0] == lastMoves[5][0] && lastMoves[0][1] == lastMoves[4][1] && lastMoves[1][1] == lastMoves[5][1]) {
            repeatedPosition = new FigurePosition(lastMoves[2][0], lastMoves[2][1])
            repeatedPosition.setLocalX(lastMoves[0][0])
            repeatedPosition.setLocalY(lastMoves[0][1])
        }
        return repeatedPosition
    }
    
    public String playTurnAi(FigurePosition globalMove, JTable table, Evaluation value, FigurePosition repeatedMove) {
        Iterator<Figure> it = this.getFiguresOnBoard().iterator()
        Figure now
        String imagePath
        while(it.hasNext()) {
            now = it.next()
            now.getPossibleMoves().clear()
        }
        value.generateGlobalMoves(this)
        Integer temp
        temp = value.maxi(this, 3, globalMove, -100000, 100000, repeatedMove)
        if(globalMove.getX() == -1 && globalMove.getY() == -1)
            return 1
        if(globalMove.getX() == -2 && globalMove.getY() == -2)
            return 2
        it = this.getFiguresOnBoard().iterator()
        while(it.hasNext()) {
            now = it.next()
            now.getPossibleMoves().clear()
        }
        value.generateGlobalMoves(this)
        it = this.getFiguresOnBoard().iterator()
        while(it.hasNext()) {
            now = it.next()
            if(now.getPosition().getY() == globalMove.getLocalY() && now.getPosition().getX() == globalMove.getLocalX())
                dragFigure = now
        }
        dropFigure(globalMove.getY(), globalMove.getX())
        setDraggedFigure(globalMove.getY(), globalMove.getX())
        table.setValueAt(null, globalMove.getLocalY(), globalMove.getLocalX())
        table.setValueAt(dragFigure.getImagePath(), globalMove.getY(), globalMove.getX())
        return "Valid"
    }
    
    public Boolean choseDragFigure(Integer row, Integer col, FigurePosition globalMove) {
        Figure now
        Evaluation value = new Evaluation()
        String nowColor
        Integer x, y
        Integer temp
        temp = value.maxi(this, 3, globalMove, -100000, 100000)
        if(globalMove.getX() == -1 && globalMove.getY() == -1)
            println "Check Mate"
        Iterator<Figure> it = this.getFiguresOnBoard().iterator();
        while(it.hasNext()) {
            now = it.next()
            now.getPossibleMoves().clear()
        }
        value.generateGlobalMoves(this)
        it = this.getFiguresOnBoard().iterator()
        while(it.hasNext()) {
            now = it.next();
            x = now.getPosition().getX()
            y = now.getPosition().getY()
            nowColor = now.getColor()
            if(x==col && y==row && whosTurn(turn) == nowColor) {
                dragFigure = now
                return true;
            }
        };
        return false;
    }
    
    public void dropFigure(Integer row, Integer col) {
        Iterator<Figure> it = this.getFiguresOnBoard().iterator();
        Figure now
        Integer x, y
        while(it.hasNext()) {
            now = it.next();
            x = now.getPosition().getX()
            y = now.getPosition().getY()
            if(x==col && y==row) {
                this.getFiguresOffBoard().add(now);  
                it.remove();
            } 
        }
    }
    
    public void setDraggedFigure(Integer row, Integer col) {
        Integer x = this.getDragFigure().getPosition().getX()
        Integer y = this.getDragFigure().getPosition().getY()
        Iterator<Figure> it = this.getFiguresOnBoard().iterator();
        while(it.hasNext()) {
            Figure now = it.next();
            if(now.getPosition().getX()==x && now.getPosition().getY()==y) { 
                now.getPosition().setX(col);
                now.getPosition().setY(row);
                now.setStartPosition(false)
                turn = -turn
            } 
        }
    }
    public Figure whosKing(Board board, Integer localTurn) {
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator()
        Figure nowFigure
        Figure myKing
        
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next()
            String nowColor = nowFigure.getColor()
            String nowPiece = nowFigure.getChessPiece()
            if(nowColor == board.whosTurn(localTurn) && nowPiece == "king")
                myKing = nowFigure
        }
        return myKing
    }
    
    public Boolean checkCheck(Board board, Integer localTurn, Figure king) {
        Integer kingX = king.getPosition().getX()
        Integer kingY = king.getPosition().getY()
        Iterator<Figure> it = board.getFiguresOnBoard().iterator();
        FigurePosition nowMove
        Figure now
        String nowColor
        while(it.hasNext()) {
            now = it.next();
            nowColor = now.getColor()
            if(board.whosTurn(-localTurn) == nowColor) {
                now.movePossibility(board)
                now.checkMoves(board, -localTurn)
                Iterator<FigurePosition> itMove = now.getPossibleMoves().iterator()
                while(itMove.hasNext()) {
                    nowMove = itMove.next()
                    if(nowMove.getX() == kingX && nowMove.getY() == kingY)
                        return true
                }
            }
        }
        return false
    }
}

