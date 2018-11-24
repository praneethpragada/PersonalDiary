package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;
import oracle.jdbc.rowset.OracleCachedRowSet;

public class EntryDAO {

    public static boolean add(Entry e) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "insert into diaryentries values(entryid_seq.nextval,?,?,?,?)");
            ps.setString(1, e.getUname());
            ps.setString(2, e.getDate());
            ps.setString(3, e.getTime());
            ps.setString(4, e.getText());
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("EntryDAO->add() : " + ex.getMessage());
            return false;
        }
    }

    public static List<Entry> getRecentEntries(String uname) {
        try (Connection con = Database.getConnection()) {
            CachedRowSet crs = new OracleCachedRowSet();
            crs.setCommand(
                    "select * from (select * from diaryentries where uname = ? order by entrydate desc, entrytime desc) where rownum <= 20");
            crs.setString(1, uname);
            crs.execute(con);
            ArrayList<Entry> entries = new ArrayList<>();
            while (crs.next()) {
                Entry e = new Entry();
                e.setId(crs.getString("entryid"));
                e.setDate(crs.getString("entrydate"));
                e.setTime(crs.getString("entrytime"));
                e.setText(crs.getString("entrytext"));
                entries.add(e);
            }
            return entries;
        } catch (Exception ex) {
            System.out.println("EntryDAO.getRecentEntries() -- > " + ex.getMessage());
            return null;
        }
    }


     public static Entry getEntry(String id) {
       try (Connection con = Database.getConnection()) {
            CachedRowSet crs = new OracleCachedRowSet();
            crs.setCommand("select * from diaryentries where entryid = ?");
            crs.setString(1,id);
            crs.execute(con);
            if ( crs.next()) {
                Entry e = new Entry();
                e.setId(crs.getString("entryid"));
                e.setDate(crs.getString("entrydate"));
                e.setTime(crs.getString("entrytime"));
                e.setText(crs.getString("entrytext"));
                return e;
            }
            return null;
        } catch (Exception ex) {
            System.out.println("EntryDAO.getEntry() -- > " + ex.getMessage());
            return null;
        }
     }

     
    public static boolean update(Entry e) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                ("update diaryentries set entrytext= ? , entrydate=? , entrytime=? where entryid=?");
            ps.setString(1, e.getText());
            ps.setString(2, e.getDate());
            ps.setString(3, e.getTime());
            ps.setString(4, e.getId());
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("EntryDAO-> update() : " + ex.getMessage());
            return false;
        }
    }

    public static boolean delete(String id) {
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement
                 ("delete from diaryentries where entryid=?");
            ps.setString(1,id);
            int count = ps.executeUpdate();
            return count == 1;
        } catch (Exception ex) {
            System.out.println("EntryDAO-> delete() : " + ex.getMessage());
            return false;
        }
    }

    public static List<Entry> searchEntries(String uname, String fromDate, String toDate, String text) {
        try (Connection con = Database.getConnection()) {
            CachedRowSet crs = new OracleCachedRowSet();
            // construct condition
            String cond = " uname = '" + uname + "'";
            
            if ( fromDate.length() > 0 )
                  cond += " and entrydate >= '" +  fromDate + "'";
            
            if ( toDate.length() > 0 )
                  cond += " and entrydate <= '" +  toDate + "'";
            
            if ( text.length() > 0 )
                  cond += " and  upper(entrytext) like '%" +  text.toUpperCase() + "%'";
            
            crs.setCommand("select * from diaryentries  where " + cond + " order by entrydate desc, entrytime desc");
            crs.execute(con);
            ArrayList<Entry> entries = new ArrayList<>();
            while (crs.next()) {
                Entry e = new Entry();
                e.setId(crs.getString("entryid"));
                e.setDate(crs.getString("entrydate"));
                e.setTime(crs.getString("entrytime"));
                e.setText(crs.getString("entrytext"));
                entries.add(e);
            }
            return entries;
        } catch (Exception ex) {
            System.out.println("EntryDAO.searchEntries() -- > " + ex.getMessage());
            return null;
        }
    }

}
