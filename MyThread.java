import java.util.Iterator;
import java.util.Stack;

import javax.swing.JMenuItem;


public class MyThread extends Thread{
	private MyImage frame;
	
	public MyThread(MyImage frame){
		this.frame = frame;
	}
	
	public void run(){
		while(true){
			InnerFrame target = searchTarget();
			if(target == null){
				frame.getEditMenu().setEnabled(false);
				frame.getPointProcess().setEnabled(false);
				frame.getHistoMenu().setEnabled(false);
				frame.getAreaProcess().setEnabled(false);
				frame.getGeometryProcess().setEnabled(false);
			} else {
				frame.getEditMenu().setEnabled(true);
				frame.getPointProcess().setEnabled(true);
				frame.getHistoMenu().setEnabled(true);
				frame.getAreaProcess().setEnabled(true);
				frame.getGeometryProcess().setEnabled(true);
				
				Stack<ImageClass> stack = target.getImageStack();
				if(stack.peek() == null){
					frame.getEditMenu().getItem(1).setEnabled(false);
				}else{
					frame.getEditMenu().getItem(1).setEnabled(true);
				}
			}
		}
	}
	
	public synchronized InnerFrame searchTarget() {
		for (Iterator<InnerFrame> iterator = MyImage.imageData.iterator(); iterator.hasNext();) {
			InnerFrame tmp = iterator.next();
			if (tmp.isSelected()) {
				return tmp;
			}
		}
		return null;
	}
}
