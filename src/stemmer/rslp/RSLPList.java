package stemmer.rslp;

public class RSLPList {

	public static final boolean KEEP_ACCENTS = true;
	
	public static final boolean REMOVE_ACCENTS = false;
	
	private boolean suffixRemoved = false;
	
	public String apply(String in, boolean accents) {
		
		// if the word ends in 's', execute the plural reduction step
		if(in.charAt(in.length() - 1) == 's'){
			in = pluralReduction(in);
		}
		
		// if the word ends in one of the feminine letters (a or ã), execute the feminine reduction step
		if(in.charAt(in.length() - 1) == 'a' || in.charAt(in.length() - 1) == 'ã'){
			in = feminineReduction(in);
		}
		
		//execute the degree (augmentative and diminutive) reduction step
		in = degreeReduction(in);
		
		//execute the adverb reduction step
		in = adverbReduction(in);
		
		//execute the noun reduction step
		in = nounReduction(in);
		
		//if none of the noun reduction rules was applied, then execute verb reduction step
		if(suffixRemoved == false){
			
			in = verbReduction(in);
			
			//if none of the verb reduction rules was applied, then execute vowel remove step
			if(suffixRemoved == false){
				in = removeVowel(in);
			}
			
		}
		
		//clean the flag
		suffixRemoved = false;
		
		//remove (or not) the accents based on the flag given
		if(accents == KEEP_ACCENTS){
			return in;
		}else{
			return removeAccents(in);
		}
		
	}
	
	private String searchRule(String word, RSLPStrippingRule[] list){
		for(int i=0; i<list.length; i++){
			if(word.endsWith(list[i].getSuffix()) == true){
				if(list[i].getExceptions() != null){
					if(word.length() >= list[i].getSuffix().length() + list[i].getStemSize() - list[i].getReplacement().length()){
						if(RSLPException.isException(word, list[i].getExceptions()) == false){
							word = word.substring(0, word.length() - list[i].getSuffix().length()) + list[i].getReplacement();
						}
					}
				}else{
					if(word.length() >= list[i].getSuffix().length() + list[i].getStemSize() - list[i].getReplacement().length()){
						word = word.substring(0, word.length() - list[i].getSuffix().length()) + list[i].getReplacement();
					}
				}
				i = list.length;
			}
		}
		return word;
	}
	
	public String pluralReduction(String in){
		return searchRule(in, RSLPSuffixStrippingRules.PLURAL_REDUCTION_RULES);
	}

	public String feminineReduction(String in){
		return searchRule(in, RSLPSuffixStrippingRules.FEMININE_REDUCTION_RULES);
	}

	public String degreeReduction(String in){
		return searchRule(in, RSLPSuffixStrippingRules.DEGREE_REDUCTION_RULES);
	}

	public String adverbReduction(String in){
		if(in.endsWith("mente") == true){
			if(RSLPException.isException(in, RSLPException.ADVERB_MENTE) == false){
				return in.replace("mente", "");
			}
		}
		return in;
	}

	public String nounReduction(String in){
		return searchRule(in, RSLPSuffixStrippingRules.NOUN_REDUCTION_RULES);
	}

	public String verbReduction(String in){
		return searchRule(in, RSLPSuffixStrippingRules.VERB_REDUCTION_RULES);
	}

	public static String removeVowel(String in){
		if(in.length() > 2 && RSLPException.isException(in, RSLPException.VOWEL) == false){
			if(in.endsWith("a") || in.endsWith("e") || in.endsWith("o")){
				return in.substring(0, in.length() - 1);
			}
		}
		return in;
	}

	public static String removeAccents(String in){
		return in.replaceAll("à|ã|á|â", "a").replaceAll("é|ê", "e").replaceAll("í", "i").replaceAll("ó|õ|ô", "o").replaceAll("ú", "");
	}
	
}
