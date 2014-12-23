/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.domain

import javax.swing.JTable
import org.gui.*;

/**
 *
 * @author Marek
 */
public class Population {

    Integer length = 14
    def players = new Player[length]
	
    Population() {
        for(int i = 0; i < length; i++) {
            players[i] = new Player()
        }
    }

    public void averageScore() {
        for(int i = 0; i < length; i++) {
            players[i].setAverageFitness(players[i].getFitness()/14)
            //players[i].setAverageFitness(length-i)
        }
    }

    public void probability() {
        Integer sum = ((1+length)*length)/2
        Float distribution = 0

        for(int i = 0; i < length; i++) {
            players[i].setProbability((length-i)/sum)
            distribution += players[i].getProbability()
            players[i].setDistribution(distribution)
        }
    }

    public Integer returnIndex(Float number) {
        for(int i = 0; i < length; i++)
            if(number < players[i].getDistribution())
                return i
    }

    public void distribution() {
        Random random = new Random()
        def nextPlayers = new Player[length]
        for(int i = 0; i < length; i++) {
            nextPlayers[i] = players[returnIndex(random.nextFloat())]
        }
        players = nextPlayers
    }

    public void crossoverIndex() {
        Random random = new Random()
        Integer index
        List<Integer> indexList = new ArrayList<Integer>()
        for(int i = 0; i < length; i++) {
            index = random.nextInt(100)+1
            if(index < 60)
                indexList.add(i)
        }
        while(1) {
            if (indexList.size() % 2 == 1)
                indexList.add(returnIfNotEven(indexList))
            else
                break
        }
        crossover(indexList)
    }

    public void crossover(List<Integer> indexList) {
        Integer first, second, firstParent, secondParent
        while(indexList) {
            Random random = new Random()
            first = random.nextInt(indexList.size())
            firstParent = indexList.getAt(first)
            indexList.remove(first)
            second = random.nextInt(indexList.size())
            secondParent = indexList.getAt(second)
            indexList.remove(second)
            crossParents(firstParent, secondParent)
        }
    }

    public void crossParents(Integer first, Integer second) {
        Player firstPlayer, secondPlayer
        def halfGenotype, secondHalfGenotype
        firstPlayer = players[first]
        secondPlayer = players[second]
        halfGenotype = firstPlayer.getGenotype().take(192)
        secondHalfGenotype = secondPlayer.getGenotype().take(192)
        for(int i = 0; i < 192; i++) {
            firstPlayer.getGenotype().putAt(i, secondHalfGenotype[i])
            secondPlayer.getGenotype().putAt(i, halfGenotype[i])
        }

    }

    public void mutation() {
        Random random = new Random()
        Integer index
        for(int i = 0; i < length; i++)
            for(int j = 0; j < 384; j++) {
                index = random.nextInt(100)+1
                if(index == 1)
                    players[i].getGenotype().putAt(j, random.nextInt(101)-50)
            }



    }

    public Integer returnIfNotEven(List<Integer> indexList) {
        Random random = new Random()
        Integer index
        for(int i = 0; i < length; i++) {
            index = random.nextInt(100)+1
            if(index < 60) {
                if(!isInList(indexList, i))
                    return i
            }
        }
    }

    public Boolean isInList(List<Integer> indexList, Integer i) {
        Iterator<Integer> it = indexList.iterator()
        while(it.hasNext()) {
            Integer now = it.next()
            if(i.equals(now))
                return true
        }
        return false
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

    public Player getPlayer(int i) {
        return players[i]
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

    void setClassic(Evaluation values) {
        values.blackPawnValue = values.getPawnValue()
        values.blackKnightValue = values.getKnightValue()
        values.blackQueenValue = values.getQueenValue()
        values.blackBishopValue = values.getBishopValue()
        values.blackKingValue = values.getKingValue()
        values.blackRookValue = values.getKingValue()
        values.whitePawnValue = values.getPawnValue()
        values.whiteKnightValue = values.getKnightValue()
        values.whiteQueenValue = values.getQueenValue()
        values.whiteBishopValue = values.getBishopValue()
        values.whiteKingValue = values.getKingValue()
        values.whiteRookValue = values.getKingValue()
    }

    public void playPopulation(Board newBoard, Evaluation value, JTable table, FigurePosition globalMove) {
        for(int i = 0; i < getLength(); i++) {
            for(int j = i + 1; j < getLength(); j++) {
                Integer moveCounter = 0;
                newBoard.resetBoard();
                for(int l = 0; l < newBoard.getWidth(); l++)
                    for(int k = 0; k < newBoard.getHeight(); k++)
                        table.setValueAt(null, k, l);
                Iterator<Figure> it = newBoard.getFiguresOnBoard().iterator()
                Figure now
                while(it.hasNext()) {
                    now = it.next()
                    Integer x = now.getPosition().getX();
                    Integer y = now.getPosition().getY();
                    String imagePath = now.getImagePath();
                    table.setValueAt(imagePath, y, x);
                }
                Integer result = newBoard.playAi(globalMove, table, moveCounter, this, value);
                if(result == 0) {
                    getPlayer(i).addFitness(1);
                    getPlayer(j).addFitness(1);
                } else if(result == 1) {
                    getPlayer(j).addFitness(2);
                } else if(result == -1) {
                    getPlayer(i).addFitness(2);
                }
            }
        }
        averageScore();
        bubbleSort();
        probability();
        distribution();
        crossoverIndex();
        mutation();
    }
    
}

