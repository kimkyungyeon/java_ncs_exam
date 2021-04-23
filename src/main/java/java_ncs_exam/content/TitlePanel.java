package java_ncs_exam.content;

import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java_ncs_exam.dto.Title;
import java_ncs_exam.exception.EmptyTfException;
import java_ncs_exam.exception.InvalidCheckException;

public class TitlePanel extends JPanel {
	private JTextField tfTno;
	private JTextField tfTname;

	/**
	 * Create the panel.
	 */
	public TitlePanel() {
		
		initialize();
	}
	private void initialize() {
		setLayout(new GridLayout(0, 2, 10, 10));
		
		JLabel lblTno = new JLabel("직책번호");
		lblTno.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblTno);
		
		tfTno = new JTextField();
		add(tfTno);
		tfTno.setColumns(10);
		
		JLabel lblTname = new JLabel("직책명");
		lblTname.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblTname);
		
		tfTname = new JTextField();
		tfTname.setColumns(10);
		add(tfTname);
	}

	public JTextField getTfTno() {
		return tfTno;
	}
	public void setTfTno(JTextField tfTno) {
		this.tfTno = tfTno;
	}
	public JTextField getTfTname() {
		return tfTname;
	}
	public void setTfTname(JTextField tfTname) {
		this.tfTname = tfTname;
	}
	public void setTitle(Title title) {
		tfTno.setText(String.valueOf(title.gettNo()));
		tfTname.setText(title.gettName());
	}
	public Title getTitle() {
		int tNo = Integer.parseInt(tfTno.getText().trim());
		String tName = tfTname.getText();
		return new Title(tNo, tName);
	}
	
	public void clearTf() {
		  tfTno.setText("");
	      tfTname.setText("");
	}
	
	public void setItem(Title item) {
		tfTno.setText(String.valueOf(item.gettNo()));
		tfTname.setText(item.gettName());		
	}
	public Title getItem() {
		validCheck();
		typeCheck();
		tNoCheck();
		int tNo = Integer.parseInt(tfTno.getText().trim());
		String tName = tfTname.getText();
		return new Title(tNo, tName);
	}
	
	public void validCheck() {
		if(tfTno.getText().contentEquals("") || tfTname.getText().equals("")){
			throw new InvalidCheckException();
		}
	}
	
	public void tNoCheck() {
		if(!((Integer.parseInt(tfTno.getText())<1000) && (Integer.parseInt(tfTno.getText())>0))) {
			throw new EmptyTfException();
		}
	}
	
	public void typeCheck() {
		if(!(Pattern.matches("^[0~9]*$", tfTno.getText()) || Pattern.matches("^[가-힣]*$", tfTname.getText()))) {
			throw new EmptyTfException();
		}
	}
}
