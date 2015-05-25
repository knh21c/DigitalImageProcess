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
		setTitle("디지털 신호 처리");
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

		// 메인 메뉴
		fileMenu = new JMenu("파일");
		menuBar.add(fileMenu);

		// 부메뉴
		JMenuItem openAction = new JMenuItem("열기");
		JMenuItem saveAction = new JMenuItem("저장");
		JMenuItem exitAction = new JMenuItem("종료");

		fileMenu.add(openAction);
		fileMenu.add(saveAction);
		fileMenu.addSeparator();
		fileMenu.add(exitAction);

		fileListener = new FileActionListener(this);
		openAction.addActionListener(fileListener);
		saveAction.addActionListener(fileListener);
		exitAction.addActionListener(fileListener);
		// 편집 메뉴
		editMenu = new JMenu("편집");
		menuBar.add(editMenu);
		
		JMenuItem originImageAction = new JMenuItem("원본 이미지 출력");
		JMenuItem undoImageAction = new JMenuItem("실행 취소");
		JMenuItem mergeImageAction = new JMenuItem("영상 합성");
		
		editMenu.add(originImageAction);
		editMenu.add(undoImageAction);
		editMenu.add(mergeImageAction);
		
		EditMenuListener editListener = new EditMenuListener(this);
		originImageAction.addActionListener(editListener);
		undoImageAction.addActionListener(editListener);
		mergeImageAction.addActionListener(editListener);
		// 화소 점 처리 메뉴
		pointProcessMenu = new JMenu("화소 점 처리");
		menuBar.add(pointProcessMenu);

		JMenuItem addSubImageAction = new JMenuItem("영상 밝기 조절");
		JMenuItem negaImageAction = new JMenuItem("영상 반전");
		JMenuItem divMulImageAction = new JMenuItem("영상 화소 곱셈/나눗셈");
		JMenuItem andImageAction = new JMenuItem("AND 연산");
		JMenuItem orImageAction = new JMenuItem("OR 연산");
		JMenuItem gammaImageAction = new JMenuItem("감마 이미지");
		JMenuItem posterImageAction = new JMenuItem("포스터 효과");
		JMenuItem binaryImageAction = new JMenuItem("이진화 효과");
		JMenuItem paraCapImageAction = new JMenuItem("파라볼라(캡) 효과");
		JMenuItem paraCupImageAction = new JMenuItem("파라볼라(컵) 효과");

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
		
		histoMenu = new JMenu("히스토그램");
		menuBar.add(histoMenu);
		
		JMenuItem histoImageAction = new JMenuItem("히스토그램 출력");
		JMenuItem histoEqualImageAction = new JMenuItem("히스토그램 평활화");
		
		histoMenu.add(histoImageAction);
		histoMenu.add(histoEqualImageAction);
		
		HistogramActionListener histoListener = new HistogramActionListener(this);
		histoImageAction.addActionListener(histoListener);
		histoEqualImageAction.addActionListener(histoListener);
		
		areaProcessMenu = new JMenu("화소 영역 처리");
		menuBar.add(areaProcessMenu);
		
		JMenuItem emboImageAction = new JMenuItem("엠보싱 효과");
		JMenuItem blurImageAction = new JMenuItem("블러링 효과");
		JMenuItem sharpImageAction = new JMenuItem("샤프닝 효과");
		JMenuItem customizMaskImageAction = new JMenuItem("사용자 정의 마스크");
		
		areaProcessMenu.add(emboImageAction);
		areaProcessMenu.add(blurImageAction);
		areaProcessMenu.add(sharpImageAction);
		areaProcessMenu.add(customizMaskImageAction);

		AreaProcessListener areaListener = new AreaProcessListener(this);
		emboImageAction.addActionListener(areaListener);
		blurImageAction.addActionListener(areaListener);
		sharpImageAction.addActionListener(areaListener);
		customizMaskImageAction.addActionListener(areaListener);
		
		geometryProcessMenu = new JMenu("기하학 처리");
		menuBar.add(geometryProcessMenu);
		
		JMenuItem zoomInImageAction = new JMenuItem("영상 확대");
		JMenuItem zoomOutImageAction = new JMenuItem("영상 축소");
		JMenuItem rotationImageAction = new JMenuItem("영상 회전");
		
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
