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
public class FigurePosition {
    
    Integer x
    Integer y
    Integer localX
    Integer localY
    Figure beatenPiece
    
    FigurePosition(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
	
}

