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
    
    def players = new Player [20]
	
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
    void setValues(Evaluation values, Integer a, Integer b) {
        for(int i=0; i<8; i++)
            for(int j=0; j<8; j++)
                values.blackPawnValue[i][j] = players[a].genotype[i*8+j]
        for(int i=8; i<16; i++)
            for(int j=8; j<16; j++)
                values.blackKnightValue[i-8][j-8] = players[a].genotype[i*8+j-8]
        for(int i=16; i<24; i++)
            for(int j=16; j<24; j++)
                values.blackQueenValue[i-16][j-16] = players[a].genotype[i*8+j-16]
        for(int i=24; i<32; i++)
            for(int j=24; j<32; j++)
                values.blackBishopValue[i-24][j-24] = players[a].genotype[i*8+j-24]
        for(int i=32; i<40; i++)
            for(int j=32; j<40; j++)
                values.blackKingValue[i-32][j-32] = players[a].genotype[i*8+j-32]
        for(int i=40; i<48; i++)
            for(int j=40; j<48; j++)
                values.blackRookValue[i-40][j-40] = players[a].genotype[i*8+j-40]
        for(int i=0; i<8; i++)
            for(int j=0; j<8; j++)
                values.whitePawnValue[i][j] = players[b].genotype[i*8+j]
        for(int i=8; i<16; i++)
            for(int j=8; j<16; j++)
                values.whiteKnightValue[i-8][j-8] = players[b].genotype[i*8+j-8]
        for(int i=16; i<24; i++)
            for(int j=16; j<24; j++)
                values.whiteQueenValue[i-16][j-16] = players[b].genotype[i*8+j-16]
        for(int i=24; i<32; i++)
            for(int j=24; j<32; j++)
                values.whiteBishopValue[i-24][j-24] = players[b].genotype[i*8+j-24]
        for(int i=32; i<40; i++)
            for(int j=32; j<40; j++)
                values.whiteKingValue[i-32][j-32] = players[b].genotype[i*8+j-32]
        for(int i=40; i<48; i++)
            for(int j=40; j<48; j++)
                values.whiteRookValue[i-40][j-40] = players[b].genotype[i*8+j-40]
    }
    
}

