/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.domain
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
    
    
    public Board () {
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
    
    public Boolean choseDragFigure(Integer row, Integer col) {
        Iterator<Figure> it = this.getFiguresOnBoard().iterator();
        Figure now
        Evaluation value = new Evaluation()
        Integer x, y
        while(it.hasNext()) {
            now = it.next();
            x = now.getPosition().getX();
            y = now.getPosition().getY();
            if(x==col && y==row) {
                dragFigure = now
                dragFigure.movePossibility()
                dragFigure.checkMoves(this)
                value.evaluate(this)
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
            } 
        }
    }
    
    
}

