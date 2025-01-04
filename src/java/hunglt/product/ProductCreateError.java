/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.product;

/**
 *
 * @author pc
 */
public class ProductCreateError {
    private String error;

    public ProductCreateError() {
    }

    public ProductCreateError(String error) {
        this.error = error;
    }
    
    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }
}
