package ui;

import stemmer.rslp.RSLPAutomata;

public class RSLPTest {

	public static final int COMPLETE_AUTOMATA_WITH_ACCENTS = 0;
	
	public static final int COMPLETE_AUTOMATA_WITHOUT_ACCENTS = 1;
	
	public static final int ONLY_PLURAL_REDUCTION = 2;
	
	public static final int ONLY_FEMININE_REDUCTION = 3;
	
	public static final int ONLY_DEGREE_REDUCTION = 4;
	
	public static final int ONLY_ADVERB_REDUCTION = 5;
	
	public static final int ONLY_NOUN_REDUCTION = 6;
	
	public static final int ONLY_VERB_REDUCTION = 7;
	
	public static final int ONLY_REMOVE_VOWEL = 8;
	
	public static final int ONLY_REMOVE_ACCENTS = 9;
	
	private RSLPAutomata rslp = new RSLPAutomata();
	
	private String input;
	
	public RSLPTest(String input) {
		this.input = input;
	}
	
	public String runRSLP(int module) {
		
		String result = "";
		
		switch(module) {
		case COMPLETE_AUTOMATA_WITH_ACCENTS:
			result = runCompleteAlgorithmWithAccentsRemove();
		break;
		case COMPLETE_AUTOMATA_WITHOUT_ACCENTS:
			result = runCompleteAlgorithmWithoutAccentsRemove();
		break;
		case ONLY_PLURAL_REDUCTION:
			result = runOnlyPluralReduction();
		break;
		case ONLY_FEMININE_REDUCTION:
			result = runOnlyFeminineReduction();
		break;
		case ONLY_DEGREE_REDUCTION:
			result = runOnlyDegreeReduction();
		break;
		case ONLY_ADVERB_REDUCTION:
			result = runOnlyAdverbReduction();
		break;
		case ONLY_NOUN_REDUCTION:
			result = runOnlyNounReduction();
		break;
		case ONLY_VERB_REDUCTION:
			result = runOnlyVerbReduction();
		break;
		case ONLY_REMOVE_VOWEL:
			result = runOnlyRemoveVowel();
		break;
		case ONLY_REMOVE_ACCENTS:
			result = runOnlyRemoveAccents();
		break;
		}
		
		return result;
		
	}
	
	private String runCompleteAlgorithmWithAccentsRemove() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.apply(words[i], RSLPAutomata.KEEP_ACCENTS)+"\n";
		}
		
		return result;
		
	}

	private String runCompleteAlgorithmWithoutAccentsRemove() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.apply(words[i], RSLPAutomata.REMOVE_ACCENTS)+"\n";
		}
		
		return result;
		
	}

	private String runOnlyPluralReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.pluralReduction(words[i])+"\n";
		}
		
		return result;
		
	}

	private String runOnlyFeminineReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.feminineReduction(words[i])+"\n";
		}
		
		return result;
		
	}

	private String runOnlyDegreeReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.degreeReduction(words[i])+"\n";
		}
		
		return result;
		
	}

	private String runOnlyAdverbReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.adverbReduction(words[i])+"\n";
		}
		
		return result;
		
	}
	
	private String runOnlyNounReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.nounReduction(words[i])+"\n";
		}
		
		return result;
		
	}
	
	private String runOnlyVerbReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.verbReduction(words[i])+"\n";
		}
		
		return result;
		
	}
	
	private String runOnlyRemoveVowel() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.removeVowel(words[i])+"\n";
		}
		
		return result;
		
	}
	

	private String runOnlyRemoveAccents() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.removeAccents(words[i])+"\n";
		}
		
		return result;
		
	}

}
