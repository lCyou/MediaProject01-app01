package final1;

import GImage.GImage;

public class Image {

	public String name;
	public GImage input;
	public double features[] = new double[8];
	private int top;
	private int bottom;
	private int left;
	private int right;

	public Image(String name, GImage input) {
		this.name = name;
		this.input = input;
		int width = input.getWidth();
		int height = input.getHeight();
		calcFeature(input, width, height);
	}

	public void calcFeature(GImage input, int width, int height) {

		//f1
		this.features[0] = calcConcent(input, width, height);
		//f2
		this.features[1] = calcRatio(input, width, height);
		//f3
		this.features[2] = calcHolizon(input, width, height);
		//f4
		this.features[3] = calcVertical(input, width, height);
		//f5
		this.features[4] = calcHDevi(input, width, height, features[2]);
		//f6
		this.features[5] = calcVDevi(input, width, height, features[3]);
		//f7
		this.features[6] = calcCenterX(input);
		//f8
		this.features[7] = calcCenterY(input);

	}

	private double calcConcent(GImage input, int width, int height) {

		left = width;
		right = 0;
		top = height;
		bottom = 0;
		int sumBlack = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (input.pixel[i][j] == GImage.MIN_GRAY) {
					if (i < top) {
						top = i;
					}
					if (bottom < i) {
						bottom = i;
					}
					if (j < left) {
						left = j;
					}
					if (right < j) {
						right = j;
					}
					sumBlack++;
				}
				if (input.pixel[i][j] == GImage.MIN_GRAY) {
//					sumBlack++;
				}
			}
		}

		return (double) sumBlack / (double) ((bottom - top + 1) * (right - left + 1)) ;

	}

	private double calcRatio(GImage input, int width, int height) {

		return (double) (bottom - top + 1) / (double) (right - left + 1);

	}

	private double calcHolizon(GImage input, int width, int height) {
		int sum = 0;
		for (int i = top; i <= bottom; i++) {
			int pre = GImage.MAX_GRAY;
			for (int j = left; j <= right; j++) {
				if (input.pixel[i][j] == GImage.MIN_GRAY) {
					if (pre == GImage.MAX_GRAY) {
						sum++;
					}
					pre = GImage.MIN_GRAY;
				}
				if (input.pixel[i][j] == GImage.MAX_GRAY) {
					pre = GImage.MAX_GRAY;
				}
			}
		}
		return (double) sum / (double) (bottom - top + 1);
	}

	private double calcVertical(GImage input, int width, int height) {
		int sum = 0;
		for (int i = left; i <= right; i++) {
			int pre = GImage.MAX_GRAY; 
			for (int j = top; j <= bottom; j++) {
				if (input.pixel[j][i] == GImage.MIN_GRAY) {
					if (pre == GImage.MAX_GRAY) {
						pre = GImage.MIN_GRAY;
						sum++;
					}
				}
				if (input.pixel[j][i] == GImage.MAX_GRAY) {
					if (pre == GImage.MIN_GRAY) {
						pre = GImage.MAX_GRAY;
					}
				}
			}
		}
		return (double) sum / (double) (right - left + 1);
	}

	private double calcHDevi(GImage input, int width, int height, double ave) {
		double sum = 0;
		for (int i = top; i <= bottom; i++) {
			int pre = GImage.MAX_GRAY;
			double n = 0;
			for (int j = left; j <= right; j++) {
				if (input.pixel[i][j] == GImage.MIN_GRAY) {
					if (pre == GImage.MAX_GRAY) {
						n++;
					}
					pre = GImage.MIN_GRAY;
				}
				if (input.pixel[i][j] == GImage.MAX_GRAY) {
					pre = GImage.MAX_GRAY;
				}
			}
			sum += Math.pow(n - ave, 2);
		}
		return Math.sqrt((double) sum / (double) (bottom - top + 1));
	}

	private double calcVDevi(GImage input, int width, int height, double ave) {
		double sum = 0;
		for (int i = left; i <= right; i++) {
			int pre = GImage.MAX_GRAY;
			double n = 0;
			for (int j = top; j <= bottom; j++) {
				if (input.pixel[j][i] == GImage.MIN_GRAY) {
					if (pre == GImage.MAX_GRAY) {
						pre = GImage.MIN_GRAY;
						n++;
					}
				}
				if (input.pixel[j][i] == GImage.MAX_GRAY) {
					if (pre == GImage.MIN_GRAY) {
						pre = GImage.MAX_GRAY;
					}
				}
			}
			sum += Math.pow(n - ave, 2);
		}
		return Math.sqrt((double) sum / (double) (right - left + 1));
	}

	private double calcCenterX(GImage input) {
		int sumX = 0;
		int sumBlack = 0;
		int p = 999;
		for (int i = top; i < bottom; i++) {
			for (int j = left; j < right; j++) {
				if (input.pixel[i][j] == GImage.MIN_GRAY) {
					p = 1;
					sumBlack++;
				} else if (input.pixel[i][j] == GImage.MAX_GRAY) {
					p = 0;
				}

				sumX += j * p;
			}
		}

		return ((double) sumX /  (double)sumBlack) - left;

	}

	private double calcCenterY(GImage input) {
		int sumY = 0;
		int sumBlack = 0;
		int p = 999;
		for (int i = top; i < bottom; i++) {
			for (int j = left; j < right; j++) {
				if (input.pixel[i][j] == GImage.MIN_GRAY) {
					p = 1;
					sumBlack++;
				} else if (input.pixel[i][j] == GImage.MAX_GRAY) {
					p = 0;
				}

				sumY += i * p;
			}
		}

		return ((double) sumY / (double) sumBlack) - top;

	}

	public double[] getFeature() {
		return features;
	}

	public String toStringData() { // 選手の名前，チーム，利き手，成績をStringで出力
		return String.format("%s, %.3f, %.3f, %.3f, %.3f, %.3f, %.3f, %.0f, %.0f", this.name, features[0],
				features[1], features[2], features[3], features[4], features[5], features[6], features[7] );
	}
}
