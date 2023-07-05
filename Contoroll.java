package final1;

import java.util.Scanner;

public class Contoroll {
	
//	検索操作の時
//	授業説明の時は特徴量を選べるようにするといわれたが
//	レポートではその必要性を感じない。
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		//create database object 
		Database database = new SearchDB(); 
		
		// select operation
		do {
			System.out.print("1:計算 2:検索 3:終了 : ");
			int input = scan.nextInt();
			int number;
			
			if(input == 1) {
				System.out.print("学籍番号の下1桁を入力: ");
				number = scan.nextInt();
				if(number > 10 || number < 0) continue;
				
				System.out.println("計算します…");
				database.pickupImage(number); 
				
			}else if(input == 2) {
				System.out.print("学籍番号の下2桁を入力: ");
				number = scan.nextInt();
				if(number<0 || number > 100) continue;
				
				System.out.println("検索します…");
				database.similerSearch(number); 
				
				
			}else if(input == 3){
				System.out.println("終了します。");
				break;
			}
			
		}while(true);

	}
}
