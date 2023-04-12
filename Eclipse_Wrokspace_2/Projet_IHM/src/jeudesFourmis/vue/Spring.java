/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudesFourmis.vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Une classe ressort pour espacer dans les Box
 * @author bergey
 */
public class Spring extends Region{
    
    /**
     * Construit un ressort
     */
    public Spring(){
        this.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(this,Priority.ALWAYS); 
        this.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(this, Priority.ALWAYS);
    }
    
}
