package java_ncs_exam;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java_ncs_exam.content.TitlePanel;
import java_ncs_exam.content.TitleTable;
import java_ncs_exam.dto.Title;
import java_ncs_exam.exception.EmptyTfException;
import java_ncs_exam.exception.InvalidCheckException;
import java_ncs_exam.service.TitleService;

public class Management extends JFrame implements ActionListener {

	private Title updateTitle;
	private TitlePanel pTitle;
	private TitleTable pList;
	private JPanel contentPane;
	private TitleService service;
	private JButton btnAdd;
	private JButton btnCancel;

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Management frame = new Management();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public Management() {
		service = new TitleService();
		initialize();
	}

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("직책 관리");
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		pTitle = new TitlePanel();
		contentPane.add(pTitle);

		JPanel pBtns = new JPanel();
		contentPane.add(pBtns);

		btnAdd = new JButton("추가");
		btnAdd.addActionListener(this);
		pBtns.add(btnAdd);

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(this);

		pBtns.add(btnCancel);

		pList = new TitleTable();
		pList.setService(service);
		pList.loadData();
		contentPane.add(pList);

		JPopupMenu popupMenu = createPopupMenu();
		pList.setPopupMenu(popupMenu);
	}

	private JPopupMenu createPopupMenu() {
		JPopupMenu popMenu = new JPopupMenu();

		JMenuItem updateItem = new JMenuItem("직책수정");
		updateItem.addActionListener(popupMenuListener);
		popMenu.add(updateItem);

		JMenuItem deleteItem = new JMenuItem("직책삭제");
		deleteItem.addActionListener(popupMenuListener);
		popMenu.add(deleteItem);

		return popMenu;
	}

	ActionListener popupMenuListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (e.getActionCommand().equals("직책수정")) {
					updateTitle = pList.getItem();
					pTitle.setItem(updateTitle);
					pTitle.getTfTno().setEnabled(false);
					btnAdd.setText("수정");
				}
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "해당 직책을 선택하세요", "선택 오류", JOptionPane.WARNING_MESSAGE);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "형식이 맞지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
			}
			
			
			try {
				if (e.getActionCommand().equals("직책삭제")) {
					Title delTitle = pList.getItem();
					service.removeTitle(delTitle);
					pList.loadData();
					JOptionPane.showMessageDialog(null, delTitle + " 삭제되었습니다.", "메시지",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "해당 직책을 선택하세요", "선택 오류", JOptionPane.WARNING_MESSAGE);
			}

		}
	};

	private void actionPerformedBtnAdd(ActionEvent e) {
//		validCheck();
//		tnoCheck();
		Title title = pTitle.getItem();
		service.addTitle(title);
		pList.loadData();
		pTitle.clearTf();
		JOptionPane.showMessageDialog(null, title + " 추가했습니다.", "메시지", JOptionPane.INFORMATION_MESSAGE);
	}

	private void validCheck() {
		if (pTitle.getTfTname().getText().contentEquals("") || pTitle.getTfTno().getText().contentEquals("")) {

			throw new InvalidCheckException();
		}
	}

	

	private void actionPerformedBtnCancel(ActionEvent e) {
		pTitle.clearTf();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			actionPerformedBtnCancel(e);
		}

		if (e.getSource() == btnAdd) {
			try {
				if (e.getActionCommand().contentEquals("추가")) {
					actionPerformedBtnAdd(e);
				} else {
					actionPerformedBtnUpdate(e);
				}
			} catch (EmptyTfException e1) {
				JOptionPane.showMessageDialog(null, "형식이 맞지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);

			} catch (InvalidCheckException e1) {
				JOptionPane.showMessageDialog(null, "공란이 존재합니다.", "오류", JOptionPane.ERROR_MESSAGE);
			} 
		}
	}

	private void actionPerformedBtnUpdate(ActionEvent e) {
		Title updateTitle1 = null;
		updateTitle1 = pTitle.getItem();
		service.modifyTitle(updateTitle1);
		pList.loadData();
		pTitle.clearTf();
		btnAdd.setText("추가");
		pTitle.getTfTno().setEnabled(true);
		JOptionPane.showMessageDialog(null, this.updateTitle + "가 " + updateTitle1 + "로 정보가 수정되었습니다.");

	}
}
