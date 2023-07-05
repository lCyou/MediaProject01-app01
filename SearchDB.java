package final1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author yakum
 * 課題1で使われる検索要素
 * ・学籍番号の下一桁と画像ファイル名の下一桁が一致するものを出力
 * ・学籍番号の下二桁とファイル名が一致する画像を元に類似画像検索
 */
public class SearchDB extends Database {

	public Map<Double, Image> similarity = new TreeMap<>();

	public SearchDB() {
		super();
	}

	@Override
	public void similerSearch(int number) {
		// TreeMapを初期化する
		similarity.clear();

		// ファイルがあるかどうか調べる
		Image input = null;
		for (Image i : database) {
			if (i.name.equals(Integer.valueOf(number).toString())) {
				input = i;
			}
		}
		if (input == null) {
			System.out.println("ファイルが見つかりません");
			return;
		}

		// 入力を受け取り、検索画像の正規化した特徴量を計算
		Scanner scan = new Scanner(System.in);
		System.out.println("使用する特徴量？");
		double[] isCalculate = new double[8];
		for (int i = 0; i < 8; i++) {
			System.out.printf("f%d (yes:1 / no:any): ", i + 1);
			int ans = scan.nextInt();
			if (ans == 1) {
				isCalculate[i] = nomalize(input.features[i], average[i], devitation[i]);
			}
		}

		// それぞれの画像の特徴空間での距離を計算しTreeMapに保持
		for (Image image : database) {
			if (image.name.equals(Integer.valueOf(number).toString()))
				continue;
			double sum = 0.0;
			for (int i = 0; i < 8; i++) {
				if (isCalculate[i] != 0.0) {
					double d = nomalize(image.features[i], average[i], devitation[i]) - isCalculate[i];
					sum += Math.pow(d, 2);
				}
			}
			double distance = Math.sqrt(sum);
			similarity.put(distance, image);
		}
		
		//テキストファイルに書き込み保存する
		saveResult(number, isCalculate);
	}

	private void saveResult(int number, double[] isCalculate) {
		/*
		 * アウトプット用フォルダのパスを取得する
		 *  存在しない場合条件文でフォルダ作成する
		 */
		String outputFolder = new String();
		GregorianCalendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
		outputFolder = sdf.format(cal.getTime());
	     Path outPath = Paths.get("../MP1Final/" , outputFolder);
	     if (!(Files.exists(outPath))){
	    	  System.out.println("\n No exit such folder, try to create this folder... \n");
	    	 try{
	    		  Files.createDirectory(outPath);
	    		  System.out.println("created folder!\n");
	    		  Files.createDirectory(outPath.resolve("image"));
	    		}catch(IOException e){
	    		  System.out.println(e);
	    		}
	    }
	     
	     Path csvPath = Paths.get(outputFolder + ".csv");
	     try (BufferedWriter writer = Files.newBufferedWriter(outPath.resolve(csvPath))) {
	    	 	String line = "file ," + Integer.valueOf(number).toString() + ", feature, ";
	    	 	for (int i = 0; i < 8; i++) {
					if (isCalculate[i] != 0.0) {
						line += String.format("%d", i+1);
					}
				}
	    	 	writer.write(line + "\n");
	    	 	
				int flag = 1;
				for(Map.Entry<Double, Image> ii : similarity.entrySet()) {
					line = String.format("%d, %s, %f", flag , ii.getValue().toStringData(), ii.getKey());
					writer.write(line+"\n");
					ii.getValue().input.output(outPath.toString() +"/image/"+ ii.getValue().name, "bmp");
					flag++;
					if(flag > 10) break;
				}
			} catch (IOException e) {
				System.out.println(e);
		}
	}

	/**
	 * 特徴量を計算する
	 */
	public double nomalize(double feature, double average, double devitation) {
		return (feature - average) / devitation;
	}

	
	/**
	 *　課題1の出力を行う
	 */
	public void pickupImage(int number) {
		for (Image image : database) {
			String name = image.name;
			if (Integer.parseInt(name) % 10 == number) {
				System.out.println(image.toStringData());
			}
		}

	}

}
