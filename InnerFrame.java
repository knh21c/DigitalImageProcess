import java.awt.Container;
import java.util.Stack;

import javax.swing.JInternalFrame;

public class InnerFrame extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container contentPane;
	private int[][] inputR, inputG, inputB;
	private int width, height;
	static int openFrameCount = 0;
	static int xOffset = 30, yOffset = 30;
	private Stack<ImageClass> imageStack = new Stack<ImageClass>();
	private ImageClass originalImage, preImage, nowImage;

	public InnerFrame(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable) {
		super(title, resizable, closable, maximizable, iconifiable);
	}

	public InnerFrame(String title, boolean resizable, boolean closable,
			boolean maximizable, boolean iconifiable, ImageClass inputImage) {
		super(title, resizable, closable, maximizable, iconifiable);
		if(openFrameCount == 15){
			openFrameCount = 0;
			xOffset = xOffset * 2;
		}
		imageStack.setSize(10);
		originalImage = inputImage;
		nowImage = inputImage;
		
		init(inputImage.getImageR(), inputImage.getImageG(), inputImage.getImageB(), inputImage.getImageWidth(), inputImage.getImageHeight());
		contentPane = getContentPane();
		
		DrawPanel draw = new DrawPanel(inputImage);
		contentPane.add(draw);

		setLocation(xOffset * openFrameCount, yOffset * openFrameCount);
		
		openFrameCount++;
		//addInternalFrameListener(new InnerFrameListener(this));

	}

	public void init(int[][] imageR, int[][] imageG, int[][] imageB, int width,
			int height) {
		contentPane = getContentPane();
		this.inputR = imageR;
		this.inputG = imageG;
		this.inputB = imageB;
		this.width = width;
		this.height = height;
	}

	public String toString() {
		return getTitle();
	}

	public void paintImage(ImageClass image) {
		preImage = nowImage;
		nowImage = image;
		imageStack.push(preImage);
		contentPane.removeAll();
		DrawPanel draw = new DrawPanel(image);
		contentPane.add(draw);
		pack();
		setSize(11 + image.getImageWidth(), 36 + image.getImageHeight());
	}
	
	public void originalImagePaint(){
		preImage = nowImage;
		nowImage = originalImage;
		imageStack.push(preImage);
		contentPane.removeAll();
		DrawPanel draw = new DrawPanel(originalImage);
		contentPane.add(draw);
		pack();
		setSize(11 + originalImage.getImageWidth(), 36 + originalImage.getImageHeight());
	}
	
	public void undoImagePaint(){
		if(!imageStack.empty()){
			nowImage = imageStack.pop();
			contentPane.removeAll();
			DrawPanel draw = new DrawPanel(nowImage);
			contentPane.add(draw);
			pack();
			setSize(11 + nowImage.getImageWidth(), 36 + nowImage.getImageHeight());
		}
	}

	public int[][] getInputR() {
		return nowImage.getImageR();
	}

	public int[][] getInputG() {
		return nowImage.getImageG();
	}

	public int[][] getInputB() {
		return nowImage.getImageB();
	}

	public int getImageWidth() {
		return nowImage.getImageWidth();
	}

	public int getImageHeight() {
		return nowImage.getImageHeight();
	}
	
	public Stack<ImageClass> getImageStack(){
		return imageStack;
	}
	
	public ImageClass getNowImage(){
		return nowImage;
	}
}
