package ui;

import stemmer.rslp.RSLPAutomata;

public class RSLPAutomataTester {

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
	
	private long executionTime;
	
	public RSLPAutomataTester(String input) {
		this.input = input;
	}
	
	public long getExecutionTime() {
		return this.executionTime;
	}
	
	/**
	 * Runs the informed RSLP module. Use the class constants to select a module.
	 * 
	 * @param module Constant informing the module to be executed.
	 * @return A String with the result. Each line contains a word.
	 */
	public String runRSLP(int module) {
		
		String result = "";
		
		long startTime = System.nanoTime(); 
		
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
		
		this.executionTime = System.nanoTime() - startTime;
		
		return result;
		
	}
	
	/**
	 * Executes the complete algorithm with accents remove for the string entered in the constructor
	 * 
	 * @return The result of the application of complete algorithm with accents remove for given words. Each line contains a word.
	 */
	private String runCompleteAlgorithmWithAccentsRemove() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.apply(words[i], RSLPAutomata.KEEP_ACCENTS)+"\n";
		}
		
		return result;
		
	}

	/**
	 * Executes the complete algorithm without accents remove for the string entered in the constructor
	 * 
	 * @return The result of the application of complete algorithm without accents remove for given words. Each line contains a word.
	 */
	private String runCompleteAlgorithmWithoutAccentsRemove() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.apply(words[i], RSLPAutomata.REMOVE_ACCENTS)+"\n";
		}
		
		return result;
		
	}

	/**
	 * Executes the plural reduction module for the string entered in the constructor
	 * 
	 * @return The result of the application of plural reduction for given words. Each line contains a word.
	 */
	private String runOnlyPluralReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.pluralReduction(words[i])+"\n";
		}
		
		return result;
		
	}

	/**
	 * Executes the feminine reduction module for the string entered in the constructor
	 * 
	 * @return The result of the application of feminine reduction for given words. Each line contains a word.
	 */
	private String runOnlyFeminineReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.feminineReduction(words[i])+"\n";
		}
		
		return result;
		
	}
	
	/**
	 * Executes the degree reduction module for the string entered in the constructor
	 * 
	 * @return The result of the application of degree reduction for given words. Each line contains a word.
	 */
	private String runOnlyDegreeReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.degreeReduction(words[i])+"\n";
		}
		
		return result;
		
	}

	/**
	 * Executes the adverb reduction module for the string entered in the constructor
	 * 
	 * @return The result of the application of adverb reduction for given words. Each line contains a word.
	 */
	private String runOnlyAdverbReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.adverbReduction(words[i])+"\n";
		}
		
		return result;
		
	}
	
	/**
	 * Executes the noun reduction module for the string entered in the constructor
	 * 
	 * @return The result of the application of noun reduction for given words. Each line contains a word.
	 */
	private String runOnlyNounReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.nounReduction(words[i])+"\n";
		}
		
		return result;
		
	}
	
	/**
	 * Executes the verb reduction module for the string entered in the constructor
	 * 
	 * @return The result of the application of verb reduction for given words. Each line contains a word.
	 */
	private String runOnlyVerbReduction() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.verbReduction(words[i])+"\n";
		}
		
		return result;
		
	}
	
	/**
	 * Executes the remove vowel module for the string entered in the constructor
	 * 
	 * @return The result of the application of vowel removal for given words. Each line contains a word.
	 */
	private String runOnlyRemoveVowel() {
		
		String[] words = this.input.split("\n");
		String result = "";

		for(int i=0; i < words.length; i++) {
			result += rslp.removeVowel(words[i])+"\n";
		}
		
		return result;
		
	}
	
	/**
	 * Executes the remove accents module for the string entered in the constructor
	 * 
	 * @return The result of the application of accents removal for given words. Each line contains a word.
	 */
	private String runOnlyRemoveAccents() {
		
		String[] words = this.input.split("\n");
		String result = "";
	
		for(int i=0; i < words.length; i++) {
			result += rslp.removeAccents(words[i])+"\n";
		}
		
		return result;
		
	}

}
