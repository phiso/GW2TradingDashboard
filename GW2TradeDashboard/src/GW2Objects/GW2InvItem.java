/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Objects;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 *
 * @author Philipp
 */
public class GW2InvItem extends GW2Item {

    private Integer count;
    private Integer charges;

    public GW2InvItem(String id, Integer count, Integer charges) throws URISyntaxException, IOException {
        super(id);
        this.count = count;
        this.charges = charges;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

}
