package dao;

import dao.EntryDAO;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@RequestScoped
public class Entry {

	private List<Entry> entries = null;
	private String id, text, time, message, uname, date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Entry() {

		Calendar c = Calendar.getInstance();
		time = String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
		date = String.format("%4d-%02d-%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DAY_OF_MONTH));

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	@Override
	public String toString() {
		return "Entry{" + "id=" + id + ", text=" + text + ", time=" + time + ", message=" + message + ", uname=" + uname
				+ ", date=" + date + '}';
	}

	public String getPreviewText() {
		return text.length() < 50 ? text : text.substring(0, 50) + " [More ...]";
	}

	public String load() {
		Entry e = EntryDAO.getEntry(id);
		this.date = e.date;
		this.time = e.time;
		this.id = e.id;
		this.text = e.text;
		this.uname = e.uname;
		return null; // no change in navigation
	}

	public void add(ActionEvent evt) {
		uname = Util.getUname();
		boolean done = EntryDAO.add(this);
		if (done) {
			message = "Successfully added entry!";
			text = "";
		} else
			message = "Sorry! Could not add entry!";
	}

	public void update(ActionEvent evt) {
		uname = Util.getUname();
		boolean done = EntryDAO.update(this);
		if (done) {
			message = "Successfully updated entry!";
		} else
			message = "Sorry! Could not update entry!";
	}

	public void delete(ActionEvent evt) {
		EntryDAO.delete(Util.getParameter("id"));
	}

	public List<Entry> getRecentEntries() {
		if (entries == null) {
			entries = EntryDAO.getRecentEntries(Util.getUname());
		}
		return entries;
	}

}
