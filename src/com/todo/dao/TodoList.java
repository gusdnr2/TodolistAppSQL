package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.DbConnector;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn=DbConnector.getConnection();
	}
	
	/* public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)"+" values(?,?,?,?,?);";
			int records = 0;
			
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String cate = st.nextToken();
				String title = st.nextToken();
				String memo = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, memo);
				pstmt.setString(3, cate);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count = pstmt.executeUpdate();
				if(count>0) records++;
				pstmt.close();
			}
			System.out.println(records+" 개의 레코드를 읽었습니다.");
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date)"+
	" values (?,?,?,?,?)";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?,  due_date=?"+" where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6,t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				TodoItem t = new TodoItem(title,memo,cate,due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getList(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
	    try {
			String sql = "select * from list where title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
			    int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				list.add(new TodoItem(id,title,memo,current_date,cate,due_date));
				}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	public ArrayList<TodoItem> getOrderedList(String orderby,int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select * from list order by "+orderby;
			if (ordering == 0) 
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				list.add(new TodoItem(id,title,memo,current_date,cate,due_date));
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "select distinct category from list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String cate = rs.getString("category");
				list.add(cate);
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "select * from list where category=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				list.add(new TodoItem(id,title,memo,current_date,cate,due_date));
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	

	public Boolean isDuplicate(String title) {
		PreparedStatement pstmt;
		int count=0;
		try {
		 String sql= "select count(id) from list where title = ?;";
		 pstmt=conn.prepareStatement(sql);
		 pstmt.setString(1, title);
		 ResultSet rs = pstmt.executeQuery();
		 rs.next();
		 count = rs.getInt("count(id)");
		 pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (count>0)
			return true;
		else return false;
	}
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt=conn.createStatement();
			String sql = "select count(id) from list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public void DeleteData() {
		try {
			Statement stat = conn.createStatement();
			String sql = "delete from list;";
			int cnt = stat.executeUpdate(sql);
			if (cnt > 0)
				System.out.println("데이터 삭제 완료.");
			else 
				System.out.println("[Error] 데이터 삭제중 오류 발생.");
			stat.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

}
	public void ResetSequence() {
		try {
			Statement stat= conn.createStatement();
			String sql = "update sqlite_sequence set seq=0 where name='list';";
			int cnt = stat.executeUpdate(sql);
			if (cnt > 0)
				System.out.println("순서 초기화 완료");
			else 
				System.out.println("[Error] 초기화 중 오류 발생.");
			stat.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
