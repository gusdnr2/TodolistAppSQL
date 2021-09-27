package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== 항목 추가 ===\n"
				+ "제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		System.out.print("카테고리 > ");
		category = sc.next();
		
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.println("마감 일자 > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("성공적으로 추가하였습니다.");
	}

	public static void deleteItem(TodoList l) {
		try {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== 항목 삭제 ===\n"
				+ "삭제 할 항목의 번호 > ");
		int title = Integer.parseInt(sc.next());
		
		for (TodoItem item : l.getList()) {
			if (title == l.indexOf(item)+1) {
				l.deleteItem(item);
				System.out.println("성공적으로 삭제 되었습니다.");
				break;
			}
		}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해 주세요!");
		}
	}


	public static void updateItem(TodoList l) {
		try {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n"
				+ "=== 항목 수정 ===\n"
				+ "수정 할 항목의 번호 > ");
		int title = Integer.parseInt(sc.next());		
		if (!l.isDuplicate(title)) {
			System.out.println("항목이 없습니다!");
			return;
		}

		System.out.print("새로운 제목을 입력해 주세요 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		System.out.print("새로운 카테고리를 입력해 주세요 > ");
		String new_cate = sc.next().trim();
		sc.nextLine();
		System.out.print("새로운 내용을 입력해 주세요 > ");
		String new_description = sc.nextLine().trim();
		System.out.print("새로운 마감 일자를 입력해 주세요 > ");
		String new_ddat = sc.next().trim();
		
		for (TodoItem item : l.getList()) {
			if (l.isDuplicate(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_cate, new_ddat);
				l.addItem(t);
				System.out.println("항목이 업데이트되었습니다.");
			}
		} 
		} catch(NumberFormatException e) {
			System.out.println("숫자만 입력해 주세요!");
			
		}
			
		}

	
	public static void find(TodoList l,String keyword) {
		int counter = 0;
		for (TodoItem item : l.getList()) {
			String itemname = item.getTitle();
			String descname = item.getDesc();
			if (itemname.contains(keyword)) {
				System.out.println((l.indexOf(item)+1)+". "+item.toString());
				counter++;
			}
			else if (descname.contains(keyword)) {
				System.out.println((l.indexOf(item)+1)+". "+item.toString());
				counter++;
			}
		}
		if (counter == 0) {
			System.out.println("검색 결과 항목을 찾지 못했습니다.");
			return;
		} else {
			System.out.println("총 "+counter+"개의 항목을 찾았습니다.");		
		}
		}
	

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 "+l.sizeOf()+"개]");
		for (TodoItem item : l.getList()) {
			System.out.println((l.indexOf(item)+1)+". "+item.toString());
		}
	}
	
	public static void saveList(TodoList l,String filename) {
		try {
			Writer w  = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
			System.out.println("데이터 입력 완료.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l,String filename) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			int count = 0;
			String eachline;
			while ((eachline = r.readLine()) != null) {
				count++;
				StringTokenizer st = new StringTokenizer(eachline,"##");
				String cate = st.nextToken();
				String name = st.nextToken();
				String desc = st.nextToken();
				String ddat = st.nextToken();
				String date = st.nextToken();
				TodoItem t = new TodoItem(name,desc,date,cate,ddat);
				l.addItem(t);
			}
			r.close();
			System.out.println(count+" 개의 항목을 읽었습니다.");
		} catch (FileNotFoundException e) {
			System.out.println("TodoList.txt 파일이 없습니다.");
		} catch (IOException e) {
			System.out.println("파일을 읽지 못했습니다.");
		} catch (NoSuchElementException e) {
			System.out.println("파일을 읽지 못했습니다.");
		}
}
}
