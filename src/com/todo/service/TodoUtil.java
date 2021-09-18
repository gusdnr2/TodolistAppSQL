package com.todo.service;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== 항목 추가 ===\n"
				+ "제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("성공적으로 추가하였습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("=== 항목 삭제 ===\n"
				+ "삭제 할 항목의 제목 > ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("성공적으로 삭제 되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "=== 항목 수정 ===\n"
				+ "수정 할 항목의 제목 > ");
		String title = sc.next().trim();
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
		sc.nextLine();
		System.out.print("새로운 내용을 입력해 주세요 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("항목이 업데이트되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
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
				String name = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				TodoItem t = new TodoItem(name,desc,date);
				l.addItem(t);
			}
			r.close();
			System.out.println(count+" 개의 항목을 읽었습니다.");
		} catch (FileNotFoundException e) {
			System.out.println("TodoList.txt 파일이 없습니다.");
		} catch (IOException e) {
			System.out.println("파일을 읽지 못했습니다.");
		}
		
	}
}
