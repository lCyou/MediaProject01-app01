package final1;

import java.util.ArrayList;

import GImage.GImage;

/**
 * @author yakum
 *	比較元になる画像データべース
 */
public abstract class Database {

	public ArrayList<Image> database;

	// 特徴軸毎の平均と標準偏差
	public double[] average = new double[8];
	public double[] devitation = new double[8];

	public Database() {
		this.database = setData();
		this.average = calcAve();
		this.devitation = calcDevi(average);

	}

	public double[] calcAve() {
		double[] feature = new double[8];
		for (Image image : database) {
			double[] i = image.getFeature();
			for(int j=0; j<8; j++) {
				feature[j] += i[j];
			}
		}

		for (int i = 0; i < 8; i++) {
			feature[i] = feature[i] / (double) database.size();
		}
		return feature;
	}

	private double[] calcDevi(double[] ave) {
		double[] feature = new double[8];
		for (Image image : database) {
			double[] i = image.getFeature();
			for(int j=0; j<8; j++) {
				feature[j] += Math.pow(i[j] - ave[j], 2);
			}
		}

		for (int i = 0; i < 8; i++) {
			feature[i] = Math.sqrt(feature[i] / (double) database.size());
		}
		return feature;
	}

	private ArrayList<Image> setData() {
		//set database Images
		ArrayList<Image> list = new ArrayList<>();

		for (int i = 1; i < 101; i++) {
			String fileNum = Integer.valueOf(i).toString();
			String fileName = "media1/2018-mp1-final1-data/" + fileNum + ".bmp";
			GImage input = new GImage(fileName);
			Image setImage = new Image(fileNum, input);
			list.add(setImage);
		}
		return list;
	}

	public abstract void similerSearch(int number);
	
	public abstract void pickupImage(int number);

}
