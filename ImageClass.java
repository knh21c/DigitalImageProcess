
public class ImageClass {
	private int[][] imageR, imageG, imageB;
	private int width, height;
	
	public ImageClass(){
		
	}
	
	public ImageClass(int[][] imageR, int[][] imageG, int[][] imageB, int width, int height){
		this.imageR = imageR;
		this.imageG = imageG;
		this.imageB = imageB;
		this.width = width;
		this.height = height;
	}
	
	public void setImageR(int[][] imageR){
		this.imageR = imageR;
	}
	
	public void setImageG(int[][] imageG){
		this.imageG = imageG;
	}
	
	public void setImageB(int[][] imageB){
		this.imageB = imageB;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public void setHeight(int height){
		this.height = height;
	}
	
	public int[][] getImageR(){
		return imageR;
	}
	
	public int[][] getImageG(){
		return imageG;
	}
	
	public int[][] getImageB(){
		return imageB;
	}
	
	public int getImageWidth(){
		return width;
	}
	
	public int getImageHeight(){
		return height;
	}
}
