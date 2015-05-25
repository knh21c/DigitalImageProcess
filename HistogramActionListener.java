import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;


public class HistogramActionListener implements ActionListener{
	private MyImage frame;
	private int[][] inputImageR, inputImageG, inputImageB;
	private int[][] outputImageR, outputImageG, outputImageB;
	private int width, height, reWidth, reHeight;
	private InnerFrame target;
	
	public HistogramActionListener(MyImage frame) {
		this.frame = frame;
	}
	
	public void actionPerformed(ActionEvent e){
		if (((JMenuItem) e.getSource()).getText().equals("히스토그램 출력")) {
			histoImage();
		} else if (((JMenuItem) e.getSource()).getText().equals("히스토그램 평활화")) {
			histoEqualImage();
		}
	}
	
	public void histoEqualImage(){
		searchTarget();
		if(target != null){
			int i, j;
	
			reWidth = width;
			reHeight = height;
			// OutputImage메모리 할당
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];
	
			// 디지털 신호처리
	
			double m_HIST_R[] = new double[256], m_HIST_G[] = new double[256], m_HIST_B[] = new double[256]; // 카운트
																												// 정규화
			double m_Sum_Of_HIST_R[] = new double[256], m_Sum_Of_HIST_G[] = new double[256], m_Sum_Of_HIST_B[] = new double[256]; // 누적
			int value;
	
			for (i = 0; i < 256; i++) {
				m_HIST_R[i] = 0.0;
				m_HIST_G[i] = 0.0;
				m_HIST_B[i] = 0.0;
			}
	
			// 갯수 세기 (빈도수 조사)
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					m_HIST_R[inputImageR[i][j]]++;
					m_HIST_G[inputImageG[i][j]]++;
					m_HIST_B[inputImageB[i][j]]++;
				}
			}
	
			// 누적 합계
			int SUM = 0;
			for (i = 0; i < 256; i++) {
				SUM += m_HIST_R[i];
				m_Sum_Of_HIST_R[i] = SUM;
			}
			SUM = 0;
			for (i = 0; i < 256; i++) {
				SUM += m_HIST_G[i];
				m_Sum_Of_HIST_G[i] = SUM;
			}
			SUM = 0;
			for (i = 0; i < 256; i++) {
				SUM += m_HIST_B[i];
				m_Sum_Of_HIST_B[i] = SUM;
			}
	
			// 출력하기
			int tmp;
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					tmp = inputImageR[i][j];
					value = (int) ((m_Sum_Of_HIST_R[tmp] * 256) / (width * height));
					if (value > 255)
						outputImageR[i][j] = 255;
					else if (value < 0)
						outputImageR[i][j] = 0;
					else
						outputImageR[i][j] = value;
					tmp = inputImageG[i][j];
					value = (int) ((m_Sum_Of_HIST_G[tmp] * 256) / (width * height));
					if (value > 255)
						outputImageG[i][j] = 255;
					else if (value < 0)
						outputImageG[i][j] = 0;
					else
						outputImageG[i][j] = value;
					tmp = inputImageB[i][j];
					value = (int) ((m_Sum_Of_HIST_B[tmp] * 256) / (width * height));
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
	
	public synchronized void histoImage(){
		searchTarget();
		if(target != null){
			int i, j;
			reWidth = 512;
			reHeight = 512;
			// OutputImage메모리 할당
			outputImageR = new int[reWidth][reHeight];
			outputImageG = new int[reWidth][reHeight];
			outputImageB = new int[reWidth][reHeight];
	
			// 디지털 신호처리
			double m_HIST_R[] = new double[256], m_HIST_G[] = new double[256], m_HIST_B[] = new double[256]; // 카운트
			double m_Scale_HIST_R[] = new double[256], m_Scale_HIST_G[] = new double[256], m_Scale_HIST_B[] = new double[256]; // m_HIST
																																// 정규화
			double MIN_R, MAX_R, MIN_G, MAX_G, MIN_B, MAX_B;
	
			for (i = 0; i < 256; i++) {
				m_HIST_R[i] = 0.0;
				m_HIST_G[i] = 0.0;
				m_HIST_B[i] = 0.0;
			}
	
			// 갯수 세기 (빈도수 조사)
			for (i = 0; i < width; i++) {
				for (j = 0; j < height; j++) {
					m_HIST_R[inputImageR[i][j]]++;
					m_HIST_G[inputImageG[i][j]]++;
					m_HIST_B[inputImageB[i][j]]++;
				}
			}
	
			// 최대 최소 추출
			MIN_R = MAX_R = m_HIST_R[0];
			MIN_G = MAX_G = m_HIST_G[0];
			MIN_B = MAX_B = m_HIST_B[0];
			for (i = 0; i < 256; i++) {
				if (m_HIST_R[i] < MIN_R)
					MIN_R = m_HIST_R[i];
				if (m_HIST_R[i] > MAX_R)
					MAX_R = m_HIST_R[i];
	
				if (m_HIST_G[i] < MIN_G)
					MIN_G = m_HIST_G[i];
				if (m_HIST_G[i] > MAX_G)
					MAX_G = m_HIST_G[i];
	
				if (m_HIST_B[i] < MIN_B)
					MIN_B = m_HIST_B[i];
				if (m_HIST_B[i] > MAX_B)
					MAX_B = m_HIST_B[i];
			}
	
			// 정규화
			for (i = 0; i < 256; i++) {
				m_Scale_HIST_R[i] = ((m_HIST_R[i] - MIN_R) * 255.0)
						/ (MAX_R - MIN_R);
				m_Scale_HIST_G[i] = ((m_HIST_G[i] - MIN_G) * 255.0)
						/ (MAX_G - MIN_G);
				m_Scale_HIST_B[i] = ((m_HIST_B[i] - MIN_B) * 255.0)
						/ (MAX_B - MIN_B);
			}
	
			// 화면 초기화
			for (i = 0; i < reHeight; i++) {
				for (j = 0; j < reWidth; j++) {
					outputImageR[i][j] = 255;
					outputImageG[i][j] = 255;
					outputImageB[i][j] = 255;
				}
			}
			// 출력하기
			for (i = 0; i < 256; i++) {
				for (j = 0; j < m_Scale_HIST_R[i]; j++) {
					outputImageR[i][256 - j] = 0;
				}
			}
			for (i = 0; i < 256; i++) {
				for (j = 0; j < m_Scale_HIST_G[i]; j++) {
					outputImageG[i + 256 - 1][256 - j] = 0;
				}
			}
			for (i = 0; i < 256; i++) {
				for (j = 0; j < m_Scale_HIST_B[i]; j++) {
					outputImageB[i][512 - j - 1] = 0;
				}
			}
			ImageClass image = new ImageClass(outputImageR, outputImageG, outputImageB, reWidth, reHeight);
			InnerFrame innerFrame = new InnerFrame(target.getTitle() + "'s Histogram", true, true, true, true, image);
			innerFrame.addInternalFrameListener(new InnerFrameListener(innerFrame));
			
			innerFrame.setSize(8 + reWidth, 31 + reHeight);
			innerFrame.setVisible(true);
			frame.getDesk().add(innerFrame);
			MyImage.imageData.add(innerFrame);
			MyImage.data.addElement(innerFrame);
			
			target = null;
		}
	}
	
	public synchronized void searchTarget() {
		for (InnerFrame tmp : MyImage.imageData) {
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
