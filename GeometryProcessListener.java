import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GeometryProcessListener implements ActionListener {
	private MyImage frame;
	private int[][] inputImageR, inputImageG, inputImageB;
	private int[][] outputImageR, outputImageG, outputImageB;
	private int width, height, reWidth, reHeight;
	private InnerFrame target;

	public GeometryProcessListener(MyImage frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem) e.getSource()).getText().equals("영상 확대")) {
			zoomInImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("영상 축소")) {
			zoomOutImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("영상 회전")) {
			rotationImage();
		}
	}

	public void rotationImage() {
		searchTarget();
		if (target != null) {
			int i, j;
			int angle;
			try {
				String num = JOptionPane.showInputDialog(null, "값 입력");
				if (num != null) {
					angle = Integer.parseInt(num);
					double rad = angle * Math.PI / 180.0;
					double radForSize = (angle % 90) * Math.PI / 180.0;
					double rad90ForSize = (90 - (angle % 90)) * Math.PI / 180.0;
					double r_H, r_W;
					int i_H, i_W;
					double linX, linY, A, B, C, D;
					int NR, NG, NB;
					double scale;

					reWidth = (int) (height * Math.cos(rad90ForSize) + width
							* Math.cos(radForSize));
					reHeight = (int) (height * Math.cos(radForSize) + width
							* Math.cos(rad90ForSize));

					outputImageR = new int[reWidth][reHeight];
					outputImageG = new int[reWidth][reHeight];
					outputImageB = new int[reWidth][reHeight];

					// 임시 입력 영상(출력과 크기 동일)
					int tempInputR[][], tempInputG[][], tempInputB[][];
					tempInputR = new int[reWidth][reHeight];
					tempInputG = new int[reWidth][reHeight];
					tempInputB = new int[reWidth][reHeight];

					for (i = 0; i < reWidth; i++) {
						for (j = 0; j < reHeight; j++) {
							tempInputR[i][j] = 255;
							tempInputG[i][j] = 255;
							tempInputB[i][j] = 255;
						}
					}

					// 입력 --> 임시 입력의 중앙
					int diffH = (reHeight - height) / 2;
					int diffW = (reWidth - width) / 2;

					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							tempInputR[i + diffW][j + diffH] = inputImageR[i][j];
							tempInputG[i + diffW][j + diffH] = inputImageG[i][j];
							tempInputB[i + diffW][j + diffH] = inputImageB[i][j];
						}
					}

					int x, y;
					int cenX = reWidth / 2, cenY = reHeight / 2;

					for (i = 0; i < reWidth; i++) {
						for (j = 0; j < reHeight; j++) {
							x = i;
							y = j;

							r_W = (((x - cenX) * Math.cos(rad) - (y - cenY)
									* Math.sin(rad)) + cenX);
							r_H = (((x - cenX) * Math.sin(rad) + (y - cenY)
									* Math.cos(rad)) + cenY);

							if (r_H >= 0 && r_H < reHeight && r_W >= 0
									&& r_W < reWidth) {
								i_H = (int) r_H;
								i_W = (int) r_W;
								linY = r_W - i_W;
								linX = r_H - i_H;

								if (i_H < 0 || i_H >= (reHeight - 1) || i_W < 0
										|| i_W >= (reWidth - 1)) {
									outputImageR[x][y] = 255;
									outputImageG[x][y] = 255;
									outputImageB[x][y] = 255;
								} else {
									A = tempInputR[i_W][i_H];
									B = tempInputR[i_W][i_H + 1];
									C = tempInputR[i_W + 1][i_H];
									D = tempInputR[i_W + 1][i_H + 1];
									NR = (int) ((A * (1 - linY) * (1 - linX))
											+ (B * (1 - linY) * linX)
											+ (C * linY * (1 - linX)) + (D
											* linX * linY));

									A = tempInputG[i_W][i_H];
									B = tempInputG[i_W][i_H + 1];
									C = tempInputG[i_W + 1][i_H];
									D = tempInputG[i_W + 1][i_H + 1];
									NG = (int) ((A * (1 - linY) * (1 - linX))
											+ (B * (1 - linY) * linX)
											+ (C * linY * (1 - linX)) + (D
											* linX * linY));

									A = tempInputB[i_W][i_H];
									B = tempInputB[i_W][i_H + 1];
									C = tempInputB[i_W + 1][i_H];
									D = tempInputB[i_W + 1][i_H + 1];
									NB = (int) ((A * (1 - linY) * (1 - linX))
											+ (B * (1 - linY) * linX)
											+ (C * linY * (1 - linX)) + (D
											* linX * linY));

									outputImageR[x][y] = NR;
									outputImageG[x][y] = NG;
									outputImageB[x][y] = NB;
								}
							} else {
								outputImageR[x][y] = 255;
								outputImageG[x][y] = 255;
								outputImageB[x][y] = 255;
							}
						}
					}

					ImageClass outputImage = new ImageClass(outputImageR,
							outputImageG, outputImageB, reWidth, reHeight);
					target.paintImage(outputImage);

					target = null;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
						JOptionPane.INFORMATION_MESSAGE);
				target = null;
			}
		}
	}

	public void zoomOutImage() {
		searchTarget();
		if (target != null) {
			int i, j, q, r;
			double scale;
			double sumR, sumG, sumB;
			try {
				String num = JOptionPane.showInputDialog(null, "값 입력");
				if (num != null) {
					scale = Integer.parseInt(num);

					reWidth = (int)(width / scale);
					reHeight = (int)(height / scale);
					// OutputImage메모리 할당
					outputImageR = new int[reWidth][reHeight];
					outputImageG = new int[reWidth][reHeight];
					outputImageB = new int[reWidth][reHeight];
					int value;
					for (i = 0; i < reWidth; i++) {
						for (j = 0; j < reHeight; j++) {
							sumR = 0.0;
							sumG = 0.0;
							sumB = 0.0;
							for (q = 0; q < scale; q++) {
								for (r = 0; r < scale; r++) {
									sumR += (double) inputImageR[i * (int)scale + q][j
											* (int)scale + r];
									sumG += (double) inputImageG[i * (int)scale + q][j
											* (int)scale + r];
									sumB += (double) inputImageB[i * (int)scale + q][j
											* (int)scale + r];
								}
							}
							value = (int) (sumR / (scale * scale));
							if(value > 255)
								outputImageR[i][j] = 255;
							else if (value < 0)
								outputImageR[i][j] = 0;
							else
								outputImageR[i][j] = value;
							
							value = (int) (sumG / (scale * scale));
							if(value > 255)
								outputImageG[i][j] = 255;
							else if (value < 0)
								outputImageG[i][j] = 0;
							else
								outputImageG[i][j] = value;
							
							value = (int) (sumB / (scale * scale));
							if(value > 255)
								outputImageB[i][j] = 255;
							else if (value < 0)
								outputImageB[i][j] = 0;
							else
								outputImageB[i][j] = value;
						}
					}

					ImageClass outputImage = new ImageClass(outputImageR,
							outputImageG, outputImageB, reWidth, reHeight);
					target.paintImage(outputImage);

					target = null;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "정수만 입력해 주십시오.", "알림",
						JOptionPane.INFORMATION_MESSAGE);
				target = null;
			}
		}
	}

	public void zoomInImage() {
		searchTarget();
		if (target != null) {
			int i, j;
			double scale;
			String num = JOptionPane.showInputDialog(null, "값 입력");
			if (num != null) {
				try {
					int i_H, i_W; // 픽셀의 정수 위치
					double r_H, r_W; // 픽셀의 실수 위치
					double x, y;
					double A_R, A_G, A_B, B_R, B_G, B_B, C_R, C_G, C_B, D_R, D_G, D_B; // 픽셀의
																						// 화소
																						// 값
					int N_R, N_G, N_B;
					scale = Double.parseDouble(num);
					reWidth = (int)(width * scale);
					reHeight = (int)(height * scale);

					// OutputImage메모리 할당
					outputImageR = new int[reWidth][reHeight];
					outputImageG = new int[reWidth][reHeight];
					outputImageB = new int[reWidth][reHeight];

					for (i = 0; i < reWidth; i++) {
						for (j = 0; j < reHeight; j++) {
							r_H = i / (double) scale;
							r_W = j / (double) scale;
							i_H = (int) r_H;
							i_W = (int) r_W;
							x = r_W - i_W;
							y = r_H - i_H;
							// 위치가 안에 들어왔는지 체크
							if (i_H < 0 || i_H >= (width - 1) || i_W < 0
									|| i_W >= (height - 1)) {
								outputImageR[i][j] = 255;
								outputImageG[i][j] = 255;
								outputImageB[i][j] = 255;
							} else {
								A_R = inputImageR[i_H][i_W];
								A_G = inputImageG[i_H][i_W];
								A_B = inputImageB[i_H][i_W];
								B_R = inputImageR[i_H][i_W + 1];
								B_G = inputImageG[i_H][i_W + 1];
								B_B = inputImageB[i_H][i_W + 1];
								C_R = inputImageR[i_H + 1][i_W];
								C_G = inputImageG[i_H + 1][i_W];
								C_B = inputImageB[i_H + 1][i_W];
								D_R = inputImageR[i_H + 1][i_W + 1];
								D_G = inputImageG[i_H + 1][i_W + 1];
								D_B = inputImageB[i_H + 1][i_W + 1];

								N_R = (int) ((A_R * (1 - y) * (1 - x))
										+ (B_R * (1 - y) * x)
										+ (C_R * y * (1 - x)) + (D_R * x * y));
								N_G = (int) ((A_G * (1 - y) * (1 - x))
										+ (B_G * (1 - y) * x)
										+ (C_G * y * (1 - x)) + (D_G * x * y));
								N_B = (int) ((A_B * (1 - y) * (1 - x))
										+ (B_B * (1 - y) * x)
										+ (C_B * y * (1 - x)) + (D_B * x * y));
								outputImageR[i][j] = N_R;
								outputImageG[i][j] = N_G;
								outputImageB[i][j] = N_B;
							}
						}
					}

					ImageClass outputImage = new ImageClass(outputImageR,
							outputImageG, outputImageB, reWidth, reHeight);
					target.paintImage(outputImage);

					target = null;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
							JOptionPane.INFORMATION_MESSAGE);
					target = null;
				}
			}
		}
	}

	public synchronized void searchTarget() {
		for (Iterator<InnerFrame> iterator = MyImage.imageData.iterator(); iterator
				.hasNext();) {
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
