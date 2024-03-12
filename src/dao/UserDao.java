package dao;

import connection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class UserDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    
    public UserDao() {
        try {
            this.con = MyConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Get User Table Max Row
    public int getMaxRow() {
        int row = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select max(uid) from users");
            while (rs.next()) {
                row = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return row + 1;
    }

    //Check If Email Already Exists
    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("select * from users where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //Check Mobile Number Already Exists
    public boolean isMobileExist(String mobile) {
        try {
            ps = con.prepareStatement("select * from users where umobile = ?");
            ps.setString(1, mobile);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //Insert Data Into User Table
    public void insert(int id, String username, String email, String pass, String mobile, String address, String seq,
            String ans) {
        String sql = "insert into users values (?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, pass);
            ps.setString(5, mobile);
            ps.setString(6, address);
            ps.setString(7, seq);
            ps.setString(8, ans);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User Added Successfully !");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Update User Data
    public void update(int id, String username, String email, String pass, String mobile, String address, String seq,
            String ans) {
        String sql = "update users set uname = ?, uemail = ?, upassword = ?, umobile = ?, uaddress = ?, usecqus = ?, uans = ? where uid = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, pass);
            ps.setString(4, mobile);
            ps.setString(5, address);
            ps.setString(6, seq);
            ps.setString(7, ans);
            ps.setInt(8, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "User Data Successfully Updated!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Delete User
    public void delete(int id)
    {
        int x = JOptionPane.showConfirmDialog(null, "Are You Sure To Delete This Acccount ?", "Delete Account",JOptionPane.OK_CANCEL_OPTION,0);
        if(x == JOptionPane.OK_OPTION)
        {
            try {
                ps = con.prepareStatement("delete from users where uid = ?");
                ps.setInt(1, id);
                if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Account Deleted Successfully !");
            }
            } catch (SQLException ex) {
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //Get User Value
    public String[] getUserValue(int id) {
        String[] value = new String[8];
        try {
            ps = con.prepareStatement("select * from users where uid = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                value[0] = rs.getString(1);
                value[1] = rs.getString(2);
                value[2] = rs.getString(3);
                value[3] = rs.getString(4);
                value[4] = rs.getString(5);
                value[5] = rs.getString(6);
                value[6] = rs.getString(7);
                value[7] = rs.getString(8);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return value;
    }

    //Get User Id
    public int getUserId(String email) {
        int id = 0;
        try {
            ps = con.prepareStatement("select uid from users where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;
    }
    
    //Get User Data
    public void getUsersData(JTable table, String search)
    {
        String sql = "select * from users where concat(uid, uname, uemail) like ? order by uid desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+search +"%");
            rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object [] row;
            while(rs.next())
            {
                row = new Object[8];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
