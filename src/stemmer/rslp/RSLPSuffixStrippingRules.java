package stemmer.rslp;

public final class RSLPSuffixStrippingRules {
	
	public static final int SUFFIX = 0;
	
	public static final int MINIMUM_STEM_SIZE = 1;
	
	public static final int REPLACEMENT = 2;
	
	public static final int EXAMPLE = 3;
	
	public static final int EXAMPLE_RESULT = 4;

	public static final String[][] PLURAL_REDUCTION_RULES = {
		{"ns", "1", "m", "bons", "bom"},
		{"ões", "3", "ão", "balões", "balão"},
		{"ães", "1", "ão", "capitães", "capitão"},
		{"ais", "1", "al", "normais", "normal"},
		{"éis", "2", "el", "papéis", "papel"},
		{"eis", "2", "el", "amáveis", "amável"},
		{"óis", "2", "ol", "lençóis", "lençol"},
		{"is", "2", "il", "barris", "barril"},
		{"les", "3", "l", "males", "mal"},
		{"res", "3", "r", "mares", "mar"},
		{"s", "2", "", "casas", "casa"}
	};
	
	public static final String[][] FEMININE_REDUCTION_RULES = {
		
	};
	
	public static final String[][] ADVERB_REDUCTION_RULES = {
			
	};
	
	public static final String[][] DEGREE_REDUCTION_RULES = {
			
	};
	
	public static final String[][] NOUN_REDUCTION_RULES = {
			
	};
	
	public static final String[][] VERB_REDUCTION_RULES = {
			
	};
	
	public static final String[][] VOWEL_REDUCTION_RULES = {
			
	};
	
}
