import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AreaProcessListener implements ActionListener {
	private MyImage frame;
	private int[][] inputImageR, inputImageG, inputImageB;
	private int[][] outputImageR, outputImageG, outputImageB;
	private int width, height, reWidth, reHeight;
	private InnerFrame target;
	private CustomMaskView customMaskView;
	private int mSize;
	private Vector<JTextField> tmpMask = new Vector<JTextField>();

	public AreaProcessListener(MyImage frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem) e.getSource()).getText().equals("엠보싱 효과")) {
			emboImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("블러링 효과")) {
			blurImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("샤프닝 효과")) {
			sharpImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("사용자 정의 마스크")) {
			customizMaskImage();
		}
	}

	public void customizMaskImage() {
		searchTarget();
		if (target != null) {
			customMaskView = new CustomMaskView();
			customMaskView.setVisible(true);

			JButton confirmBtn = customMaskView.getConfirmBtn();
			confirmBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String strMSize = (String) customMaskView.getComboBox()
							.getSelectedItem();
					mSize = Integer.parseInt(strMSize);
					if (mSize == 3)
						tmpMask = customMaskView.get3X3Inputs();
					else if (mSize == 5)
						tmpMask = customMaskView.get5X5Inputs();
					else if (mSize == 7)
						tmpMask = customMaskView.get7X7Inputs();

					customMaskView.dispose();
					maskImage();
				}
			});
		}
	}

	public void maskImage() {
		int i, j;
		reWidth = width;
		reHeight = height;
		// OutputImage메모리 할당
		outputImageR = new int[reWidth][reHeight];
		outputImageG = new int[reWidth][reHeight];
		outputImageB = new int[reWidth][reHeight];

		// 디지털 신호처리
		double Mask[][] = new double[mSize][mSize];
		System.out.println(mSize);
		for (i = 0; i < mSize; i++) {
			for (j = 0; j < mSize; j++) {
				Mask[i][j] = Double.parseDouble(tmpMask.get(j + (i * mSize)).getText());
				System.out.println(tmpMask.get(j+(i*mSize)).getText());
			}
		}
		
		// 실수용 영상메모리
		double tempInputImageR[][], tempInputImageG[][], tempInputImageB[][], tempOutputImageR[][], tempOutputImageG[][], tempOutputImageB[][];

		tempInputImageR = new double[(int) (width + (mSize - 1))][(int) (height + (mSize - 1))];
		tempInputImageG = new double[(int) (width + (mSize - 1))][(int) (height + (mSize - 1))];
		tempInputImageB = new double[(int) (width + (mSize - 1))][(int) (height + (mSize - 1))];

		tempOutputImageR = new double[reWidth][reHeight];
		tempOutputImageG = new double[reWidth][reHeight];
		tempOutputImageB = new double[reWidth][reHeight];

		// 정수 Input --> 실수 Input
		for (i = 0; i < width; i++) {
			for (j = 0; j < height; j++) {
				tempInputImageR[i + 1][j + 1] = inputImageR[i][j];
				tempInputImageG[i + 1][j + 1] = inputImageG[i][j];
				tempInputImageB[i + 1][j + 1] = inputImageB[i][j];
			}
		}

		// 회선 연산
		int n, m;
		double S_R = 0.0, S_G = 0.0, S_B = 0.0;
		for (i = 0; i < width; i++) {
			for (j = 0; j < height; j++) {
				// 한 점의 마스크 연산
				for (n = 0; n < mSize; n++) {
					for (m = 0; m < mSize; m++) {
						S_R = S_R
								+ (tempInputImageR[i + n][j + m] * Mask[n][m]);
						S_G = S_G
								+ (tempInputImageG[i + n][j + m] * Mask[n][m]);
						S_B = S_B
								+ (tempInputImageB[i + n][j + m] * Mask[n][m]);
					}
				}
				tempOutputImageR[i][j] = S_R;
				tempOutputImageG[i][j] = S_G;
				tempOutputImageB[i][j] = S_B;
				S_R = S_G = S_B = 0.0;
			}
		}
		int sumOfMask = 0;
		for(i=0; i<mSize; i++){
			for(j=0; j<mSize; j++){
				sumOfMask += Mask[i][j];
			}
		}
		int value;
		
		if(sumOfMask == 0){
			Vector<double[][]> result = add127(tempOutputImageR, tempOutputImageG, tempOutputImageB);
			tempOutputImageR = result.get(0);
			tempOutputImageG = result.get(1);
			tempOutputImageB = result.get(2);
		}
		else{
			for (i = 0; i < reWidth; i++) {
				for (j = 0; j < reHeight; j++) {
					value = (int) (tempOutputImageR[i][j]);
					if (value > 255)
						tempOutputImageR[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageR[i][j] = 0;
					else
						tempOutputImageR[i][j] = value;
					value = (int) (tempOutputImageG[i][j]);
					if (value > 255)
						tempOutputImageG[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageG[i][j] = 0;
					else
						tempOutputImageG[i][j] = value;
					value = (int) (tempOutputImageB[i][j]);
					if (value > 255)
						tempOutputImageB[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageB[i][j] = 0;
					else
						tempOutputImageB[i][j] = value;
				}
			}
		}

		// 임시 output --> 정수 output
		for (i = 0; i < reWidth; i++) {
			for (j = 0; j < reHeight; j++) {
				outputImageR[i][j] = (int) tempOutputImageR[i][j];
				outputImageG[i][j] = (int) tempOutputImageG[i][j];
				outputImageB[i][j] = (int) tempOutputImageB[i][j];
			}
		}

		ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
				outputImageB, reWidth, reHeight);
		target.paintImage(outputImage);

		target = null;
	}
	
	public Vector<double[][]> add127(double[][] tempOutputImageR, double[][] tempOutputImageG, double[][] tempOutputImageB){
		int i, j, value;
		for (i = 0; i < reWidth; i++) {
			for (j = 0; j < reHeight; j++) {
				value = (int) (tempOutputImageR[i][j] + 127.0);
				if (value > 255)
					tempOutputImageR[i][j] = 255.0;
				else if (value < 0)
					tempOutputImageR[i][j] = 0;
				else
					tempOutputImageR[i][j] = value;
				value = (int) (tempOutputImageG[i][j] + 127.0);
				if (value > 255)
					tempOutputImageG[i][j] = 255.0;
				else if (value < 0)
					tempOutputImageG[i][j] = 0;
				else
					tempOutputImageG[i][j] = value;
				value = (int) (tempOutputImageB[i][j] + 127.0);
				if (value > 255)
					tempOutputImageB[i][j] = 255.0;
				else if (value < 0)
					tempOutputImageB[i][j] = 0;
				else
					tempOutputImageB[i][j] = value;
			}
		}
		
		Vector<double[][]> result = new Vector<double[][]>();
		result.add(tempOutputImageR);
		result.add(tempOutputImageG);
		result.add(tempOutputImageB);
		
		return result;
	}

	public void sharpImage() {
		searchTarget();

		if (target != null) {
			int i, j;

			reWidth = width;
			reHeight = height;
			// OutputImage메모리 할당
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];

			// 디지털 신호처리
			double Mask[][] = { { 0.0, -1.0, 0.0 }, { -1.0, 5.0, -1.0 },
					{ 0.0, -1.0, 0.0 } };
			mSize = 3;
			// 실수용 영상메모리
			double tempInputImageR[][], tempInputImageG[][], tempInputImageB[][], tempOutputImageR[][], tempOutputImageG[][], tempOutputImageB[][];

			tempInputImageR = new double[width + 2][height + 2];
			tempInputImageG = new double[width + 2][height + 2];
			tempInputImageB = new double[width + 2][height + 2];

			tempOutputImageR = new double[reWidth][reHeight];
			tempOutputImageG = new double[reWidth][reHeight];
			tempOutputImageB = new double[reWidth][reHeight];

			// 정수 Input --> 실수 Input
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					tempInputImageR[i + 1][j + 1] = inputImageR[i][j];
					tempInputImageG[i + 1][j + 1] = inputImageG[i][j];
					tempInputImageB[i + 1][j + 1] = inputImageB[i][j];
				}
			}

			// 회선 연산
			int n, m;
			double S_R = 0.0, S_G = 0.0, S_B = 0.0;
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					// 한 점의 마스크 연산
					for (n = 0; n < mSize; n++) {
						for (m = 0; m < mSize; m++) {
							S_R = S_R
									+ (tempInputImageR[i + n][j + m] * Mask[n][m]);
							S_G = S_G
									+ (tempInputImageG[i + n][j + m] * Mask[n][m]);
							S_B = S_B
									+ (tempInputImageB[i + n][j + m] * Mask[n][m]);
						}
					}
					tempOutputImageR[i][j] = S_R;
					tempOutputImageG[i][j] = S_G;
					tempOutputImageB[i][j] = S_B;
					S_R = S_G = S_B = 0.0;
				}
			}

			int value;
			for (i = 0; i < reWidth; i++) {
				for (j = 0; j < reHeight; j++) {
					value = (int) (tempOutputImageR[i][j]);
					if (value > 255)
						tempOutputImageR[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageR[i][j] = 0;
					else
						tempOutputImageR[i][j] = value;
					value = (int) (tempOutputImageG[i][j]);
					if (value > 255)
						tempOutputImageG[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageG[i][j] = 0;
					else
						tempOutputImageG[i][j] = value;
					value = (int) (tempOutputImageB[i][j]);
					if (value > 255)
						tempOutputImageB[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageB[i][j] = 0;
					else
						tempOutputImageB[i][j] = value;
				}
			}

			// 임시 output --> 정수 output
			for (i = 0; i < reWidth; i++) {
				for (j = 0; j < reHeight; j++) {
					outputImageR[i][j] = (int) tempOutputImageR[i][j];
					outputImageG[i][j] = (int) tempOutputImageG[i][j];
					outputImageB[i][j] = (int) tempOutputImageB[i][j];
				}
			}

			ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
					outputImageB, reWidth, reHeight);
			target.paintImage(outputImage);

			target = null;
		}
	}

	public void blurImage() {
		searchTarget();

		if (target != null) {
			int i, j;

			reWidth = width;
			reHeight = height;
			// OutputImage메모리 할당
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];

			// 디지털 신호처리
			double Mask[][] = { { 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0 },
					{ 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0 },
					{ 1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0 } };
			mSize = 3;
			// 실수용 영상메모리
			double tempInputImageR[][], tempInputImageG[][], tempInputImageB[][], tempOutputImageR[][], tempOutputImageG[][], tempOutputImageB[][];

			tempInputImageR = new double[width + 2][height + 2];
			tempInputImageG = new double[width + 2][height + 2];
			tempInputImageB = new double[width + 2][height + 2];

			tempOutputImageR = new double[reWidth][reHeight];
			tempOutputImageG = new double[reWidth][reHeight];
			tempOutputImageB = new double[reWidth][reHeight];

			// 정수 Input --> 실수 Input
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					tempInputImageR[i + 1][j + 1] = inputImageR[i][j];
					tempInputImageG[i + 1][j + 1] = inputImageG[i][j];
					tempInputImageB[i + 1][j + 1] = inputImageB[i][j];
				}
			}

			// 회선 연산
			int n, m;
			double S_R = 0.0, S_G = 0.0, S_B = 0.0;
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					// 한 점의 마스크 연산
					for (n = 0; n < mSize; n++) {
						for (m = 0; m < mSize; m++) {
							S_R = S_R
									+ (tempInputImageR[i + n][j + m] * Mask[n][m]);
							S_G = S_G
									+ (tempInputImageG[i + n][j + m] * Mask[n][m]);
							S_B = S_B
									+ (tempInputImageB[i + n][j + m] * Mask[n][m]);
						}
					}
					tempOutputImageR[i][j] = S_R;
					tempOutputImageG[i][j] = S_G;
					tempOutputImageB[i][j] = S_B;
					S_R = S_G = S_B = 0.0;
				}
			}

			int value;
			for (i = 0; i < reWidth; i++) {
				for (j = 0; j < reHeight; j++) {
					value = (int) (tempOutputImageR[i][j]);
					if (value > 255)
						tempOutputImageR[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageR[i][j] = 0;
					else
						tempOutputImageR[i][j] = value;
					value = (int) (tempOutputImageG[i][j]);
					if (value > 255)
						tempOutputImageG[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageG[i][j] = 0;
					else
						tempOutputImageG[i][j] = value;
					value = (int) (tempOutputImageB[i][j]);
					if (value > 255)
						tempOutputImageB[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageB[i][j] = 0;
					else
						tempOutputImageB[i][j] = value;
				}
			}

			// 임시 output --> 정수 output
			for (i = 0; i < reWidth; i++) {
				for (j = 0; j < reHeight; j++) {
					outputImageR[i][j] = (int) tempOutputImageR[i][j];
					outputImageG[i][j] = (int) tempOutputImageG[i][j];
					outputImageB[i][j] = (int) tempOutputImageB[i][j];
				}
			}

			ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
					outputImageB, reWidth, reHeight);
			target.paintImage(outputImage);

			target = null;
		}
	}

	public void emboImage() {
		searchTarget();

		if (target != null) {
			int i, j;

			reWidth = width;
			reHeight = height;
			// OutputImage메모리 할당
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];

			// 디지털 신호처리
			double Mask[][] = { { -1., 0., 0. }, { 0., 0., 0. }, { 0., 0., 1. } };
			mSize = 3;
			// 실수용 영상메모리
			double tempInputImageR[][], tempInputImageG[][], tempInputImageB[][], tempOutputImageR[][], tempOutputImageG[][], tempOutputImageB[][];

			tempInputImageR = new double[width + 2][height + 2];
			tempInputImageG = new double[width + 2][height + 2];
			tempInputImageB = new double[width + 2][height + 2];

			tempOutputImageR = new double[reWidth][reHeight];
			tempOutputImageG = new double[reWidth][reHeight];
			tempOutputImageB = new double[reWidth][reHeight];

			// tempInputImge 초기화
			for (i = 0; i < width + 2; i++) {
				for (j = 0; j < height + 2; j++) {
					tempInputImageR[i][j] = 127.0;
					tempInputImageG[i][j] = 127.0;
					tempInputImageB[i][j] = 127.0;
				}
			}

			// 정수 Input --> 실수 Input
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					tempInputImageR[i + 1][j + 1] = inputImageR[i][j];
					tempInputImageG[i + 1][j + 1] = inputImageG[i][j];
					tempInputImageB[i + 1][j + 1] = inputImageB[i][j];
				}
			}

			// 회선 연산
			int n, m;
			double S_R = 0.0, S_G = 0.0, S_B = 0.0;
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					// 한 점의 마스크 연산
					for (n = 0; n < mSize; n++) {
						for (m = 0; m < mSize; m++) {
							S_R = S_R
									+ (tempInputImageR[i + n][j + m] * Mask[n][m]);
							S_G = S_G
									+ (tempInputImageG[i + n][j + m] * Mask[n][m]);
							S_B = S_B
									+ (tempInputImageB[i + n][j + m] * Mask[n][m]);
						}
					}
					tempOutputImageR[i][j] = S_R;
					tempOutputImageG[i][j] = S_G;
					tempOutputImageB[i][j] = S_B;
					S_R = S_G = S_B = 0.0;
				}
			}

			// 영상값 + 127
			int value;
			for (i = 0; i < reWidth; i++) {
				for (j = 0; j < reHeight; j++) {
					value = (int) (tempOutputImageR[i][j] + 127.0);
					if (value > 255)
						tempOutputImageR[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageR[i][j] = 0;
					else
						tempOutputImageR[i][j] = value;
					value = (int) (tempOutputImageG[i][j] + 127.0);
					if (value > 255)
						tempOutputImageG[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageG[i][j] = 0;
					else
						tempOutputImageG[i][j] = value;
					value = (int) (tempOutputImageB[i][j] + 127.0);
					if (value > 255)
						tempOutputImageB[i][j] = 255.0;
					else if (value < 0)
						tempOutputImageB[i][j] = 0;
					else
						tempOutputImageB[i][j] = value;
				}
			}

			// 임시 output --> 정수 output
			for (i = 0; i < reWidth; i++) {
				for (j = 0; j < reHeight; j++) {
					outputImageR[i][j] = (int) tempOutputImageR[i][j];
					outputImageG[i][j] = (int) tempOutputImageG[i][j];
					outputImageB[i][j] = (int) tempOutputImageB[i][j];
				}
			}

			ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
					outputImageB, reWidth, reHeight);
			target.paintImage(outputImage);

			target = null;
		}
	}

	public synchronized void searchTarget() {
		for (Iterator<InnerFrame> iterator = MyImage.imageData.iterator(); iterator.hasNext();) {
			InnerFrame tmp = iterator.next();
			if (tmp.isSelected()) {
				target = tmp;
				inputImageR = target.getInputR();
				inputImageG = target.getInputG();
				inputImageB = target.getInputB();
				width = target.getImageWidth();
				height = target.getImageHeight();
			}
		}
	}
}
