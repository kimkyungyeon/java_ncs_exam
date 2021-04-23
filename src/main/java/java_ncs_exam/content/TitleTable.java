package java_ncs_exam.content;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java_ncs_exam.dto.Title;
import java_ncs_exam.exception.NotSelectedException;
import java_ncs_exam.service.TitleService;



/**
 * @author lenovo
 *
 */
@SuppressWarnings("serial")
public class TitleTable extends JPanel {
	protected JTable table;
	protected List<Title> list ;
	private TitleService service;

	public TitleTable() {
		initialize();
	}
	
	public Title getItem() {
		int row = table.getSelectedRow();
		String str = (String) table.getValueAt(row, 0);
		String subStr = str.replace("T", "");
		int titleNo =  Integer.parseInt(subStr);
//		System.out.println(list.get(list.indexOf(new Title(titleNo))));
		if(row == -1) {
			throw new NotSelectedException();
		}
		return list.get(list.indexOf(new Title(titleNo)));
	};
	
	public void loadData() {
		initList();
		setList();
	}
	
	public void setPopupMenu(JPopupMenu popMenu) {
		table.setComponentPopupMenu(popMenu);
	}
	public  void initList() {
		list = service.showTitles();
	};
	
	private void initialize() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(getModel()
//				new DefaultTableModel(
//			new Object[][] {
//				{null, null, null, null},
//			},
//			new String[] {
//				"New column", "New column", "New column", "New column"
//			}
//		)
				);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
	}
	
	public DefaultTableModel getModel() {
		CustomTableModel model = new CustomTableModel(getData(), getColumnNames());
		return model;
	}
	public  String[] getColumnNames() {
		return new String[] { "직책번호", "직책명" };
	};
	
	public Object[][] getData() {
		return new Object[][] {{null,null,null}};
	}
	

	public  void setList() {
		Object[][] data = new Object[list.size()][];
		
		for(int i=0; i<data.length; i++) {
			data[i] = toArray(list.get(i));
		}
		
		CustomTableModel model = new CustomTableModel(data, getColumnNames());
		table.setModel(model);
		
		//컬럼 클릭 정렬
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);
		
		setAlignAndWidth();

	};

	
	
	/**
	 * 	//컬럼내용 정렬
		setTableAlign(SwingConstants.CENTER, 0,1,2);
		//컬럼별 너비조정
		setTableCellWidth(100,250,100);
	 */
	protected  void setAlignAndWidth() {
		//컬럼내용 정렬
		setTableAlign(SwingConstants.CENTER, 0,1);
		//컬럼별 너비조정
		setTableCellWidth(100,250);
	};

	
	protected void setTableCellWidth(int...width) {
		TableColumnModel tcm = table.getColumnModel();
		
		for(int i=0; i<width.length; i++) {
			tcm.getColumn(i).setPreferredWidth(width[i]);
		}
		
		
	}
	protected void setTableAlign(int align, int...idx) {		
		TableColumnModel tcm = table.getColumnModel();
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);
		for(int i=0 ; i<idx.length;i++) {
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
	}
	
	public  Object[] toArray(Title t) {
		
		String tNo  = String.format("T%03d", t.gettNo());
			return new Object[] {tNo, t.gettName() };
		
	}


	private class CustomTableModel extends DefaultTableModel{

		public CustomTableModel(Object[][] data, Object[] columnNames) {
			super(data, columnNames);
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
	}
	
	public void setService(TitleService service) {
		this.service = service;
	}
}
