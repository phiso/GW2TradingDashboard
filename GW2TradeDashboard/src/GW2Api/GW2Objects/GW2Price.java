/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

/**
 *
 * @author Philipp
 */
public class GW2Price {
    private final Integer gold;
    private final Integer silver;
    private final Integer copper;
    private final Integer rawPrice;

    public GW2Price(Integer price) {                
        gold = Math.floorDiv(price, 10000);
        Integer tmp = price % 10000;
        silver = Math.floorDiv(tmp, 100);
        copper = tmp % 100;        
        rawPrice = price;
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
    
    public Integer getRawPrice(){
        return rawPrice;
    }
    
    @Override
    public String toString(){
        if (gold == 0 && silver == 0 && copper == 0){
            return "No Price";
        }
        return "G:"+gold.toString()+" S:"+silver.toString()+" C:"+copper.toString();
    }
}
