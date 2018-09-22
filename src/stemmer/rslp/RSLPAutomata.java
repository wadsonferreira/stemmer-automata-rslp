package stemmer.rslp;

public class RSLPAutomata {

	public static final boolean REMOVE_ACCENTS = true;
	
	public static final boolean KEEP_ACCENTS = false;
	
	private static final int STOP = -1;
	
	public String apply(String in) {
		
		if(in.charAt(in.length() - 1) == 's') {
			in = pluralReduction(in);
		}
		
		return in;
		
	}
	
	public String pluralReduction(String in) {
		
		int position = in.length() - 2;
		int state = 2;
		boolean accept = false;
		
		while(position >= 0) {
			switch(state) {
			case 2:
				if(accept) {
					//rule for word ending in 's'
					//executed when the automata has change from state 4 to state 2
					
					//the value used in this if is the 'minimum stem size' (minus one because of the string starting at zero)
					//read more about the 'minimum stem size' in class RSLPSuffixStrippingRules
					if(position >= 1) {
						
						//we need to search the given word in a list of exception. If the word is found we can not apply the reduction. See the original RSLP article to more details about exceptions.
						if(RSLPException.isException(in, RSLPException.PLURAL_S) == false){
							//remove the 's'
							in = in.substring(0, in.length() - 1);
						}
					}
					position = STOP;
				}else {
					switch(in.charAt(position)) {
					case 'e':
						//got to automata state 4
						//see the file 'rslp-automata-plural.png' in folder 'automatas' for more details about status changing for the plural reduction
						state = 4;
						//set a new position of the word to be analysed
						position--;
					break;
					case 'i':
						state = 5;
						position--;
					break;
					case 'n':
						//rule for word ending in ns
						//there is no RSLPException.isException because the 'ns' rule does not have exceptions
						if(position >= 0) {
							//the 'm' character is add because it is required by the original RSLP algorithm replacement rule
							//read more about the 'replacement rule' in class RSLPSuffixStrippingRules
							in = in.substring(0, position) + "m";
						}
						position = STOP;
					break;
					default:
						//rule for word ending in 's'
						//executed when the automata can not change to state 3, 4 ou 5
						
						if(position >= 1) {
							if(RSLPException.isException(in, RSLPException.PLURAL_S) == false){
								//remove the 's'
								in = in.substring(0, in.length() - 1);
							}
						}
						position = STOP;
					break;
					}
				}
			break;
			case 4:
				switch(in.charAt(position)) {
				case 'õ':
					//rule for word ending in ões
					if(position >= 2) {
						in = in.substring(0, position) + "ão";
					}
					position = STOP;
				break;
				case 'ã':
					//rule for word ending in ães
					if(position >= 0) {
						if(RSLPException.isException(in, RSLPException.PLURAL_AES) == false){
							in = in.substring(0, position) + "ão";
						}
					}
					position = STOP;
				break;
				case 'l':
					//rule for word ending in les
					if(position >= 2) {
						in = in.substring(0, position) + "l";
					}
					position = STOP;
				break;
				case 'r':
					//rule for word ending in res
					if(position >= 2) {
						if(RSLPException.isException(in, RSLPException.PLURAL_RES) == false){
							in = in.substring(0, position) + "r";
						}
					}
					position = STOP;
				break;
				default:
					//if none of the rule are valid, we still had a word ending in s
					state = 2;
					accept = true;
				break;
				}
			break;
			case 5:
				switch(in.charAt(position)) {
				case 'a':
					if(position >= 0) {
						if(RSLPException.isException(in, RSLPException.PLURAL_AIS) == false){
							in = in.substring(0, position) + "al";
						}
					}
					position = STOP;
				break;
				case 'é':
					if(position >= 1) {
							in = in.substring(0, position) + "el";
					}
					position = STOP;
				break;
				case 'e':
					if(position >= 1) {
						if(RSLPException.isException(in, RSLPException.PLURAL_EIS) == false){
							in = in.substring(0, position) + "el";
						}
					}
					position = STOP;
				break;
				case 'ó':
					if(position >= 1) {
						in = in.substring(0, position) + "ol";
					}
					position = STOP;
				break;
				default:
					if(position >= 1) {
						if(RSLPException.isException(in, RSLPException.PLURAL_IS) == false){
							//it is necessary made (position + 1) because the algorithm has gone back one state in the automata
							in = in.substring(0, position + 1) + "il";
						}
					}
					position = STOP;
				break;
				}
			break;
			}
		}
		
		return in;
		
	}
	
}
