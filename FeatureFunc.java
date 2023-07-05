package final1;

import GImage.GImage;

public class FeatureFunc {

	

	public static double calcVertical(GImage input, int width, int height) {

		int sum = 0;
		for (int i = 1; i < height; i++) {
			for (int j = 1; j < width; j++) {
				if (input.pixel[j][i - 1] == GImage.MAX_GRAY && input.pixel[j][i] == GImage.MIN_GRAY) {

				}
			}
		}

		return (double) sum / (double) width;
	}
	
	
}
