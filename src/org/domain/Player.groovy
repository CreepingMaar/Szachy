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
    Float averageFitness
    Float probability
    Float distribution
    
    Player() {
        fitness = 0
        averageFitness = 0
        color = ""
        ai = true
        probability = 0
        for(int i = 0; i < 384; i++) {
            Random random = new Random()
            genotype[i] = random.nextInt(101)-50
        }
    }

    public void addFitness(Integer fitness) {
        this.fitness += fitness
    }
}

