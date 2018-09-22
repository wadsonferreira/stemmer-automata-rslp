package stemmer.rslp;

public final class RSLPStrippingRule {

	private String suffix;
	
	private int stemSize;
	
	private String replacement;
	
	private String[] exceptions;
	
	private String example;
	
	private String exampleResult;
	
	public RSLPStrippingRule(String suffix, int stemSize, String replacement, String[] exceptions, String example, String exampleResult) {
		
		this.suffix = suffix;
		this.stemSize = stemSize;
		this.replacement = replacement;
		this.exceptions = exceptions;
		this.example = example;
		this.exampleResult = exampleResult;
		
	}
	
	public RSLPStrippingRule(String suffix, int stemSize, String replacement, String[] exceptions) {}
	
	public String getSuffix() {
		return this.suffix;
	}

	public int getStemSize() {
		return stemSize;
	}

	public String getReplacement() {
		return replacement;
	}

	public String[] getExceptions() {
		return exceptions;
	}

	public String getExample() {
		return example;
	}

	public String getExampleResult() {
		return exampleResult;
	}
	
}
