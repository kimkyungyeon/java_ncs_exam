package java_ncs_exam.exception;

import javax.swing.JOptionPane;

public class EmptyTfException extends RuntimeException {
	public EmptyTfException() {
		super("형식이 맞지 않습니다.");
	}
}
