/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.registration;

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
public class RegistrationDAO implements Serializable {

    public RegistrationDTO checkLogin(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result = null;

        try {
            //1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "Select lastname, isAdmin "
                        + "From Registration "
                        + "Where username = ? "
                        + "And password = ?";
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process result
                if (rs.next()) {
                    //map
                    //get
                    String fullName = rs.getString("lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    //set
                    result = new RegistrationDTO(username, "", fullName, role);
                }//username and password are existed
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

    private List<RegistrationDTO> accounts;

    public List<RegistrationDTO> getAccounts() {
        return accounts;
    }

    public void searchLastname(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            //1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "Select username, password, lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                //4. Execute Query
                rs = stm.executeQuery();
                //5. Process result
                while (rs.next()) {
                    //Bước 12. map data
                    //get data from ResultSet

                    String username = rs.getString("username");// lấy trên câu lệnh copy xuống
                    String password = rs.getString("password");// lấy trên câu lệnh copy xuống
                    String fullname = rs.getString("lastname");// lấy trên câu lệnh copy xuống
                    Boolean role = rs.getBoolean("isAdmin");// lấy trên câu lệnh copy xuống
                    //set data to DTO properties
                    RegistrationDTO dto = new RegistrationDTO(username, password, fullname, role);
                    if (this.accounts == null) {
                        this.accounts = new ArrayList<>();
                    }//accounts are unavailable
                    this.accounts.add(dto);
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

    public boolean deleteAccount(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            //1. connect DB
            con = DBHelper.getConnection();
            if (con != null) {
                //2. create SQL String
                String sql = "Delete From Registration "
                        + "Where username = ?";
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                //4. Execute Query
                int affectedRows = stm.executeUpdate();
                //5. Process result
                if (affectedRows > 0) {
                    result = true;
                }
            }//end connection is available
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
    public boolean updateAccount(String username, String password, String isAdmin)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;

        try {
            //1. connect DB
            con = DBHelper.getConnection(); // SQL+ClassNot.. bat dong nay
            if (con != null) {
                //2. create SQL String
                String sql = "Update registration "
                        + "Set password= ?, isAdmin = ? "
                        + "Where username= ?"; // prepareStm
                //3. create Statement Object
                stm = con.prepareStatement(sql);
                // 2 ? => 2 tham so 
                stm.setString(1, password);
                if (isAdmin != null && isAdmin.equals("ON")) {
                    stm.setString(2, "1");
                } else {
                    stm.setString(2, "0");
                }
                stm.setString(3, username);
                // loi -> truyen it thamm so, hoac nhieu tham so honw
                //4. Execute Query
                int affected = stm.executeUpdate(); // ko nap vi dg prepare ma chi nap 1 lan duuy nhat cho 3. do
                // nap lai tu dau la Statement roi
                //5. Process result
                if (affected > 0) { // Dung if (1 dong) while (nhieu dong)
                    result = true;
                }// username va password are existed
            } // end connection is available
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
