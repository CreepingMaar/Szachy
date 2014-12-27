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
    List<FigurePosition> movesDone = new ArrayList<FigurePosition>()
    Figure dragFigure
    Integer turn
    def icons = new String[12]
    Integer kindOfEnd
    Boolean isChanged = false
    
    
    public Board () {
        turn = 1
        width = 8
        height = 8
        cellHeight = 80
        cellWidth = 80
        dragFigure = new Figure("blank", "", "", new FigurePosition(0, 0))
        addGraphics()
    }

    public void resetBoard() {
        movesDone.clear()
        figuresOnBoard.clear()
        figuresOffBoard.clear()
        turn = 1
        width = 8
        height = 8
        cellHeight = 80
        cellWidth = 80
        dragFigure = new Figure("blank", "", "", new FigurePosition(0, 0))
        addGraphics()
    }

    public void addGraphics() {
        if(isChanged == false)
            changeColor("white")
        else
            changeColor("black")
        for(int i = 0; i < width; i++) {
            figuresOnBoard.add(new Figure("black", icons[1], "pawn", new FigurePosition(i, 1)))
            figuresOnBoard.add(new Figure("white", icons[0], "pawn", new FigurePosition(i, 6)))
        }
        figuresOnBoard.add(new Figure("black", icons[3], "rook", new FigurePosition(0, 0)))
        figuresOnBoard.add(new Figure("black", icons[3], "rook", new FigurePosition(7, 0)))
        figuresOnBoard.add(new Figure("white", icons[2], "rook", new FigurePosition(0, 7)))
        figuresOnBoard.add(new Figure("white", icons[2], "rook", new FigurePosition(7, 7)))
        figuresOnBoard.add(new Figure("black", icons[5], "knight", new FigurePosition(1, 0)))
        figuresOnBoard.add(new Figure("black", icons[5], "knight", new FigurePosition(6, 0)))
        figuresOnBoard.add(new Figure("white", icons[4], "knight", new FigurePosition(1, 7)))
        figuresOnBoard.add(new Figure("white", icons[4], "knight", new FigurePosition(6, 7)))
        figuresOnBoard.add(new Figure("black", icons[7], "bishop", new FigurePosition(2, 0)))
        figuresOnBoard.add(new Figure("black", icons[7], "bishop", new FigurePosition(5, 0)))
        figuresOnBoard.add(new Figure("white", icons[6], "bishop", new FigurePosition(2, 7)))
        figuresOnBoard.add(new Figure("white", icons[6], "bishop", new FigurePosition(5, 7)))
        figuresOnBoard.add(new Figure("black", icons[9], "queen", new FigurePosition(3, 0)))
        figuresOnBoard.add(new Figure("black", icons[11], "king", new FigurePosition(4, 0)))
        figuresOnBoard.add(new Figure("white", icons[8], "queen", new FigurePosition(3, 7)))
        figuresOnBoard.add(new Figure("white", icons[10], "king", new FigurePosition(4, 7)))
    }

    public void changeColor(String color) {
        if(color == "white") {
            icons[0] = "src/org/icons/whitePawn.png"
            icons[1] = "src/org/icons/blackPawn.png"
            icons[2] = "src/org/icons/whiteRook.png"
            icons[3] = "src/org/icons/blackRook.png"
            icons[4] = "src/org/icons/whiteKnight.png"
            icons[5] = "src/org/icons/blackKnight.png"
            icons[6] = "src/org/icons/whiteBishop.png"
            icons[7] = "src/org/icons/blackBishop.png"
            icons[8] = "src/org/icons/whiteQueen.png"
            icons[9] = "src/org/icons/blackQueen.png"
            icons[10] = "src/org/icons/whiteKing.png"
            icons[11] = "src/org/icons/blackKing.png"
            isChanged = false
        } else {
            icons[1] = "src/org/icons/whitePawn.png"
            icons[0] = "src/org/icons/blackPawn.png"
            icons[3] = "src/org/icons/whiteRook.png"
            icons[2] = "src/org/icons/blackRook.png"
            icons[5] = "src/org/icons/whiteKnight.png"
            icons[4] = "src/org/icons/blackKnight.png"
            icons[7] = "src/org/icons/whiteBishop.png"
            icons[6] = "src/org/icons/blackBishop.png"
            icons[9] = "src/org/icons/whiteQueen.png"
            icons[8] = "src/org/icons/blackQueen.png"
            icons[11] = "src/org/icons/whiteKing.png"
            icons[10] = "src/org/icons/blackKing.png"
            isChanged = true
        }
    }
    
    public String whosTurn(Integer turn) {
        if(turn == 1)
            return "white"
        else if(turn == -1)
            return "black"
    }

    public Integer playAi(FigurePosition globalMove, JTable table, Integer moveCounter, Population population, Evaluation value) {
        String end = "Valid";
        kindOfEnd = 2

        while(end=="Valid") {
            FigurePosition repeatedPosition = checkRepeated()
            end = this.playTurnAi(globalMove, table, value, repeatedPosition);
            moveCounter++

            if(kindOfEnd == 0)
                end = "Pat"
            else if(kindOfEnd == 1)
                end = "White Check Mate"
            else if(kindOfEnd == -1)
                end = "Black Check Mate"

            addMadeMove(globalMove)
            
            if(moveCounter > 200) {
                end = "Pat";
                kindOfEnd = 0
            }
            if(end != "Valid")
                System.out.println(end);
        }

        return kindOfEnd
    }
    
    public void addMadeMove(FigurePosition globalMove) {
        FigurePosition anotherMove = new FigurePosition(globalMove.getX(), globalMove.getY())
        anotherMove.setLocalX(globalMove.getLocalX())
        anotherMove.setLocalY(globalMove.getLocalY())
        movesDone.add(anotherMove)
    }

    public FigurePosition checkRepeated() {
        if(movesDone.size() >= 10)
            if(isRepeated(movesDone.get(movesDone.size()-2), movesDone.get(movesDone.size()-4))
            && isRepeated(movesDone.get(movesDone.size()-4), movesDone.get(movesDone.size()-6))
            && isRepeated(movesDone.get(movesDone.size()-6), movesDone.get(movesDone.size()-8)))
                return movesDone.get(movesDone.size()-4)
    }

    public Boolean isRepeated(FigurePosition first, FigurePosition second) {
        if(first.getY().equals(second.getLocalY())
        && first.getX().equals(second.getLocalX())
        && first.getLocalY().equals(second.getY())
        && first.getLocalX().equals(second.getX()))
            return true
    }
    
    public String playTurnAi(FigurePosition globalMove, JTable table, Evaluation value, FigurePosition repeatedMove) {
        Iterator<Figure> it = this.getFiguresOnBoard().iterator()
        Figure now
        while(it.hasNext()) {
            now = it.next()
            now.getPossibleMoves().clear()
        }
        value.generateGlobalMoves(this)
        value.maxi(this, 3, globalMove, -100000, 100000, repeatedMove)
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
    
    public Boolean choseDragFigure(Integer row, Integer col, FigurePosition globalMove, Evaluation value) {
        Figure now
        String nowColor
        Integer x, y
        Integer temp
        FigurePosition repeatedPosition
        temp = value.maxi(this, 3, globalMove, -100000, 100000, repeatedPosition)
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
    
    public Figure dropFigure(Integer row, Integer col) {
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
                return now;
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
        
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next()
            String nowColor = nowFigure.getColor()
            String nowPiece = nowFigure.getChessPiece()
            if(nowColor == board.whosTurn(localTurn) && nowPiece == "king")
                return nowFigure
        }
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
                now.getPossibleMoves().clear();
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

    public Boolean checkHumanCheck(Integer row, Integer col, Board board, Integer localTurn, Figure king) {
        Iterator<Figure> itT = this.getFiguresOnBoard().iterator();
        Figure nowT
        Integer xT, yT
        String myColor
        Figure dropped
        while(itT.hasNext()) {
            nowT = itT.next();
            xT = nowT.getPosition().getX()
            yT = nowT.getPosition().getY()
            if(xT==col && yT==row) {
                myColor = nowT.getColor()
                nowT.setColor("")
                dropped = nowT
            }
        }
        Integer x = this.getDragFigure().getPosition().getX()
        Integer y = this.getDragFigure().getPosition().getY()
        Iterator<Figure> it = this.getFiguresOnBoard().iterator()
        Boolean willCheck = false
        Figure moved;
        while(it.hasNext()) {
            Figure now = it.next()
            if(now.getPosition().getX()==x && now.getPosition().getY()==y) {
                now.getPosition().setX(col)
                now.getPosition().setY(row)
                moved = now
            }
        }
        if(checkCheck(board, localTurn, king))
            willCheck = true
        if(dropped) {
            dropped.setColor(myColor)
        }
        moved.getPosition().setX(x)
        moved.getPosition().setY(y)
        return willCheck
    }
}

