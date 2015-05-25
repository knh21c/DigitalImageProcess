import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class PointProcessListener implements ActionListener {
	private MyImage frame;
	private int[][] inputImageR, inputImageG, inputImageB;
	private int[][] outputImageR, outputImageG, outputImageB;
	private int width, height, reWidth, reHeight;
	private InnerFrame target;

	public PointProcessListener(MyImage frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem) e.getSource()).getText().equals("영상 밝기 조절")) {
			addSubImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("영상 반전")) {
			negaImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("영상 화소 곱셈/나눗셈")) {
			divMulImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("AND 연산")) {
			andImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("OR 연산")) {
			orImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("감마 이미지")) {
			gammaImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("포스터 효과")) {
			posterImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("이진화 효과")) {
			binaryImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("파라볼라(캡) 효과")) {
			paraCapImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("파라볼라(컵) 효과")) {
			paraCupImage();
		}
	}

	public void paraCupImage() {
		searchTarget();

		if (target != null) {
			// 출력 영상 메모리 할당
			reWidth = width;
			reHeight = height;
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];
			int lookUpTable[] = new int[256];
			int i, j, value;

			for (i = 0; i < 256; i++) {
				value = (int) (255.0 - (255.0 * Math.pow(i / 127.0, 2)));
				if (value > 255)
					lookUpTable[i] = 255;
				else if (value < 0)
					lookUpTable[i] = 0;
				else
					lookUpTable[i] = value;
			}

			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					outputImageR[i][j] = lookUpTable[inputImageR[i][j]];
					outputImageG[i][j] = lookUpTable[inputImageG[i][j]];
					outputImageB[i][j] = lookUpTable[inputImageB[i][j]];
				}
			}
			ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
					outputImageB, reWidth, reHeight);
			target.paintImage(outputImage);

			target = null;
		}
	}

	public void paraCapImage() {
		searchTarget();

		if (target != null) {
			// 출력 영상 메모리 할당
			reWidth = width;
			reHeight = height;
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];
			int lookUpTable[] = new int[256];
			int i, j, value;

			for (i = 0; i < 256; i++) {
				value = (int) (255.0 * Math.pow(i / 127.0, 2));
				if (value > 255)
					lookUpTable[i] = 255;
				else if (value < 0)
					lookUpTable[i] = 0;
				else
					lookUpTable[i] = value;
			}

			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					outputImageR[i][j] = lookUpTable[inputImageR[i][j]];
					outputImageG[i][j] = lookUpTable[inputImageG[i][j]];
					outputImageB[i][j] = lookUpTable[inputImageB[i][j]];
				}
			}
			ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
					outputImageB, reWidth, reHeight);
			target.paintImage(outputImage);

			target = null;
		}
	}

	public void binaryImage() {
		searchTarget();
		try {
			if (target != null) {
				// 출력 영상 메모리 할당
				reWidth = width;
				reHeight = height;
				outputImageR = new int[reWidth][reHeight];
				outputImageG = new int[reWidth][reHeight];
				outputImageB = new int[reWidth][reHeight];

				int i, j, value, intNum;
				String num = JOptionPane.showInputDialog(frame, "값 입력");
				if (num != null) {
					intNum = Integer.parseInt(num);
					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							value = inputImageR[i][j];
							if (value > intNum)
								outputImageR[i][j] = 255;
							else
								outputImageR[i][j] = 0;
							value = inputImageG[i][j];
							if (value > intNum)
								outputImageG[i][j] = 255;
							else
								outputImageG[i][j] = 0;
							value = inputImageB[i][j];
							if (value > intNum)
								outputImageB[i][j] = 255;
							else
								outputImageB[i][j] = 0;
						}
					}
					ImageClass outputImage = new ImageClass(outputImageR,
							outputImageG, outputImageB, reWidth, reHeight);
					target.paintImage(outputImage);

					target = null;
				}
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
					JOptionPane.INFORMATION_MESSAGE);
			target = null;
		}
	}

	public void posterImage() {
		searchTarget();

		if (target != null) {
			// 출력 영상 메모리 할당
			reWidth = width;
			reHeight = height;
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];

			int i, j, value;

			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					value = inputImageR[i][j];
					if (0 <= value && value <= 50)
						outputImageR[i][j] = 50;
					else if (50 < value && value <= 100)
						outputImageR[i][j] = 100;
					else if (100 < value && value <= 150)
						outputImageR[i][j] = 150;
					else if (150 < value && value <= 200)
						outputImageR[i][j] = 200;
					else
						outputImageR[i][j] = 255;
					value = inputImageG[i][j];
					if (0 <= value && value <= 50)
						outputImageG[i][j] = 50;
					else if (50 < value && value <= 100)
						outputImageG[i][j] = 100;
					else if (100 < value && value <= 150)
						outputImageG[i][j] = 150;
					else if (150 < value && value <= 200)
						outputImageG[i][j] = 200;
					else
						outputImageG[i][j] = 255;
					value = inputImageB[i][j];
					if (0 <= value && value <= 50)
						outputImageB[i][j] = 50;
					else if (50 < value && value <= 100)
						outputImageB[i][j] = 100;
					else if (100 < value && value <= 150)
						outputImageB[i][j] = 150;
					else if (150 < value && value <= 200)
						outputImageB[i][j] = 200;
					else
						outputImageB[i][j] = 255;
				}
			}
			ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
					outputImageB, reWidth, reHeight);
			target.paintImage(outputImage);

			target = null;
		}
	}

	public void gammaImage() {
		searchTarget();
		try {
			if (target != null) {
				// 출력 영상 메모리 할당
				reWidth = width;
				reHeight = height;
				outputImageR = new int[reWidth][reHeight];
				outputImageG = new int[reWidth][reHeight];
				outputImageB = new int[reWidth][reHeight];
				int i, j, value;
				String num = JOptionPane.showInputDialog(frame, "값 입력");
				if (num != null) {
					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							value = (int) Math.pow(inputImageR[i][j],
									1.0 / Double.parseDouble(num));
							if (value > 255)
								outputImageR[i][j] = 255;
							else if (value < 0)
								outputImageR[i][j] = 0;
							else
								outputImageR[i][j] = value;
							value = (int) Math.pow(inputImageG[i][j],
									1.0 / Double.parseDouble(num));
							if (value > 255)
								outputImageG[i][j] = 255;
							else if (value < 0)
								outputImageG[i][j] = 0;
							else
								outputImageG[i][j] = value;
							value = (int) Math.pow(inputImageB[i][j],
									1.0 / Double.parseDouble(num));
							if (value > 255)
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
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
					JOptionPane.INFORMATION_MESSAGE);
			target = null;
		}
	}

	public void orImage() {
		searchTarget();
		try {
			if (target != null) {
				// 출력 영상 메모리 할당
				reWidth = width;
				reHeight = height;
				outputImageR = new int[reWidth][reHeight];
				outputImageG = new int[reWidth][reHeight];
				outputImageB = new int[reWidth][reHeight];

				int i, j, value;
				String num = JOptionPane.showInputDialog(frame, "값 입력");
				if (num != null) {
					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							value = inputImageR[i][j] | Integer.parseInt(num);
							if (value > 255)
								outputImageR[i][j] = 255;
							else if (value < 0)
								outputImageR[i][j] = 0;
							else
								outputImageR[i][j] = value;
							value = inputImageG[i][j] | Integer.parseInt(num);
							if (value > 255)
								outputImageG[i][j] = 255;
							else if (value < 0)
								outputImageG[i][j] = 0;
							else
								outputImageG[i][j] = value;
							value = inputImageB[i][j] | Integer.parseInt(num);
							if (value > 255)
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
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
					JOptionPane.INFORMATION_MESSAGE);
			target = null;
		}
	}

	public void andImage() {
		searchTarget();
		try {
			if (target != null) {
				// 출력 영상 메모리 할당
				reWidth = width;
				reHeight = height;
				outputImageR = new int[reWidth][reHeight];
				outputImageG = new int[reWidth][reHeight];
				outputImageB = new int[reWidth][reHeight];

				int i, j, value;
				String num = JOptionPane.showInputDialog(frame, "값 입력");
				if (num != null) {
					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							value = inputImageR[i][j] & Integer.parseInt(num);
							if (value > 255)
								outputImageR[i][j] = 255;
							else if (value < 0)
								outputImageR[i][j] = 0;
							else
								outputImageR[i][j] = value;
							value = inputImageG[i][j] & Integer.parseInt(num);
							if (value > 255)
								outputImageG[i][j] = 255;
							else if (value < 0)
								outputImageG[i][j] = 0;
							else
								outputImageG[i][j] = value;
							value = inputImageB[i][j] & Integer.parseInt(num);
							if (value > 255)
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
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
					JOptionPane.INFORMATION_MESSAGE);
			target = null;
		}
	}

	public void divMulImage() {
		searchTarget();
		try {
			if (target != null) {
				// 출력 영상 메모리 할당
				reWidth = width;
				reHeight = height;
				outputImageR = new int[reWidth][reHeight];
				outputImageG = new int[reWidth][reHeight];
				outputImageB = new int[reWidth][reHeight];
				
				int i, j, value;
				String num = JOptionPane.showInputDialog(frame, "값 입력");
				if (num != null) {
					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							value = (int)(inputImageR[i][j] * Double.parseDouble(num));
							if (value > 255)
								outputImageR[i][j] = 255;
							else if (value < 0)
								outputImageR[i][j] = 0;
							else
								outputImageR[i][j] = value;
							value = (int)(inputImageG[i][j] * Double.parseDouble(num));
							if (value > 255)
								outputImageG[i][j] = 255;
							else if (value < 0)
								outputImageG[i][j] = 0;
							else
								outputImageG[i][j] = value;
							value = (int)(inputImageB[i][j] * Double.parseDouble(num));
							if (value > 255)
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
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
					JOptionPane.INFORMATION_MESSAGE);
			target = null;
		}
	}

	public void negaImage() {
		searchTarget();

		if (target != null) {
			// 출력 영상 메모리 할당
			reWidth = width;
			reHeight = height;
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];

			int i, j, value;

			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					value = 255 - inputImageR[i][j];
					if (value > 255)
						outputImageR[i][j] = 255;
					else if (value < 0)
						outputImageR[i][j] = 0;
					else
						outputImageR[i][j] = value;
					value = 255 - inputImageG[i][j];
					if (value > 255)
						outputImageG[i][j] = 255;
					else if (value < 0)
						outputImageG[i][j] = 0;
					else
						outputImageG[i][j] = value;
					value = 255 - inputImageB[i][j];
					if (value > 255)
						outputImageB[i][j] = 255;
					else if (value < 0)
						outputImageB[i][j] = 0;
					else
						outputImageB[i][j] = value;
				}
			}
			ImageClass outputImage = new ImageClass(outputImageR, outputImageG,
					outputImageB, reWidth, reHeight);
			target.paintImage(outputImage);

			target = null;
		}
	}

	public void addSubImage() {
		searchTarget();
		try {
			if (target != null) {
				// 출력 영상 메모리 할당
				reWidth = width;
				reHeight = height;
				outputImageR = new int[reWidth][reHeight];
				outputImageG = new int[reWidth][reHeight];
				outputImageB = new int[reWidth][reHeight];

				int i, j, value;
				String num = JOptionPane.showInputDialog(frame, "값 입력");
				if (num != null) {
					for (i = 0; i < width; i++) {
						for (j = 0; j < height; j++) {
							value = inputImageR[i][j] + Integer.parseInt(num);
							if (value > 255)
								outputImageR[i][j] = 255;
							else if (value < 0)
								outputImageR[i][j] = 0;
							else
								outputImageR[i][j] = value;
							value = inputImageG[i][j] + Integer.parseInt(num);
							if (value > 255)
								outputImageG[i][j] = 255;
							else if (value < 0)
								outputImageG[i][j] = 0;
							else
								outputImageG[i][j] = value;
							value = inputImageB[i][j] + Integer.parseInt(num);
							if (value > 255)
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
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "숫자만 입력해 주십시오.", "알림",
					JOptionPane.INFORMATION_MESSAGE);
			target = null;
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
