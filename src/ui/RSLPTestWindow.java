package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import ui.i18n.Language;

public final class RSLPTestWindow extends JFrame {
	
	private RSLPTestWindow mainWindow;
	
	private JTextArea logArea;
	
	public RSLPTestWindow() {
		
		this.mainWindow = this;
		
		setTitle(Language.getString("application_title"));
		setSize(540, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setJMenuBar(generateMenuBar());
		
		add(new RSLPTestConsolePanel());
		
		setVisible(true);
	}
	
	private JMenuBar generateMenuBar() {
		
		JMenuBar bar = new JMenuBar();
		
		JMenu languageMenu = new JMenu(Language.getString("menu_language"));
		
		JMenuItem languageENG = new JMenuItem(Language.getString("menu_language_en"));
		languageENG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSLPTestConfig.language = "us";
				RSLPTestConfig.country = "US";
				Language.changeLanguage();
				new RSLPTestWindow();
				mainWindow.dispose();
			}
		});
		languageMenu.add(languageENG);
		
		JMenuItem languagePT = new JMenuItem(Language.getString("menu_language_pt"));
		languagePT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RSLPTestConfig.language = "pt";
				RSLPTestConfig.country = "BR";
				Language.changeLanguage();
				new RSLPTestWindow();
				mainWindow.dispose();
			}
		});
		languageMenu.add(languagePT);
		
		bar.add(languageMenu);
		
		return bar;
		
	}
	
	
	
	public JTextArea getLogArea() {
		return this.logArea;
	}
	
	public static void main(String[] args) {
		new RSLPTestWindow();
	}

}
