/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package similarcosin;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author phucdk
 */
public class GeneralVector {
    private int dimention;
    private List<Float> listValue;

    public GeneralVector(int dimention) {
        this.dimention = dimention;
        this.listValue = new ArrayList<>();
    }
    
    
    

    public int getDimention() {
        return dimention;
    }

    public void setDimention(int dimention) {
        this.dimention = dimention;
    }

    public List<Float> getListValue() {
        return listValue;
    }

    public void setListValue(List<Float> listValue) {
        this.listValue = listValue;
    }
}
