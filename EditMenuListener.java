import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JMenuItem;

public class EditMenuListener implements ActionListener {
	private MyImage frame;

	public EditMenuListener(MyImage frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem) e.getSource()).getText().equals("원본 이미지 출력")) {
			originImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("실행 취소")) {
			undoImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("영상 합성")) {
			mergeImage();
		}
	}

	public void mergeImage() {
		InnerFrame target = searchTarget();
		if (target != null) {
			if (frame.getFileLisetener().open()) {
				ImageClass image1 = target.getNowImage();
				InnerFrame merged = MyImage.imageData.get(MyImage.imageData.size() - 1);
				ImageClass image2 = merged.getNowImage();

				ImageClass output = merge(image1, image2);
				
				InnerFrame innerFrame = new InnerFrame(target.getTitle() + " + " + merged.getTitle(), true, true, true, true, output);
				innerFrame.addInternalFrameListener(new InnerFrameListener(
						innerFrame));
				innerFrame.setSize(11 + image1.getImageWidth(), 36 + image1.getImageHeight());
				innerFrame.setVisible(true);
				frame.getDesk().add(innerFrame);
				synchronized (this) {
					MyImage.imageData.add(innerFrame);
					MyImage.data.addElement(innerFrame);
				}
			}
		}
	}

	public ImageClass merge(ImageClass image1, ImageClass image2) {
		int[][] input1R, input1G, input1B, input2R, input2G, input2B, outputR, outputG, outputB;
		int width1, height1, width2, height2, reWidth, reHeight;
		int i, j, value;

		input1R = image1.getImageR();
		input1G = image1.getImageG();
		input1B = image1.getImageB();
		width1 = image1.getImageWidth();
		height1 = image1.getImageHeight();

		input2R = image2.getImageR();
		input2G = image2.getImageG();
		input2B = image2.getImageB();
		width2 = image2.getImageWidth();
		height2 = image2.getImageHeight();

		if (width1 == width2 && height1 == height2) {
			reWidth = width1;
			reHeight = height1;
			outputR = new int[reWidth][reHeight];
			outputG = new int[reWidth][reHeight];
			outputB = new int[reWidth][reHeight];

			for (i = 0; i < height1; i++) {
				for (j = 0; j < width1; j++) {
					value = input1R[i][j] + input2R[i][j];
					if (value > 255)
						outputR[i][j] = 255;
					else if (value < 0)
						outputR[i][j] = 0;
					else
						outputR[i][j] = value;

					value = input1G[i][j] + input2G[i][j];
					if (value > 255)
						outputG[i][j] = 255;
					else if (value < 0)
						outputG[i][j] = 0;
					else
						outputG[i][j] = value;

					value = input1B[i][j] + input2B[i][j];
					if (value > 255)
						outputB[i][j] = 255;
					else if (value < 0)
						outputB[i][j] = 0;
					else
						outputB[i][j] = value;
				}
			}
			
			return new ImageClass(outputR, outputG, outputB, reWidth, reHeight);
		}
		return null;
	}

	public void undoImage() {
		InnerFrame target = searchTarget();

		if (target != null) {
			target.undoImagePaint();
		}
	}

	public void originImage() {
		InnerFrame target = searchTarget();

		if (target != null) {
			target.originalImagePaint();
		}
	}

	public synchronized InnerFrame searchTarget() {
		for (Iterator<InnerFrame> iterator = MyImage.imageData.iterator(); iterator
				.hasNext();) {
			InnerFrame tmp = iterator.next();
			if (tmp.isSelected()) {
				return tmp;
			}
		}
		return null;
	}
}
