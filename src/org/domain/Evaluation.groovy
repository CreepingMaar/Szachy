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
class Evaluation {

Integer[][] pawnValue = [
    [0,  0,  0,  0,  0,  0,  0,  0],
    [50, 50, 50, 50, 50, 50, 50, 50],
    [10, 10, 20, 30, 30, 20, 10, 10],
    [5,  5, 10, 25, 25, 10,  5,  5],
    [0,  0,  0, 20, 20,  0,  0,  0],
    [5, -5,-10,  0,  0,-10, -5,  5],
    [5, 10, 10,-20,-20, 10, 10,  5],
    [0,  0,  0,  0,  0,  0,  0,  0]
]

Integer[][] knightValue = [
    [-50,-40,-30,-30,-30,-30,-40,-50],
    [-40,-20,  0,  0,  0,  0,-20,-40],
    [-30,  0, 10, 15, 15, 10,  0,-30],
    [-30,  5, 15, 20, 20, 15,  5,-30],
    [-30,  0, 15, 20, 20, 15,  0,-30],
    [-30,  5, 10, 15, 15, 10,  5,-30],
    [-40,-20,  0,  5,  5,  0,-20,-40],
    [-50,-40,-30,-30,-30,-30,-40,-50],
]

Integer[][] bishopValue = [
    [-20,-10,-10,-10,-10,-10,-10,-20],
    [-10,  0,  0,  0,  0,  0,  0,-10],
    [-10,  0,  5, 10, 10,  5,  0,-10],
    [-10,  5,  5, 10, 10,  5,  5,-10],
    [-10,  0, 10, 10, 10, 10,  0,-10],
    [-10, 10, 10, 10, 10, 10, 10,-10],
    [-10,  5,  0,  0,  0,  0,  5,-10],
    [-20,-10,-10,-10,-10,-10,-10,-20],
]

Integer[][] rookValue = [
    [0,  0,  0,  0,  0,  0,  0,  0],
    [5, 10, 10, 10, 10, 10, 10,  5],
    [-5,  0,  0,  0,  0,  0,  0, -5],
    [-5,  0,  0,  0,  0,  0,  0, -5],
    [-5,  0,  0,  0,  0,  0,  0, -5],
    [-5,  0,  0,  0,  0,  0,  0, -5],
    [-5,  0,  0,  0,  0,  0,  0, -5],
    [0,  0,  0,  5,  5,  0,  0,  0]
]

Integer[][] kingValue = [
    [-30,-40,-40,-50,-50,-40,-40,-30],
    [-30,-40,-40,-50,-50,-40,-40,-30],
    [-30,-40,-40,-50,-50,-40,-40,-30],
    [-30,-40,-40,-50,-50,-40,-40,-30],
    [-20,-30,-30,-40,-40,-30,-30,-20],
    [-10,-20,-20,-20,-20,-20,-20,-10],
    [20, 20,  0,  0,  0,  0, 20, 20],
    [20, 30, 10,  0,  0, 10, 30, 20]
]

Integer[][] queenValue = [
    [-20,-10,-10, -5, -5,-10,-10,-20],
    [-10,  0,  0,  0,  0,  0,  0,-10],
    [-10,  0,  5,  5,  5,  5,  0,-10],
    [-5,  0,  5,  5,  5,  5,  0, -5],
    [0,  0,  5,  5,  5,  5,  0, -5],
    [-10,  5,  5,  5,  5,  5,  0,-10],
    [-10,  0,  5,  0,  0,  0,  0,-10],
    [-20,-10,-10, -5, -5,-10,-10,-20]
]

    public Evaluation() {
        
    }
    
    Set<FigurePosition> allMoves = new HashSet<FigurePosition>()
    
    public void generateMoves(Board board) {
        Iterator<Figure> it = board.getFiguresOnBoard().iterator();
        Figure now
        while(it.hasNext()) {
            now = it.next();
            now.movePossibility()
            now.checkMoves(board)
            allMoves.addAll(now.getPossibleMoves())
        };

    }

    public Integer evaluate(Board board) {
        Integer scoreWhite = 0
        Integer scoreBlack = 0
        Integer x
        Integer y
        String chessPiece
        String color
        Figure now
        Iterator<Figure> it = board.getFiguresOnBoard().iterator();
        
        while(it.hasNext()) {
            now = it.next();
            chessPiece = now.getChessPiece()
            color = now.getColor()
            x = now.getPosition().getX()
            if(color == "black")
                y = 7 - now.getPosition().getY()
            else
                y = now.getPosition().getY()
                
            if(chessPiece == "queen") {
                if(color == "white")
                    scoreWhite += queenValue[x][y]
                else if (color == "black")
                    scoreBlack += queenValue[x][y]
            }
            else if(chessPiece == "king") {
                if(color == "white")
                    scoreWhite += kingValue[x][y]
                else if (color == "black")
                    scoreBlack += kingValue[x][y]
            }
            else if(chessPiece == "rook") {
                if(color == "white")
                    scoreWhite += rookValue[x][y]
                else if (color == "black")
                    scoreBlack += rookValue[x][y]
            }
            else if(chessPiece == "pawn") {
                if(color == "white")
                    scoreWhite += pawnValue[x][y]
                else if (color == "black")
                    scoreBlack += pawnValue[x][y]
            }
            else if(chessPiece == "bishop") {
                if(color == "white")
                    scoreWhite += bishopValue[x][y]
                else if (color == "black")
                    scoreBlack += bishopValue[x][y]
            }
            else if(chessPiece == "knight") {
                if(color == "white")
                    scoreWhite += knightValue[x][y]
                else if (color == "black")
                    scoreBlack += knightValue[x][y]
            }
        }
        
        return scoreWhite - scoreBlack
    }
    
    public FigurePosition maxi(Board board, Integer depth) {
        if(depth<=0)
            evaluate(board)
            
        Integer bestScore = -1
        FigurePosition bestMove
        Figure nowFigure;
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator();
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next();
            nowFigure.getPossibleMoves().clear();
        }    
        generateMoves(board)
        FigurePosition nowMove
        Iterator<FigurePosition> itMove = allMoves.iterator()
        FigurePosition backupMove
        
        while(itMove.hasNext()) {
            nowMove = itMove.next()
            backupMove = makeMove(nowMove, board)
            //mini(board, depth-1)
            if(evaluate(board) > bestScore) {
                bestScore = evaluate(board)
                bestMove = nowMove
            }
            undoMove(nowMove, backupMove, board)
        }
        
        return bestMove
    }
    
    public FigurePosition mini(Board board, Integer depth) {
        if(depth<=0)
            evaluate(board)
            
        Integer bestScore = 100000
        FigurePosition bestMove
        Figure nowFigure;
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator();
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next();
            nowFigure.getPossibleMoves().clear();
        }      
        generateMoves(board)
        FigurePosition nowMove
        Iterator<FigurePosition> itMove = allMoves.iterator()
        FigurePosition backupMove
        
        while(itMove.hasNext()) {
            nowMove = itMove.next()
            backupMove = makeMove(nowMove, board)
            maxi(board, depth-1)
            if(evaluate(board) < bestScore) {
                bestScore = evaluate(board)
                bestMove = nowMove
            }
            undoMove(nowMove, backupMove, board)
        }
        
        return bestMove
    }
    
    public FigurePosition makeMove(FigurePosition nowMove, Board board) {
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator()
        Figure nowFigure
        FigurePosition nowFigureMove
        Iterator<FigurePosition> itMove
        FigurePosition backupMove
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next()
            itMove = nowFigure.getPossibleMoves().iterator()
            while(itMove.hasNext()) {
                nowFigureMove = itMove.next()
                if(nowFigureMove == nowMove) {
                    backupMove = nowFigure.getPosition()
                    nowFigure.setPosition(nowMove)
                }
            }      
        }
        return backupMove
    }
    
    public void undoMove(FigurePosition nowMove, FigurePosition backupMove, Board board) {
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator()
        Figure nowFigure
        FigurePosition nowFigureMove
        Iterator<FigurePosition> itMove
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next()
            itMove = nowFigure.getPossibleMoves().iterator()
            while(itMove.hasNext()) {
                nowFigureMove = itMove.next()
                if(nowFigureMove == nowMove) {
                    nowFigure.setPosition(backupMove)
                }
            }      
        }
    }
	
}