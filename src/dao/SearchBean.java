
package dao;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

@ManagedBean
public class SearchBean {

    private List<Entry> entries = null;
    private String fromDate, toDate, text;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getText() {
        return text;
    }
   
    public void setText(String text) {
        this.text = text;
    }
    
    public void search(ActionEvent evt) {
       entries =  EntryDAO.searchEntries( Util.getUname(),fromDate,toDate,text);
    }
    
    public List<Entry> getEntries() {
        return entries ;
    }
    
}
