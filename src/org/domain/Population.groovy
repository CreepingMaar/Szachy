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
class Population {
    
    def players = new Player [50]
	
    Population() {
        for(int i = 0; i < players.length; i++) {
            players[i] = new Player()
            players[i].setAverageFitness(players.length-i)
        }
    }
    
    public void bubbleSort() {
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < players.length - 1; j++) {
                if (players[j].getAverageFitness() > players[j+1].getAverageFitness()) {
                    Player temp;
                    temp = players[j+1];
                    players[j+1] = players[j];
                    players[j] = temp;
                }
            }
        }
    }
    
}

