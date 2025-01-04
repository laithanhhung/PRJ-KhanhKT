/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.product;
import hunglt.util.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author pc
 */
public class ProductDAO implements Serializable {

    private List<ProductDTO> products;

    public List<ProductDTO> getProducts() {
        return products;
    }

    //checkProduct
    public boolean checkProduct(String id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            //1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "Select name "
                        + "From Product "
                        + "Where id = ?";
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process result
                if (rs.next()) {
                    result = true;
                }//product are existed
            }//end connection is available
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    //searchProduct
    public void searchAllProduct() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "Select sku, name, description, quantity, price, status "
                        + "From Product";
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process result
                while (rs.next()) {
                    //Bước 12. map data
                    //get data from ResultSet

                    Integer sku = rs.getInt("sku");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    Integer quantity = rs.getInt("quantity");
                    Float price = rs.getFloat("price");
                    Boolean status = rs.getBoolean("status");
                    //set data to DTO properties
                    ProductDTO dto = new ProductDTO(sku, name, description, quantity, price, status);
                    if (this.products == null) {
                        this.products = new ArrayList<>();
                    }//accounts are unavailable
                    this.products.add(dto);
                }//traverse all ResultSet
            }//end connection is available
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public ProductDTO getProduct(String id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "Select sku, name, description, quantity, price, status From Product Where name = ?";
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, id);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process result

                //Bước 12. map data
                //get data from ResultSet
                if (rs.next()) {
                    Integer sku = rs.getInt("sku");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    Integer quantity = rs.getInt("quantity");
                    Float price = rs.getFloat("price");
                    Boolean status = rs.getBoolean("status");
                    //set data to DTO properties
                    ProductDTO dto = new ProductDTO(sku, name, description, quantity, price, status);
                    return dto;
                }
            }//end connection is available
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean updateProduct(ProductDTO dto)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            //1. connect DB
            con = DBHelper.getConnection(); // SQL+ClassNot.. bat dong nay
            if (con != null) {
                //2. create SQL String
                String sql = "Update Product "
                        + "Set name=?, description=?, quantity=?, price=?, status=? "
                        + "Where sku=?"; // prepareStm
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                // 2 ? => 2 tham so 
                stm.setString(1, dto.getName());
                stm.setString(2, dto.getDescription());
                stm.setInt(3, dto.getQuantity());
                stm.setFloat(4, dto.getPrice());
                stm.setBoolean(5, dto.isStatus());
                stm.setInt(6, dto.getSku());
                //4. Execute Query
                int affectedRows = stm.executeUpdate();
                //5. Process result
                if (affectedRows > 0) { 
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close(); // SQLExcetion bat dong nay
            }
        }
        return result;
    }
}
