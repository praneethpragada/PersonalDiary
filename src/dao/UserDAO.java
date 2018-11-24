package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static boolean register(User u) {

        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = 
                    con.prepareStatement("insert into users values(?,?,?,?,?,sysdate)");
            ps.setString(1, u.getUname());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullname());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getMobile());
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("UserDAO->register() : " + ex.getMessage());
            return false;
        }
    }

    public static User login(String username, String password) {

        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                ("select * from users where uname = ? and password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUname(username);
                u.setPassword(password);
                u.setFullname(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setMobile(rs.getString("mobile"));
                return u;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("UserDAO-> login() : " + ex.getMessage());
            return null;
        }
    }

    public static boolean changePassword(String uname, String password, String newPassword) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                 ("update users set password = ? where uname = ? and password = ?");
            ps.setString(1,newPassword);
            ps.setString(2,uname);
            ps.setString(3,password);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("UserDAO-> changePassword() : " + ex.getMessage());
            return false;
        }

    }

    public static User getUser(String uname, String email) {

        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement
               ("select * from users where uname = ? or email = ?");
            ps.setString(1, uname);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUname(uname);
                u.setPassword(rs.getString("password"));
                u.setFullname(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setMobile(rs.getString("mobile"));
                return u;
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.out.println("UserDAO-> getUser() : " + ex.getMessage());
            return null;
        }
    }

}
