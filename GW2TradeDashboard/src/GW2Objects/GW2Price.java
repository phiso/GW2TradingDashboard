/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Objects;

/**
 *
 * @author Philipp
 */
public class GW2Price {

    private final Integer gold;
    private final Integer silver;
    private final Integer copper;

    public GW2Price(Integer gold, Integer silver, Integer copper) {
        this.gold = gold;
        this.silver = silver;
        this.copper = copper;
    }

    public Integer getGold() {
        return gold;
    }

    public Integer getSilver() {
        return silver;
    }

    public Integer getCopper() {
        return copper;
    }
    
    @Override
    public String toString(){
        return "G:"+gold.toString()+" S:"+silver.toString()+" C:"+copper.toString();
    }

}
