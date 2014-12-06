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
class Player {
    
    Boolean ai
    def genotype = new Integer [384]
    String color
    Integer fitness
    Integer averageFitness
    
    Player() {
                
        for(int i = 0; i < 384; i++) {
            Random random = new Random()
            genotype[i] = random.nextInt(101)-50
        }
    }
    
    
	
}

