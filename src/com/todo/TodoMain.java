package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		String filename = "TodoList.txt";
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, filename);
		Menu.displaymenu();
		do {
			Menu.Prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("=== 이름 순서 정렬 ===");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("=== 이름 역순 정렬 ===");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;

			case "exit":
				quit = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			default:
				System.out.println("정확한 명령어를 입력해 주세요. \n명령어 목록이 궁금하시면 help 명령어를 사용해 주세요.");
				break;
			}
			
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		TodoUtil.saveList(l, filename);
	}
}
