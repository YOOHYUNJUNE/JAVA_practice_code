package codingtest;

import java.util.Scanner;

public class 체스판 {
	public static void main(String[] args) {
		
		// m 가로 * n 세로 (>=8) 체스판 W,B 입력 
		// WB 또는 BW가 아니면 +1
		
		// 체스판 입력
		Scanner sc = new Scanner(System.in);
		
		System.out.println("체스판 입력 : ");
		String nm = sc.nextLine();
		String[] parts = nm.split(" ");
		
		int n = Integer.parseInt(parts[0]); // 세로
		int m = Integer.parseInt(parts[1]); // 가로?
		
		// 배열
		char[][] arr = new char[n][m];
		
		
		
		
		
		
		
		
	}
}
