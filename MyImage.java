import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class MyImage extends JFrame {
	public static JList<InnerFrame> imageList;
	private Container contentPane;
	private JScrollPane scroll;
	private JDesktopPane desk;
	private JMenu fileMenu, editMenu, pointProcessMenu, histoMenu, areaProcessMenu, geometryProcessMenu;
	public static CopyOnWriteArrayList<InnerFrame> imageData = new CopyOnWriteArrayList<InnerFrame>(); 
	//public static Vector<InnerFrame> imageData = new Vector<InnerFrame>();
	public static DefaultListModel<InnerFrame> data = new DefaultListModel<InnerFrame>();
	private FileActionListener fileListener;

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		MyImage myImage = new MyImage();
		myImage.init();

		// Display the window.
		myImage.setVisible(true);
	}

	public void init() {
		setTitle("������ ��ȣ ó��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		contentPane = getContentPane();
		//leftPanel = new JPanel(null);
		//rightPanel = new JPanel(new BorderLayout());
		desk = new JDesktopPane();

		imageList = new JList<InnerFrame>();
		imageList.setModel(data);
		imageList.setFixedCellWidth(200);
		ImageListMouseListener imageListListener = new ImageListMouseListener(imageList);
		imageList.addMouseListener(imageListListener);

		data.addListDataListener(new ListDataListener() {
			public void intervalRemoved(ListDataEvent e) {
				// TODO Auto-generated method stub
				Graphics g = getGraphics();
				imageList.paintComponents(g);
			}

			public void intervalAdded(ListDataEvent e) {
				// TODO Auto-generated method stub
				Graphics g = getGraphics();
				imageList.paintComponents(g);
			}

			public void contentsChanged(ListDataEvent e) {
				// TODO Auto-generated method stub
				Graphics g = getGraphics();
				imageList.paintComponents(g);
			}
		});

		scroll = new JScrollPane(imageList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setMaximumSize(new Dimension(200, 700));
		scroll.setMinimumSize(new Dimension(200, 700));

		contentPane.add(scroll);
		contentPane.add(desk);
		setSize(1300, 700);
		addMenu();
	}

	public void addMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// ���� �޴�
		fileMenu = new JMenu("����");
		menuBar.add(fileMenu);

		// �θ޴�
		JMenuItem openAction = new JMenuItem("����");
		JMenuItem saveAction = new JMenuItem("����");
		JMenuItem exitAction = new JMenuItem("����");

		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);

		fileListener = new FileActionListener(this);
		openAction.addActionListener(fileListener);
		saveAction.addActionListener(fileListener);
		exitAction.addActionListener(fileListener);
		// ���� �޴�
		editMenu = new JMenu("����");
		menuBar.add(editMenu);
		
		JMenuItem originImageAction = new JMenuItem("���� �̹��� ���");
		JMenuItem undoImageAction = new JMenuItem("���� ���");
		JMenuItem mergeImageAction = new JMenuItem("���� �ռ�");
		
		editMenu.add(originImageAction);
		editMenu.add(undoImageAction);
		editMenu.add(mergeImageAction);
		
		EditMenuListener editListener = new EditMenuListener(this);
		originImageAction.addActionListener(editListener);
		undoImageAction.addActionListener(editListener);
		mergeImageAction.addActionListener(editListener);
		// ȭ�� �� ó�� �޴�
		pointProcessMenu = new JMenu("ȭ�� �� ó��");
		menuBar.add(pointProcessMenu);

		JMenuItem addSubImageAction = new JMenuItem("���� ��� ����");
		JMenuItem negaImageAction = new JMenuItem("���� ����");
		JMenuItem divMulImageAction = new JMenuItem("���� ȭ�� ����/������");
		JMenuItem andImageAction = new JMenuItem("AND ����");
		JMenuItem orImageAction = new JMenuItem("OR ����");
		JMenuItem gammaImageAction = new JMenuItem("���� �̹���");
		JMenuItem posterImageAction = new JMenuItem("������ ȿ��");
		JMenuItem binaryImageAction = new JMenuItem("����ȭ ȿ��");
		JMenuItem paraCapImageAction = new JMenuItem("�Ķ󺼶�(ĸ) ȿ��");
		JMenuItem paraCupImageAction = new JMenuItem("�Ķ󺼶�(��) ȿ��");

		pointProcessMenu.add(addSubImageAction);
		pointProcessMenu.add(negaImageAction);
		pointProcessMenu.add(divMulImageAction);
		pointProcessMenu.add(andImageAction);
		pointProcessMenu.add(orImageAction);
		pointProcessMenu.add(gammaImageAction);
		pointProcessMenu.add(posterImageAction);
		pointProcessMenu.add(binaryImageAction);
		pointProcessMenu.add(paraCapImageAction);
		pointProcessMenu.add(paraCupImageAction);

		PointProcessListener pointProcessListener = new PointProcessListener(
				this);
		addSubImageAction.addActionListener(pointProcessListener);
		negaImageAction.addActionListener(pointProcessListener);
		divMulImageAction.addActionListener(pointProcessListener);
		andImageAction.addActionListener(pointProcessListener);
		orImageAction.addActionListener(pointProcessListener);
		gammaImageAction.addActionListener(pointProcessListener);
		posterImageAction.addActionListener(pointProcessListener);
		binaryImageAction.addActionListener(pointProcessListener);
		paraCapImageAction.addActionListener(pointProcessListener);
		paraCupImageAction.addActionListener(pointProcessListener);
		
		histoMenu = new JMenu("������׷�");
		menuBar.add(histoMenu);
		
		JMenuItem histoImageAction = new JMenuItem("������׷� ���");
		JMenuItem histoEqualImageAction = new JMenuItem("������׷� ��Ȱȭ");
		
		histoMenu.add(histoImageAction);
		histoMenu.add(histoEqualImageAction);
		
		HistogramActionListener histoListener = new HistogramActionListener(this);
		histoImageAction.addActionListener(histoListener);
		histoEqualImageAction.addActionListener(histoListener);
		
		areaProcessMenu = new JMenu("ȭ�� ���� ó��");
		menuBar.add(areaProcessMenu);
		
		JMenuItem emboImageAction = new JMenuItem("������ ȿ��");
		JMenuItem blurImageAction = new JMenuItem("���� ȿ��");
		JMenuItem sharpImageAction = new JMenuItem("������ ȿ��");
		JMenuItem customizMaskImageAction = new JMenuItem("����� ���� ����ũ");
		
		areaProcessMenu.add(emboImageAction);
		areaProcessMenu.add(blurImageAction);
		areaProcessMenu.add(sharpImageAction);
		areaProcessMenu.add(customizMaskImageAction);

		AreaProcessListener areaListener = new AreaProcessListener(this);
		emboImageAction.addActionListener(areaListener);
		blurImageAction.addActionListener(areaListener);
		sharpImageAction.addActionListener(areaListener);
		customizMaskImageAction.addActionListener(areaListener);
		
		geometryProcessMenu = new JMenu("������ ó��");
		menuBar.add(geometryProcessMenu);
		
		JMenuItem zoomInImageAction = new JMenuItem("���� Ȯ��");
		JMenuItem zoomOutImageAction = new JMenuItem("���� ���");
		JMenuItem rotationImageAction = new JMenuItem("���� ȸ��");
		
		geometryProcessMenu.add(zoomInImageAction);
		geometryProcessMenu.add(zoomOutImageAction);
		geometryProcessMenu.add(rotationImageAction);
		
		GeometryProcessListener geometryListener = new GeometryProcessListener(this);
		zoomInImageAction.addActionListener(geometryListener);
		zoomOutImageAction.addActionListener(geometryListener);
		rotationImageAction.addActionListener(geometryListener);
		
		MyThread th = new MyThread(this);
		th.start();
	}

	public JDesktopPane getDesk() {
		return desk;
	}
	
	public JMenu getEditMenu(){
		return editMenu;
	}
	
	public JMenu getPointProcess(){
		return pointProcessMenu;
	}
	
	public JMenu getHistoMenu(){
		return histoMenu;
	}
	
	public JMenu getAreaProcess(){
		return areaProcessMenu;
	}
	
	public JMenu getGeometryProcess(){
		return geometryProcessMenu;
	}
	
	public FileActionListener getFileLisetener(){
		return fileListener;
	}
}
