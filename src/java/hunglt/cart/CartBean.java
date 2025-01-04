/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.cart;

import hunglt.product.ProductDTO;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;

/**
 *
 * @author pc
 */
public class CartBean implements Serializable {

    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }

    public void addIteamToCart(ProductDTO item) throws SQLException, NamingException {
        if (item == null) {
            return;
        }

        //1. check ngăn chứa(items) có tồn tại hay chưa
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        //2. check existed item để tăng số lượng thôi
        int quantity = 1;

        if (item.getQuantity() > 0 && item.isStatus() == true) {
            if (this.items.containsKey(item.getName())) {
                quantity = this.items.get(item.getName()) + 1;
            }
            //3. drop item to items
            this.items.put(item.getName(), quantity);
        }
    }

    public void removeItemFromCart(String item) {
        if (item == null) {
            return;
        }
        if (item.trim().isEmpty()) {
            return;
        }
        //1. check existed items: ko có phải 
        if (this.items == null) {
            return;
        }
        //2. check existed item
        if (this.items.containsKey(item)) {
            //3. remove item from items
            this.items.remove(item);
            if (this.items.isEmpty()) {
                this.items = null;// làm vậy thì ko cần check null và set size
            }
        }
        //về làm thêm xóa từng cái do thầy chỉ làm xóa toàn bộ item
    }
}
