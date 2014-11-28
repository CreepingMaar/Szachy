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
	
}