/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.domain

import java.util.concurrent.CopyOnWriteArraySet

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

    Integer[] figureValue = [
        100, 320, 330, 500, 900, 20000
    ]

    public Evaluation() {
        
    }
    
    Integer localTurn
    
    public void generateMoves(Board board, Set<FigurePosition> localMoves) {
        Iterator<Figure> it = board.getFiguresOnBoard().iterator();
        FigurePosition nowMove
        Figure now
        String nowColor
        while(it.hasNext()) {
            now = it.next();
            nowColor = now.getColor()
            if(board.whosTurn(localTurn) == nowColor) {
                now.movePossibility(board)
                now.checkMoves(board, localTurn)
                Iterator<FigurePosition> itMove = now.getPossibleMoves().iterator()
                while(itMove.hasNext()) {
                    nowMove = itMove.next()
                    nowMove.setLocalX(now.getPosition().getX())
                    nowMove.setLocalY(now.getPosition().getY())
                }
                localMoves.addAll(now.getPossibleMoves())
            }
        };
    }
    
    public void generateGlobalMoves(Board board) {
        Iterator<Figure> it = board.getFiguresOnBoard().iterator();
        Figure now
        String color
        while(it.hasNext()) {
            now = it.next();
            color = now.getColor()
            if(color == board.whosTurn(board.getTurn())) {
                now.movePossibility(board)
                now.checkMoves(board, board.getTurn())
            }
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
                if(color == "white") {
                    scoreWhite += queenValue[y][x]
                    scoreWhite += figureValue[4]
                }
                else if (color == "black") {
                    scoreBlack += queenValue[y][x]
                    scoreBlack += figureValue[4]
                }
            }
            else if(chessPiece == "king") {
                if(color == "white") {
                    scoreWhite += kingValue[y][x]
                    scoreWhite += figureValue[5]
                }
                else if (color == "black") {
                    scoreBlack += kingValue[y][x]
                    scoreBlack += figureValue[5]
                }
            }
            else if(chessPiece == "rook") {
                if(color == "white") {
                    scoreWhite += rookValue[y][x]
                    scoreWhite += figureValue[3]
                }
                else if (color == "black") {
                    scoreBlack += rookValue[y][x]
                    scoreBlack += figureValue[3]
                }
            }
            else if(chessPiece == "pawn") {
                if(color == "white") {
                    scoreWhite += pawnValue[y][x]
                    scoreWhite += figureValue[0]
                }
                else if (color == "black") {
                    scoreBlack += pawnValue[y][x]
                    scoreBlack += figureValue[0]
                }
            }
            else if(chessPiece == "bishop") {
                if(color == "white") {
                    scoreWhite += bishopValue[y][x]
                    scoreWhite += figureValue[2]
                }
                else if (color == "black") {
                    scoreBlack += bishopValue[y][x]
                    scoreBlack += figureValue[2]
                }
            }
            else if(chessPiece == "knight") {
                if(color == "white") {
                    scoreWhite += knightValue[y][x]
                    scoreWhite += figureValue[1]
                }
                else if (color == "black") {
                    scoreBlack += knightValue[y][x]
                    scoreBlack += figureValue[1]
                }
            }
        }
        
        if(board.whosTurn(board.getTurn()) == "white")
            return scoreWhite - scoreBlack
        if(board.whosTurn(board.getTurn()) == "black")
            return scoreBlack - scoreWhite
    }
    
    def moves = new FigurePosition[3]
    
    
    public Integer maxi(Board board, Integer depth, FigurePosition globalMove) {
        localTurn = board.getTurn()
        if(depth<=0)
            return evaluate(board)
        Set<FigurePosition> localMoves = new CopyOnWriteArraySet<FigurePosition>()    
        localMoves.clear()    
        Integer bestScore = -100000
        FigurePosition bestMove
        Figure nowFigure;
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator();
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next();
            nowFigure.getPossibleMoves().clear();
        }    
        generateMoves(board, localMoves)
        
        Iterator<FigurePosition> itMove = localMoves.iterator()
        
        while(itMove.hasNext()) {
            localTurn = board.getTurn()
            FigurePosition nowMove
            def backupFigure = new Figure[2]
            Integer value
            nowMove = itMove.next()
            moves[depth-1] = nowMove
            backupFigure = moveFigure(board, nowMove)
            value = mini(board, depth-1, globalMove)
            if(value > bestScore) {
                bestScore = value
                bestMove = nowMove
            }
            undoMove(board, backupFigure[0], nowMove, backupFigure[1])
            localTurn = board.getTurn()
        }
        
        if(depth == 3) {
            globalMove.setX(bestMove.getX()) 
            globalMove.setY(bestMove.getY())
            globalMove.setLocalX(bestMove.getLocalX()) 
            globalMove.setLocalY(bestMove.getLocalY())
            
        }
            
        return bestScore
    }
    
    public Integer mini(Board board, Integer depth, FigurePosition globalMove) {
        localTurn = -board.getTurn()
        if(depth<=0)
            return evaluate(board)
        Set<FigurePosition> localMoves = new CopyOnWriteArraySet<FigurePosition>()    
        localMoves.clear()     
        Integer bestScore = 100000
        FigurePosition bestMove
        Figure nowFigure;
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator();
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next();
            nowFigure.getPossibleMoves().clear();
        }      
        generateMoves(board, localMoves)
        Iterator<FigurePosition> itMove = localMoves.iterator()
        while(itMove.hasNext()) {
            localTurn = -board.getTurn()
            FigurePosition nowMove
            def backupFigure = new Figure[2]
            Integer value
            nowMove = itMove.next()
            moves[depth-1] = nowMove
            backupFigure = moveFigure(board, nowMove)
            value = maxi(board, depth-1, globalMove)
            if(value < bestScore) {
                bestScore = value
                bestMove = nowMove
            }
            undoMove(board, backupFigure[0], nowMove, backupFigure[1])
            localTurn = -board.getTurn()
        }
          
        return bestScore
    }
    
    public Figure[] moveFigure(Board board, FigurePosition nowMove) {
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator();
        Figure nowFigure
        Figure backupFigure
        Figure beaten
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next()
            Integer nowX = nowFigure.getPosition().getX()
            Integer nowY = nowFigure.getPosition().getY()
            if(nowY == nowMove.getLocalY() && nowX == nowMove.getLocalX()) {
                backupFigure = nowFigure
            }
            if(nowY == nowMove.getY() && nowX == nowMove.getX()) {
                beaten = nowFigure
            }
        }
        backupFigure.setPosition(nowMove)
        
        if(beaten) {
            board.getFiguresOffBoard().add(beaten)
            board.getFiguresOnBoard().remove(beaten)
        }
        
        def figures = new Figure[2]
        figures[0] = backupFigure
        figures[1] = beaten
            
        return figures
    }
    
    public void undoMove(Board board, Figure backupFigure, FigurePosition nowMove, Figure beaten) {
        if(beaten) {
            board.getFiguresOnBoard().add(beaten)
            board.getFiguresOffBoard().remove(beaten)
        }
            
        Integer x = nowMove.getLocalX()
        Integer y = nowMove.getLocalY()
        backupFigure.getPosition().setX(x)
        backupFigure.getPosition().setY(y)
    }
}