import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class FileActionListener implements ActionListener {
	private MyImage frame;
	private int[][] inputImageR, inputImageG, inputImageB;
	private int width, height;
	private InnerFrame target;

	public FileActionListener(MyImage frame) {
		this.frame = frame;
	}

	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem) e.getSource()).getText().equals("열기")) {
			open();
		} else if (((JMenuItem) e.getSource()).getText().equals("저장")) {
			save();
		} else if (((JMenuItem) e.getSource()).getText().equals("종료")) {
			exit();
		}
	}

	public boolean open() {
		int i, j;
		String fname = "";

		final FileDialog dlg = new FileDialog(frame, "열기", FileDialog.LOAD);
		dlg.setVisible(true);
		fname = dlg.getDirectory() + File.separator + dlg.getFile();

		if (fname == "")
			return false;
		// 칼라 이미지 로딩
		try {
			BufferedImage readImage = null;
			readImage = ImageIO.read(new File(fname));

			width = readImage.getWidth();
			height = readImage.getHeight();

			inputImageR = new int[width][height];
			inputImageG = new int[width][height];
			inputImageB = new int[width][height];

			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					Color RGB = new Color(readImage.getRGB(i, j));
					inputImageR[i][j] = RGB.getRed();
					inputImageG[i][j] = RGB.getGreen();
					inputImageB[i][j] = RGB.getBlue();
				}
			}
			ImageClass inputImage = new ImageClass(inputImageR, inputImageG,
					inputImageB, width, height);

			InnerFrame innerFrame = new InnerFrame(dlg.getFile(), true, true,
					true, true, inputImage);
			innerFrame.addInternalFrameListener(new InnerFrameListener(
					innerFrame));

			innerFrame.setSize(11 + width, 36 + height);
			innerFrame.setVisible(true);
			frame.getDesk().add(innerFrame);
			synchronized (this) {
				MyImage.imageData.add(innerFrame);
				MyImage.data.addElement(innerFrame);
			}
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public void save(){
		target = searchTarget();
		if(target != null){
			ImageClass image = target.getNowImage();
			int i, j;
			String fname = "";
			final FileDialog dlg = new FileDialog(frame, "저장", FileDialog.SAVE);
			dlg.setVisible(true);
			fname = dlg.getDirectory() + File.separator + dlg.getFile();
			
			if(fname == "")
				return;

			try{
				BufferedImage writeImage = new BufferedImage(image.getImageWidth(), image.getImageHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics g = writeImage.getGraphics();
				for(i=0; i<image.getImageHeight(); i++){
					for(j=0; j<image.getImageWidth(); j++){
						g.setColor(new Color(image.getImageR()[i][j], image.getImageG()[i][j], image.getImageB()[i][j]));
						g.drawRect(i, j, 1, 1);
					}
				}
				ImageIO.write(writeImage, "jpg", new File(fname));
				
				JOptionPane.showMessageDialog(null, "저장 완료!", "알림",
						JOptionPane.INFORMATION_MESSAGE);
			}catch(Exception e){
				
			}
		}
	}

	public void exit() {
		System.exit(0);
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