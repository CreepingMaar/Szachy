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
public class Figure {
    
    String color
    String imagePath
    String chessPiece
    FigurePosition position
    Set<FigurePosition> possibleMoves = new HashSet<FigurePosition>()
    Boolean startPosition
    
    Figure(String color, String imagePath, String chessPiece, FigurePosition position) {
        this.color = color
        this.imagePath = imagePath
        this.chessPiece = chessPiece
        this.position = position
        this.startPosition = true
    }
    
    public void movePossibility() {
        if(chessPiece=="pawn") {
            this.generatePawnMoves()
        }
        if(chessPiece=="rook") {
            this.generateRookMoves()
        }
        if(chessPiece=="bishop") {
            this.generateBishopMoves()
        }
        if(chessPiece=="queen") {
            this.generateRookMoves()
            this.generateBishopMoves()
        }
        if(chessPiece=="knight") {
            this.generateKnightMoves()
        }
        if(chessPiece=="king") {
            this.generateKingMoves()
        }
    }
    
    public void generatePawnMoves() {
        Integer x = position.getX()
        Integer y = position.getY()
        
        if(color=="white") {
            if(y-1>=0)
                possibleMoves.add(new FigurePosition(x, y-1))
            if(startPosition)
                possibleMoves.add(new FigurePosition(x, y-2))
        }
        else if(color=="black") {
            if(y+1<=7)
                possibleMoves.add(new FigurePosition(x, y+1))
            if(startPosition)
                possibleMoves.add(new FigurePosition(x, y+2))
        }
            
        startPosition = false
    }
    
    public void generateRookMoves() {
        Integer i
        Integer x = position.getX()
        Integer y = position.getY()
        
        for(i=x-1;i>=0;i--)
            possibleMoves.add(new FigurePosition(i, y))
            
        for(i=x+1;i<=7;i++)
            possibleMoves.add(new FigurePosition(i, y))
            
        for(i=y-1;i>=0;i--)
            possibleMoves.add(new FigurePosition(x, i))
            
        for(i=y+1;i<=7;i++)
            possibleMoves.add(new FigurePosition(x, i))
       
            
        startPosition = false
    }
    
    public void generateBishopMoves() {
        Integer x = position.getX()
        Integer y = position.getY()
        
        Integer i = x-1
        Integer j = y-1
        while(i>=0 && j>=0) {
            possibleMoves.add(new FigurePosition(i, j))
            i--
            j--
        }
        
        i = x-1
        j = y+1
        while(i>=0 && j<=7) {
            possibleMoves.add(new FigurePosition(i, j))
            i--
            j++
        }
        
        i = x+1
        j = y+1
        while(i<=7 && j<=7) {
            possibleMoves.add(new FigurePosition(i, j))
            i++
            j++
        }
        
        i = x+1
        j = y-1
        while(i<=7 && j>=0) {
            possibleMoves.add(new FigurePosition(i, j))
            i++
            j--
        }
            
        startPosition = false
    }
    
    public void generateKnightMoves() {
        Integer i
        Integer x = position.getX()
        Integer y = position.getY()
        
        if(x-2>=0 && y-1>=0)
            possibleMoves.add(new FigurePosition(x-2, y-1))
        if(x-2>=0 && y+1<=7)
            possibleMoves.add(new FigurePosition(x-2, y+1))
        if(x-1>=0 && y-2>=0)
            possibleMoves.add(new FigurePosition(x-1, y-2))    
        if(x-1>=0 && y+2<=7)
            possibleMoves.add(new FigurePosition(x-1, y+2))
        if(x+1<=7 && y-2>=0)
            possibleMoves.add(new FigurePosition(x+1, y-2))
        if(x+1<=7 && y+2<=7)
            possibleMoves.add(new FigurePosition(x+1, y+2))
        if(x+2<=7 && y-1>=0)
            possibleMoves.add(new FigurePosition(x+2, y-1))
        if(x+2<=7 && y+1<=7)
            possibleMoves.add(new FigurePosition(x+2, y+1))
         
        startPosition = false
    }
    
    public void checkMoves(Board board) {
        Iterator<FigurePosition> itMove = this.getPossibleMoves().iterator()
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator()
        FigurePosition nowMove
        Figure nowFigure
        
        while(itMove.hasNext()) {
            nowMove = itMove.next()
            
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                String colorFigure = nowFigure.getColor()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                if(xPos == xMove && yPos == yMove)
                    if(colorFigure == this.getColor())
                        itMove.remove()
            }
            itFigure = board.getFiguresOnBoard().iterator()
        }
        
        if(chessPiece == "bishop")
            checkBishopMoves(board)
        if(chessPiece == "rook")
            checkRookMoves(board)
        if(chessPiece == "queen") {
            checkRookMoves(board)
            checkBishopMoves(board)
        }
    }
    
    public void checkRookMoves(Board board) {
        Integer i
        Integer x = this.position.getX()
        Integer y = this.position.getY()
        Integer max
        Integer min
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator()
        Figure nowFigure
        Iterator<FigurePosition> itMove = this.getPossibleMoves().iterator()
        FigurePosition nowMove
        
        max = -1
        for(i=y-1;i>=0;i--) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(i==yPos && x==xPos)
                    if(i>max)
                        max=i
            }
            itFigure = board.getFiguresOnBoard().iterator()
        }
        for(i=max;i>=0;i--) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(i==yMove && x==xMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
        }
        
        itFigure = board.getFiguresOnBoard().iterator()
        itMove = this.getPossibleMoves().iterator()
        max = -1
        for(i=x-1;i>=0;i--) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(i==xPos && y==yPos)
                    if(i>max)
                        max=i
            }
            itFigure = board.getFiguresOnBoard().iterator()
        }
        for(i=max;i>=0;i--) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(i==xMove && y==yMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
        }
        
        itFigure = board.getFiguresOnBoard().iterator()
        itMove = this.getPossibleMoves().iterator()
        min = 8
        for(i=x+1;i<=7;i++) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(i==xPos && y==yPos)
                    if(i<min)
                        min=i
            }
            itFigure = board.getFiguresOnBoard().iterator()
        }
        for(i=min;i<=7;i++) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(i==xMove && y==yMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
        }
        
        itFigure = board.getFiguresOnBoard().iterator()
        itMove = this.getPossibleMoves().iterator()
        min = 8
        for(i=y+1;i<=7;i++) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(i==yPos && x==xPos)
                    if(i<min)
                        min=i
            }
            itFigure = board.getFiguresOnBoard().iterator()
        }
        for(i=min;i<=7;i++) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(i==yMove && x==xMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
        }
    }
    
    public void checkBishopMoves(Board board) {
        Integer x = this.position.getX()
        Integer y = this.position.getY()
        Integer maxx
        Integer maxy
        Iterator<Figure> itFigure = board.getFiguresOnBoard().iterator()
        Figure nowFigure
        Iterator<FigurePosition> itMove = this.getPossibleMoves().iterator()
        FigurePosition nowMove
        
        maxx = -1
        maxy = -1
        Integer i = x-1
        Integer j = y-1
        while(i>=0 && j>=0) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(j==yPos && i==xPos)
                    if(i>maxx) {
                        maxx=i
                        maxy=j
                    }
            }
            itFigure = board.getFiguresOnBoard().iterator()
            i--
            j--
        }
        
        i = maxx
        j = maxy
        while(i>=0 && j>=0) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(j==yMove && i==xMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
            i--
            j--
        }
        
        itFigure = board.getFiguresOnBoard().iterator()
        itMove = this.getPossibleMoves().iterator()
        
        maxx = 8
        maxy = 8
        i = x+1
        j = y+1
        while(i<=7 && j<=7) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(j==yPos && i==xPos)
                    if(i<maxx) {
                        maxx=i
                        maxy=j
                    }
            }
            itFigure = board.getFiguresOnBoard().iterator()
            i++
            j++
        }
        
        i = maxx
        j = maxy
        while(i<=7 && j<=7) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(j==yMove && i==xMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
            i++
            j++
        }
        
        maxx = 8
        maxy = 8
        i = x+1
        j = y+1
        while(i<=7 && j<=7) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(j==yPos && i==xPos)
                    if(i<maxx) {
                        maxx=i
                        maxy=j
                    }
            }
            itFigure = board.getFiguresOnBoard().iterator()
            i++
            j++
        }
        
        i = maxx
        j = maxy
        while(i<=7 && j<=7) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(j==yMove && i==xMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
            i++
            j++
        }
        
        maxx = -1
        maxy = 8
        i = x-1
        j = y+1
        while(i>=0 && j<=7) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(j==yPos && i==xPos)
                    if(j<maxy && i>maxx) {
                        maxx=i
                        maxy=j
                    }
            }
            itFigure = board.getFiguresOnBoard().iterator()
            i--
            j++
        }
        
        i = maxx
        j = maxy
        while(i>=0 && j<=7) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(j==yMove && i==xMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
            i--
            j++
        }
        
        maxx = 8
        maxy = -1
        i = x+1
        j = y-1
        while(i<=7 && j>=0) {
            while(itFigure.hasNext()) {
                nowFigure = itFigure.next()
                Integer xPos = nowFigure.getPosition().getX()
                Integer yPos = nowFigure.getPosition().getY()
                
                if(j==yPos && i==xPos)
                    if(j>maxy && i<maxx) {
                        maxx=i
                        maxy=j
                    }
            }
            itFigure = board.getFiguresOnBoard().iterator()
            i++
            j--
        }
        
        i = maxx
        j = maxy
        while(i<=7 && j>=0) {
            while(itMove.hasNext()) {
                nowMove = itMove.next()
                Integer xMove = nowMove.getX()
                Integer yMove = nowMove.getY()
                
                if(j==yMove && i==xMove)
                    itMove.remove()  
            }
            itMove = this.getPossibleMoves().iterator()
            i++
            j--
        }
        
    }
    
    
    public void generateKingMoves() {
        Integer i
        Integer j
        Integer x = position.getX()
        Integer y = position.getY()
        
        if(x-1>=0 && y-1>=0)
            possibleMoves.add(new FigurePosition(x-1, y-1))
        if(x-1>=0)
            possibleMoves.add(new FigurePosition(x-1, y))
        if(x-1>=0 && y+1<=7)
            possibleMoves.add(new FigurePosition(x-1, y+1))
        if(y-1>=0)
            possibleMoves.add(new FigurePosition(x, y-1))
        if(y+1<=7)
            possibleMoves.add(new FigurePosition(x, y+1))
        if(x+1<=7 && y-1>=0)
            possibleMoves.add(new FigurePosition(x+1, y-1))
        if(x+1<=7)
            possibleMoves.add(new FigurePosition(x+1, y))
        if(x+1<=7 && y+1<=7)
            possibleMoves.add(new FigurePosition(x+1, y+1))
            
        startPosition = false
    }
    
	
}

