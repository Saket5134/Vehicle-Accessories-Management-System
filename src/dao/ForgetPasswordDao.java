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
import user.ForgetPassword;

public class ForgetPasswordDao {

    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    public ForgetPasswordDao() {
        try {
            this.con = MyConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ForgetPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isEmailExist(String email) {
        try {
            ps = con.prepareStatement("select * from users where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                ForgetPassword.jTextField11.setText(rs.getString(7));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Email Address Doesn't Exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgetPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean getAns(String email, String newAns) {
        try {
            ps = con.prepareStatement("select * from users where uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString(8);
                if (newAns.equals(oldAns)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Security Answer Didn't Match");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgetPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    public boolean validateSecurityAnswer(String email, String newAns) {
        try {
            ps = con.prepareStatement("SELECT uans FROM users WHERE uemail = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                String oldAns = rs.getString("uans");
                return newAns.equalsIgnoreCase(oldAns);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgetPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    //Set New Password
    public void setPassword(String email, String pass)
    {
        String sql = "update users set upassword = ? where uemail = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, email);
            if(ps.executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Password Successfully Changed!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ForgetPasswordDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
