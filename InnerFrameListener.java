import java.util.Iterator;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

public class InnerFrameListener implements InternalFrameListener {
	InnerFrame thisFrame;

	public InnerFrameListener(InnerFrame innerFrame) {
		thisFrame = innerFrame;
	}

	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameClosing(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public synchronized void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		MyImage.imageData.remove(thisFrame);
		MyImage.data.removeElement(thisFrame);
	}

	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public synchronized void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		MyImage.imageList.setSelectedValue(thisFrame, false);
	}

	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

}