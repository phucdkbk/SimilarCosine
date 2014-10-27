/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package similarcosin;

/**
 *
 * @author phucdk
 */
public class ResultObject implements Comparable<ResultObject> {

    private String document;
    private Float similarCosin;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Float getSimilarCosin() {
        return similarCosin;
    }

    public void setSimilarCosin(Float similarCosin) {
        this.similarCosin = similarCosin;
    }

    @Override
    public int compareTo(ResultObject o) {
        if (this.similarCosin <= o.similarCosin) {
            return 1;
        } else {
            return -1;
        }
    }

}
