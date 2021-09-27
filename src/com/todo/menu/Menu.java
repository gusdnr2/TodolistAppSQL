package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("=== TodoListApp 어플리케이션 명령어 목록 ===");
        System.out.println("1. add - 새로운 과제 추가");
        System.out.println("2. del - 기존 과제 삭제");
        System.out.println("3. edit - 기존 과제 수정");
        System.out.println("4. ls - 과제 목록 보기");
        System.out.println("5. ls_name_asc - 과제 이름 순서 정렬");
        System.out.println("6. ls_name_desc - 과제 이름 역순 정렬");
        System.out.println("7. ls_date - 과제 날짜 순서 정렬");
        System.out.println("8. find keyword - keyword가 제목, 내용에 들어간 과제 검색");
        System.out.println("9. exit - 프로그램 종료하기");
    }
    public static void Prompt()
    {
        System.out.println();
    	System.out.print("원하시는 명령어를 입력해주세요 > ");
    }
}
