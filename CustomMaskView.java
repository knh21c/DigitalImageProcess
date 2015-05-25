import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CustomMaskView extends JFrame {
	private JPanel contentPane;
	private JTextField input1;
	private JTextField input0;
	private JTextField input2;
	private JTextField input3;
	private JTextField input4;
	private JTextField input5;
	private JTextField input6;
	private JTextField input7;
	private JTextField input8;
	private JTextField input9;
	private JTextField input10;
	private JTextField input11;
	private JTextField input12;
	private JTextField input13;
	private JTextField input14;
	private JTextField input15;
	private JTextField input16;
	private JTextField input17;
	private JTextField input18;
	private JTextField input19;
	private JTextField input20;
	private JTextField input21;
	private JTextField input22;
	private JTextField input23;
	private JTextField input24;
	private JTextField input25;
	private JTextField input26;
	private JTextField input27;
	private JTextField input28;
	private JTextField input29;
	private JTextField input30;
	private JTextField input31;
	private JTextField input32;
	private JTextField input33;
	private JTextField input34;
	private JTextField input35;
	private JTextField input36;
	private JTextField input37;
	private JTextField input38;
	private JTextField input39;
	private JTextField input40;
	private JTextField input41;
	private JTextField input42;
	private JTextField input43;
	private JTextField input44;
	private JTextField input45;
	private JTextField input46;
	private JTextField input47;
	private JTextField input48;
	private JComboBox comboBox;
	private JButton confirmBtn;
	private Vector<JTextField> inputs = new Vector<JTextField>();

	public CustomMaskView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("사용자 정의 마스크");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);

		JLabel lblNewLabel = new JLabel(
				"\uB9C8\uC2A4\uD06C \uC0AC\uC774\uC988:");
		panel.add(lblNewLabel);

		comboBox = new JComboBox();

		comboBox.setModel(new DefaultComboBoxModel(new String[] { " ", "3",
				"5", "7" }));
		panel.add(comboBox);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JComboBox) e.getSource()).getSelectedItem().equals(" ")) {
					allInputDisable();
				} else if (((JComboBox) e.getSource()).getSelectedItem()
						.equals("3")) {
					allInputDisable();
					input16.setVisible(true);
					input17.setVisible(true);
					input18.setVisible(true);
					input23.setVisible(true);
					input24.setVisible(true);
					input25.setVisible(true);
					input30.setVisible(true);
					input31.setVisible(true);
					input32.setVisible(true);
				} else if (((JComboBox) e.getSource()).getSelectedItem()
						.equals("5")) {
					allInputDisable();
					input8.setVisible(true);
					input9.setVisible(true);
					input10.setVisible(true);
					input11.setVisible(true);
					input12.setVisible(true);
					input15.setVisible(true);
					input16.setVisible(true);
					input17.setVisible(true);
					input18.setVisible(true);
					input19.setVisible(true);
					input22.setVisible(true);
					input23.setVisible(true);
					input24.setVisible(true);
					input25.setVisible(true);
					input26.setVisible(true);
					input29.setVisible(true);
					input30.setVisible(true);
					input31.setVisible(true);
					input32.setVisible(true);
					input33.setVisible(true);
					input36.setVisible(true);
					input37.setVisible(true);
					input38.setVisible(true);
					input39.setVisible(true);
					input40.setVisible(true);
				} else if (((JComboBox) e.getSource()).getSelectedItem()
						.equals("7")) {
					allInputEnable();
				}
			}
		});

		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setHgap(40);
		contentPane.add(panel_1, BorderLayout.SOUTH);

		confirmBtn = new JButton("\uD655\uC778");
		panel_1.add(confirmBtn);

		JButton cancelBtn = new JButton("\uCDE8\uC18C");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_1.add(cancelBtn);

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new GridLayout(7, 7, 20, 2));

		input0 = new JTextField();
		panel_2.add(input0);
		input0.setColumns(10);

		input1 = new JTextField();
		panel_2.add(input1);
		input1.setColumns(10);

		input2 = new JTextField();
		panel_2.add(input2);
		input2.setColumns(10);

		input3 = new JTextField();
		panel_2.add(input3);
		input3.setColumns(10);

		input4 = new JTextField();
		panel_2.add(input4);
		input4.setColumns(10);

		input5 = new JTextField();
		panel_2.add(input5);
		input5.setColumns(10);

		input6 = new JTextField();
		panel_2.add(input6);
		input6.setColumns(10);

		input7 = new JTextField();
		panel_2.add(input7);
		input7.setColumns(10);

		input8 = new JTextField();
		panel_2.add(input8);
		input8.setColumns(10);

		input9 = new JTextField();
		panel_2.add(input9);
		input9.setColumns(10);

		input10 = new JTextField();
		panel_2.add(input10);
		input10.setColumns(10);

		input11 = new JTextField();
		panel_2.add(input11);
		input11.setColumns(10);

		input12 = new JTextField();
		panel_2.add(input12);
		input12.setColumns(10);

		input13 = new JTextField();
		panel_2.add(input13);
		input13.setColumns(10);

		input14 = new JTextField();
		panel_2.add(input14);
		input14.setColumns(10);

		input15 = new JTextField();
		panel_2.add(input15);
		input15.setColumns(10);

		input16 = new JTextField();
		panel_2.add(input16);
		input16.setColumns(10);

		input17 = new JTextField();
		panel_2.add(input17);
		input17.setColumns(10);

		input18 = new JTextField();
		panel_2.add(input18);
		input18.setColumns(10);

		input19 = new JTextField();
		panel_2.add(input19);
		input19.setColumns(10);

		input20 = new JTextField();
		panel_2.add(input20);
		input20.setColumns(10);

		input21 = new JTextField();
		panel_2.add(input21);
		input21.setColumns(10);

		input22 = new JTextField();
		panel_2.add(input22);
		input22.setColumns(10);

		input23 = new JTextField();
		panel_2.add(input23);
		input23.setColumns(10);

		input24 = new JTextField();
		panel_2.add(input24);
		input24.setColumns(10);

		input25 = new JTextField();
		panel_2.add(input25);
		input25.setColumns(10);

		input26 = new JTextField();
		panel_2.add(input26);
		input26.setColumns(10);

		input27 = new JTextField();
		panel_2.add(input27);
		input27.setColumns(10);

		input28 = new JTextField();
		panel_2.add(input28);
		input28.setColumns(10);

		input29 = new JTextField();
		panel_2.add(input29);
		input29.setColumns(10);

		input30 = new JTextField();
		panel_2.add(input30);
		input30.setColumns(10);

		input31 = new JTextField();
		panel_2.add(input31);
		input31.setColumns(10);

		input32 = new JTextField();
		panel_2.add(input32);
		input32.setColumns(10);

		input33 = new JTextField();
		panel_2.add(input33);
		input33.setColumns(10);

		input34 = new JTextField();
		panel_2.add(input34);
		input34.setColumns(10);

		input35 = new JTextField();
		panel_2.add(input35);
		input35.setColumns(10);

		input36 = new JTextField();
		panel_2.add(input36);
		input36.setColumns(10);

		input37 = new JTextField();
		panel_2.add(input37);
		input37.setColumns(10);

		input38 = new JTextField();
		panel_2.add(input38);
		input38.setColumns(10);

		input39 = new JTextField();
		panel_2.add(input39);
		input39.setColumns(10);

		input40 = new JTextField();
		panel_2.add(input40);
		input40.setColumns(10);

		input41 = new JTextField();
		panel_2.add(input41);
		input41.setColumns(10);

		input42 = new JTextField();
		panel_2.add(input42);
		input42.setColumns(10);

		input43 = new JTextField();
		panel_2.add(input43);
		input43.setColumns(10);

		input44 = new JTextField();
		panel_2.add(input44);
		input44.setColumns(10);

		input45 = new JTextField();
		panel_2.add(input45);
		input45.setColumns(10);

		input46 = new JTextField();
		panel_2.add(input46);
		input46.setColumns(10);

		input47 = new JTextField();
		panel_2.add(input47);
		input47.setColumns(10);

		input48 = new JTextField();
		panel_2.add(input48);
		input48.setColumns(10);

		allInputDisable();
	}

	public void allInputEnable() {
		input0.setVisible(true);
		input1.setVisible(true);
		input2.setVisible(true);
		input3.setVisible(true);
		input4.setVisible(true);
		input5.setVisible(true);
		input6.setVisible(true);
		input7.setVisible(true);
		input8.setVisible(true);
		input9.setVisible(true);
		input10.setVisible(true);
		input11.setVisible(true);
		input12.setVisible(true);
		input13.setVisible(true);
		input14.setVisible(true);
		input15.setVisible(true);
		input16.setVisible(true);
		input17.setVisible(true);
		input18.setVisible(true);
		input19.setVisible(true);
		input20.setVisible(true);
		input21.setVisible(true);
		input22.setVisible(true);
		input23.setVisible(true);
		input24.setVisible(true);
		input25.setVisible(true);
		input26.setVisible(true);
		input27.setVisible(true);
		input28.setVisible(true);
		input29.setVisible(true);
		input30.setVisible(true);
		input31.setVisible(true);
		input32.setVisible(true);
		input33.setVisible(true);
		input34.setVisible(true);
		input35.setVisible(true);
		input36.setVisible(true);
		input37.setVisible(true);
		input38.setVisible(true);
		input39.setVisible(true);
		input40.setVisible(true);
		input41.setVisible(true);
		input42.setVisible(true);
		input43.setVisible(true);
		input44.setVisible(true);
		input45.setVisible(true);
		input46.setVisible(true);
		input47.setVisible(true);
		input48.setVisible(true);
	}

	public void allInputDisable() {
		input0.setVisible(false);
		input1.setVisible(false);
		input2.setVisible(false);
		input3.setVisible(false);
		input4.setVisible(false);
		input5.setVisible(false);
		input6.setVisible(false);
		input7.setVisible(false);
		input8.setVisible(false);
		input9.setVisible(false);
		input10.setVisible(false);
		input11.setVisible(false);
		input12.setVisible(false);
		input13.setVisible(false);
		input14.setVisible(false);
		input15.setVisible(false);
		input16.setVisible(false);
		input17.setVisible(false);
		input18.setVisible(false);
		input19.setVisible(false);
		input20.setVisible(false);
		input21.setVisible(false);
		input22.setVisible(false);
		input23.setVisible(false);
		input24.setVisible(false);
		input25.setVisible(false);
		input26.setVisible(false);
		input27.setVisible(false);
		input28.setVisible(false);
		input29.setVisible(false);
		input30.setVisible(false);
		input31.setVisible(false);
		input32.setVisible(false);
		input33.setVisible(false);
		input34.setVisible(false);
		input35.setVisible(false);
		input36.setVisible(false);
		input37.setVisible(false);
		input38.setVisible(false);
		input39.setVisible(false);
		input40.setVisible(false);
		input41.setVisible(false);
		input42.setVisible(false);
		input43.setVisible(false);
		input44.setVisible(false);
		input45.setVisible(false);
		input46.setVisible(false);
		input47.setVisible(false);
		input48.setVisible(false);
	}
	
	public JComboBox getComboBox(){
		return comboBox;
	}
	
	public JButton getConfirmBtn(){
		return confirmBtn;
	}
	
	public Vector<JTextField> get3X3Inputs(){
		inputs.removeAllElements();
		inputs.add(input16);
		inputs.add(input17);
		inputs.add(input18);
		inputs.add(input23);
		inputs.add(input24);
		inputs.add(input25);
		inputs.add(input30);
		inputs.add(input31);
		inputs.add(input32);
		
		return inputs;
	}
	
	public Vector<JTextField> get5X5Inputs(){
		inputs.removeAllElements();
		inputs.add(input8);
		inputs.add(input9);
		inputs.add(input10);
		inputs.add(input11);
		inputs.add(input12);
		inputs.add(input15);
		inputs.add(input16);
		inputs.add(input17);
		inputs.add(input18);
		inputs.add(input19);
		inputs.add(input22);
		inputs.add(input23);
		inputs.add(input24);
		inputs.add(input25);
		inputs.add(input26);
		inputs.add(input29);
		inputs.add(input30);
		inputs.add(input31);
		inputs.add(input32);
		inputs.add(input33);
		inputs.add(input36);
		inputs.add(input37);
		inputs.add(input38);
		inputs.add(input39);
		inputs.add(input40);
		return inputs;
	}
	
	public Vector<JTextField> get7X7Inputs(){
		inputs.removeAllElements();
		inputs.add(input0);
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		inputs.add(input4);
		inputs.add(input5);
		inputs.add(input6);
		inputs.add(input7);
		inputs.add(input8);
		inputs.add(input9);
		inputs.add(input10);
		inputs.add(input11);
		inputs.add(input12);
		inputs.add(input13);
		inputs.add(input14);
		inputs.add(input15);
		inputs.add(input16);
		inputs.add(input17);
		inputs.add(input18);
		inputs.add(input19);
		inputs.add(input20);
		inputs.add(input21);
		inputs.add(input22);
		inputs.add(input23);
		inputs.add(input24);
		inputs.add(input25);
		inputs.add(input26);
		inputs.add(input27);
		inputs.add(input28);
		inputs.add(input29);
		inputs.add(input30);
		inputs.add(input31);
		inputs.add(input32);
		inputs.add(input33);
		inputs.add(input34);
		inputs.add(input35);
		inputs.add(input36);
		inputs.add(input37);
		inputs.add(input38);
		inputs.add(input39);
		inputs.add(input40);
		inputs.add(input41);
		inputs.add(input42);
		inputs.add(input43);
		inputs.add(input44);
		inputs.add(input45);
		inputs.add(input46);
		inputs.add(input47);
		inputs.add(input48);
		
		return inputs;
	}
}
