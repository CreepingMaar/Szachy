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
    
    def blackPawnValue = new Integer[8][8]
    def blackKnightValue = new Integer[8][8]
    def blackBishopValue = new Integer[8][8]
    def blackQueenValue = new Integer[8][8]
    def blackKingValue = new Integer[8][8]
    def blackRookValue = new Integer[8][8]
    def whitePawnValue = new Integer[8][8]
    def whiteKnightValue = new Integer[8][8]
    def whiteBishopValue = new Integer[8][8]
    def whiteQueenValue = new Integer[8][8]
    def whiteKingValue = new Integer[8][8]
    def whiteRookValue = new Integer[8][8]

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
                    scoreWhite += whiteQueenValue[y][x]
                    scoreWhite += figureValue[4]
                }
                else if (color == "black") {
                    scoreBlack += blackQueenValue[y][x]
                    scoreBlack += figureValue[4]
                }
            }
            else if(chessPiece == "king") {
                if(color == "white") {
                    scoreWhite += whiteKingValue[y][x]
                    scoreWhite += figureValue[5]
                }
                else if (color == "black") {
                    scoreBlack += blackKingValue[y][x]
                    scoreBlack += figureValue[5]
                }
            }
            else if(chessPiece == "rook") {
                if(color == "white") {
                    scoreWhite += whiteRookValue[y][x]
                    scoreWhite += figureValue[3]
                }
                else if (color == "black") {
                    scoreBlack += blackRookValue[y][x]
                    scoreBlack += figureValue[3]
                }
            }
            else if(chessPiece == "pawn") {
                if(color == "white") {
                    scoreWhite += whitePawnValue[y][x]
                    scoreWhite += figureValue[0]
                }
                else if (color == "black") {
                    scoreBlack += blackPawnValue[y][x]
                    scoreBlack += figureValue[0]
                }
            }
            else if(chessPiece == "bishop") {
                if(color == "white") {
                    scoreWhite += whiteBishopValue[y][x]
                    scoreWhite += figureValue[2]
                }
                else if (color == "black") {
                    scoreBlack += blackBishopValue[y][x]
                    scoreBlack += figureValue[2]
                }
            }
            else if(chessPiece == "knight") {
                if(color == "white") {
                    scoreWhite += whiteKnightValue[y][x]
                    scoreWhite += figureValue[1]
                }
                else if (color == "black") {
                    scoreBlack += blackKnightValue[y][x]
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

    public Boolean moveEquals(FigurePosition first, FigurePosition second) {
        if(first.getY().equals(second.getY())
        && first.getX().equals(second.getX())
        && first.getLocalY().equals(second.getLocalY())
        && first.getLocalX().equals(second.getLocalX()))
            return true
    }
    
    
    public Integer maxi(Board board, Integer depth, FigurePosition globalMove, int alfa, int beta, FigurePosition repeatedMove) {
        localTurn = board.getTurn()
        if(depth<=0)
            return evaluate(board)
        Set<FigurePosition> localMoves = new HashSet<FigurePosition>()    
        localMoves.clear()  
        FigurePosition bestMove
        Figure nowFigure;
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator();
        while(itFigure.hasNext()) {
            nowFigure = itFigure.next();
            nowFigure.getPossibleMoves().clear();
        }    
        generateMoves(board, localMoves)
        Boolean isNow=true
        if(depth == 3)
            isNow = board.checkCheck(board, localTurn, board.whosKing(board, localTurn))
        
        Iterator<FigurePosition> itMove = localMoves.iterator()
        
        while(itMove.hasNext()) {
            localTurn = board.getTurn()
            FigurePosition nowMove
            Integer value
            nowMove = itMove.next()
            moves[depth-1] = nowMove
            Figure[] backupFigure = moveFigure(board, nowMove)
            if(depth == 3) {
                Boolean isCheck
                isCheck = board.checkCheck(board, localTurn, board.whosKing(board, localTurn))
                if (!isCheck && !repeatedMove) {
                    value = mini(board, depth - 1, globalMove, alfa, beta, repeatedMove)
                    if (value > alfa) {
                        alfa = value
                        bestMove = nowMove
                    }
                } else if(!isCheck && !moveEquals(repeatedMove, nowMove)) {
                    value = mini(board, depth - 1, globalMove, alfa, beta, repeatedMove)
                    if (value > alfa) {
                        alfa = value
                        bestMove = nowMove
                    }
                }
            }
            else {
                value = mini(board, depth-1, globalMove, alfa, beta, repeatedMove)
                if(value > alfa) {
                    alfa = value
                    bestMove = nowMove
                }
            }
            undoMove(board, backupFigure[0], nowMove, backupFigure[1])
            localTurn = board.getTurn()
            if(alfa >= beta)
                return beta
        }
        
        if(depth == 3 && bestMove) {
            globalMove.setX(bestMove.getX()) 
            globalMove.setY(bestMove.getY())
            globalMove.setLocalX(bestMove.getLocalX()) 
            globalMove.setLocalY(bestMove.getLocalY())
        }
        else if(depth == 3 && !bestMove && isNow) {
            globalMove.setX(-1) 
            globalMove.setY(-1)
            globalMove.setLocalX(-1) 
            globalMove.setLocalY(-1)
        }
        else if(depth == 3 && !bestMove && !isNow) {
            globalMove.setX(-2) 
            globalMove.setY(-2)
            globalMove.setLocalX(-2) 
            globalMove.setLocalY(-2)
        }
        return alfa
    }
    
    public Integer mini(Board board, Integer depth, FigurePosition globalMove, int alfa, int beta, FigurePosition repeatedMove) {
        localTurn = -board.getTurn()
        if(depth<=0)
            return evaluate(board)
        Set<FigurePosition> localMoves = new HashSet<FigurePosition>()    
        localMoves.clear()
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
            Integer value
            nowMove = itMove.next()
            moves[depth-1] = nowMove
            Figure[] backupFigure = moveFigure(board, nowMove)
            value = maxi(board, depth-1, globalMove, alfa, beta, repeatedMove)
            if(value < beta) {
                beta = value
            }
            undoMove(board, backupFigure[0], nowMove, backupFigure[1])
            localTurn = -board.getTurn()
            if(beta <= alfa)
                return alfa
        }
          
        return beta
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