package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import stemmer.rslp.RSLPSuffixStrippingRules;
import ui.i18n.Language;

public class RSLPTestConsolePanel extends JPanel {

	private JTextArea inputWords;
	
	private JTextArea resultWords;
	
	private JComboBox<String> actionSelector;
	
	public RSLPTestConsolePanel() {

		setLayout(null);
		
		JPanel inputPanel = generateInputPanel();
		inputPanel.setBounds(10, 10, 515, 235);
		
		JPanel actionPanel = generateActionPanel();
		actionPanel.setBounds(10, 255, 515, 95);
		
		JPanel resultPanel = generateResultPanel();
		resultPanel.setBounds(10, 360, 515, 180);

		add(inputPanel);
		add(actionPanel);
		add(resultPanel);
		
	}
	
	private JPanel generateInputPanel() {
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(
				Color.LIGHT_GRAY, 1, true),
				Language.getString("input_panel_title"),
				TitledBorder.LEFT,
				TitledBorder.TOP
			)
		);
		panel.setLayout(null);
		
		JLabel inputPanelDescription = new JLabel(Language.getString("input_panel_description"));
		inputPanelDescription.setBounds(10, 20, 555, 20);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(10, 50, 495, 100);
		
		JLabel descriptionLabel = new JLabel(Language.getString("inpul_panel_load_description"));
		descriptionLabel.setBounds(20, 170, 415, 20);

		JComboBox<String> inputActionSelector = new JComboBox<String>(new String[] {
			Language.getString("input_load_plural_reduction_examples"),
			Language.getString("input_load_feminine_reduction_examples"),
			Language.getString("input_load_degree_reduction_examples"),
			Language.getString("input_load_adverb_reduction_examples"),
			Language.getString("input_load_noun_reduction_examples"),
			Language.getString("input_load_verb_reduction_examples"),
			Language.getString("input_load_vowel_remove_examples"),
		});
		inputActionSelector.setBounds(20, 200, 340, 30);
		
		JButton button = new JButton(Language.getString("input_panel_load_button"));
		button.setBounds(370, 200, 145, 30);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				inputWords.setText(loadSetOfExamples(inputActionSelector.getSelectedIndex()));
				actionSelector.setSelectedIndex(inputActionSelector.getSelectedIndex() + 2);
			}
		});
		
		this.inputWords = textArea;
		panel.add(inputPanelDescription);
		panel.add(scroll);
		add(descriptionLabel);
		add(inputActionSelector);
		add(button);
		
		return panel;
		
	}
	
	private JPanel generateActionPanel() {
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(
				Color.LIGHT_GRAY, 1, true),
				Language.getString("action_panel"),
				TitledBorder.LEFT,
				TitledBorder.TOP
			)
		);
		panel.setLayout(null);
		
		JLabel descriptionLabel = new JLabel(Language.getString("action_panel_description"));
		descriptionLabel.setBounds(10, 20, 415, 20);

		this.actionSelector = new JComboBox<String>(new String[] {
			Language.getString("complete_algorithm_with_accents"),
			Language.getString("complete_algorithm_without_accents"),
			Language.getString("only_plural_reduction"),
			Language.getString("only_feminine_reduction"),
			Language.getString("only_degree_reduction"),
			Language.getString("only_adverb_reduction"),
			Language.getString("only_noun_reduction"),
			Language.getString("only_verb_reduction"),
			Language.getString("only_vowel_reduction"),
		});
		this.actionSelector.setBounds(10, 50, 340, 30);
		
		JButton button = new JButton(Language.getString("action_panel_exec_button"));
		button.setBounds(360, 50, 145, 30);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				RSLPTest rslpTest = new RSLPTest(inputWords.getText());
				resultWords.setText(rslpTest.runRSLP(actionSelector.getSelectedIndex()));
			}
		});
		
		panel.add(descriptionLabel);
		panel.add(this.actionSelector);
		panel.add(button);
		
		return panel;
		
	}

	private JPanel generateResultPanel() {
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(
				Color.LIGHT_GRAY, 1, true),
				Language.getString("result_panel_title"),
				TitledBorder.LEFT,
				TitledBorder.TOP
			)
		);
		panel.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(10, 30, 495, 140);
		
		this.resultWords = textArea;
		panel.add(scroll);
		
		return panel;
		
	}
	
	private String loadSetOfExamples(int module) {
		
		String examples = "";
		
		switch(module) {
		case 0:
			for(int i=0; i < RSLPSuffixStrippingRules.PLURAL_REDUCTION_RULES.length; i++) {
				examples += RSLPSuffixStrippingRules.PLURAL_REDUCTION_RULES[i].getExample()+"\n";
			}
		break;
		case 1:
			for(int i=0; i < RSLPSuffixStrippingRules.FEMININE_REDUCTION_RULES.length; i++) {
				examples += RSLPSuffixStrippingRules.FEMININE_REDUCTION_RULES[i].getExample()+"\n";
			}
		break;
		case 2:
			for(int i=0; i < RSLPSuffixStrippingRules.DEGREE_REDUCTION_RULES.length; i++) {
				examples += RSLPSuffixStrippingRules.DEGREE_REDUCTION_RULES[i].getExample()+"\n";
			}
		break;
		case 3:
			for(int i=0; i < RSLPSuffixStrippingRules.ADVERB_REDUCTION_RULES.length; i++) {
				examples += RSLPSuffixStrippingRules.ADVERB_REDUCTION_RULES[i].getExample()+"\n";
			}
		break;
		case 4:
			for(int i=0; i < RSLPSuffixStrippingRules.NOUN_REDUCTION_RULES.length; i++) {
				examples += RSLPSuffixStrippingRules.NOUN_REDUCTION_RULES[i].getExample()+"\n";
			}
		break;
		case 5:
			for(int i=0; i < RSLPSuffixStrippingRules.VERB_REDUCTION_RULES.length; i++) {
				examples += RSLPSuffixStrippingRules.VERB_REDUCTION_RULES[i].getExample()+"\n";
			}
		break;
		case 6:
			for(int i=0; i < RSLPSuffixStrippingRules.VOWEL_REDUCTION_RULES.length; i++) {
				examples += RSLPSuffixStrippingRules.VOWEL_REDUCTION_RULES[i].getExample()+"\n";
			}
		break;
		}
		
		return examples;
		
	}
	
}
