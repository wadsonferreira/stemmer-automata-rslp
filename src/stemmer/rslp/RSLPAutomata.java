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
	
	public String feminineReduction(String in){

		int position = in.length() - 2;
		int state = 1;

		while(position >= 0){
			switch(state){
			case 1:
				switch(in.charAt(position)){
				case 'n':
					state = 3;
					position--;
				break;
				case 'r':
					state = 4;
					position--;
				break;
				case 'h':
					state = 5;
					position--;
				break;
				case 's':
					state = 6;
					position--;
				break;
				case 'c':
					state = 7;
					position--;
				break;
				case 'd':
					state = 8;
					position--;
				break;
				case 'm':
					state = 9;
					position--;
				break;
				case 'v':
					state = 10;
					position--;
				break;
				default:
					if(in.charAt(in.length() - 1) == 'ã'){
						//rule for word ending in ã
						if(position >= 1){
							if(RSLPException.isException(in, RSLPException.FEMININE_AA) == false){
								in = in.substring(0, position + 1) + "ão";
							}
						}
					}
					position = STOP;
				break;
				}
			break;
			case 3:
				if(in.charAt(position) == 'o'){
					//rule for word ending in ona
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_ONA) == false){
							in = in.substring(0, position) + "ão";
						}
					}
				}else{
					//rule for word ending in a
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.FEMININE_NA) == false){
							in = in.substring(0, position + 1) + "no";
						}
					}
				}
				position = STOP;
			break;
			case 4:
				switch(in.charAt(position)){
				case 'i':
					state = 23;
					position--;
				break;
				case 'o':
					//rule for word ending in ora
					if(position >= 2){
						in = in.substring(0, position) + "or";
					}
					position = STOP;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 5:
				if(in.charAt(position) == 'n'){
					state = 13;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 6:
				switch(in.charAt(position)){
				case 'e':
					//rule for word ending in esa
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_ESA) == false){
							in = in.substring(0, position) + "ês";
						}
					}
				break;
				case 'o':
					//rule for word ending in osa
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_OSA) == false){
							in = in.substring(0, position) + "oso";
						}
					}
				break;
				}
				position = STOP;
			break;
			case 7:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in ica
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_ICA) == false){
							in = in.substring(0, position) + "ico";
						}
					}
					position = STOP;
				break;
				case 'a':
					state = 16;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 8:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in ada
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.FEMININE_ADA) == false){
							in = in.substring(0, position) + "ado";
						}
					}
				break;
				case 'i':
					//rule for word ending in ida
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_IDA) == false){
							in = in.substring(0, position) + "ido";
						}
					}
				break;
				case 'í':
					//rule for word ending in ída
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_IIDA) == false){
							in = in.substring(0, position) + "ído";
						}
					}
				break;
				}
				position = STOP;
			break;
			case 9:
				if(in.charAt(position) == 'i'){
					//rule for word ending in ima
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_IMA) == false){
							in = in.substring(0, position) + "imo";
						}
					}
				}
				position = STOP;
			break;
			case 10:
				if(in.charAt(position) == 'i'){
					//rule for word ending in iva
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_IVA) == false){
							in = in.substring(0, position) + "ivo";
						}
					}
				}
				position = STOP;
			break;
			case 13:
				if(in.charAt(position) == 'i'){
					//rule for word ending in inha
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_INHA) == false){
							in = in.substring(0, position) + "inho";
						}
					}
				}
				position = STOP;
			break;
			case 16:
				if(in.charAt(position) == 'í'){
					//rule for word ending in íaca
					if(position >= 2){
						in = in.substring(0, position) + "íaco";
					}
				}
				position = STOP;
			break;
			case 23:
				if(in.charAt(position) == 'e'){
					//rule for word ending in eira
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.FEMININE_EIRA) == false){
							in = in.substring(0, position) + "eiro";
						}
					}
				}
				position = STOP;
			break;
			}
		}
		return in;
	}
	
	public String degreeReduction(String in){

		int position = in.length() - 1;
		int state = 0;
		boolean accept = false;

		while(position >= 0){
			switch(state){
			case 0:
				switch(in.charAt(position)){
				case 'a':
					state = 1;
					position--;
				break;
				case 'o':
					state = 4;
					position--;
				break;
				case 'z':
					state = 36;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 1:
				switch(in.charAt(position)){
				case 'ç':
					state = 2;
					position--;
				break;
				case 'r':
					state = 33;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 2:
				if(in.charAt(position) == 'u'){
					//rule for word ending in uça
					if(position >= 3){
						in = in.substring(0, position);
					}
				}
				position = STOP;
			break;
			case 4:
				switch(in.charAt(position)){
				case 'm':
					state = 5;
					position--;
				break;
				case 'h':
					state = 6;
					position--;
				break;
				case 'ã':
					state = 7;
					position--;
				break;
				case 'ç':
					state = 8;
					position--;
				break;
				case 'i':
					state = 41;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 5:
				if(in.charAt(position) == 'i'){
					state = 9;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 6:
				if(in.charAt(position) == 'n'){
					state = 10;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 7:
				if(accept){
					//rule for word ending in ão
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.DEGREE_AO) == false){
							in = in.substring(0, position);
						}
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'd':
						state = 44;
						position--;
					break;
					case 'h':
						state = 11;
						position--;
					break;
					case 'z':
						//rule for word ending in zão
						if(RSLPException.isException(in, RSLPException.DEGREE_ZAO) == false){
							if(position >= 1){
								in = in.substring(0, position);
							}
						}
						position = STOP;
					break;
					default:
						//rule for word ending in ão
						if(position >= 2){
							if(RSLPException.isException(in, RSLPException.DEGREE_AO) == false){
								in = in.substring(0, position + 1);
							}
						}
						position = STOP;
					break;
					}
				}
			break;
			case 8:
				if(in.charAt(position) == 'a'){
					//rule for word ending in aço
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.DEGREE_ACO) == false){
							in = in.substring(0, position);
						}
					}
				}
				position = STOP;
			break;
			case 9:
				switch(in.charAt(position)){
				case 's':
					state = 13;
					position--;
				break;
				case 'r':
					state = 14;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 10:
				if(in.charAt(position) == 'i'){
					state = 15;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 11:
				if(in.charAt(position) == 'l'){
					state = 16;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 13:
				if(in.charAt(position) == 's'){
					state = 17;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 14:
				if(in.charAt(position) == 'r'){
					state = 18;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 15:
				if(accept){
					//rule for word ending in inho
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.DEGREE_INHO) == false){
							in = in.substring(0, position + 1);
						}
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'z':
						//rule for word ending in zinho
						if(position >= 1){
							in = in.substring(0, position);
						}
						position = STOP;
					break;
					case 'u':
						state = 20;
						position--;
					break;
					case 'd':
						state = 21;
						position--;
					break;
					default:
						//rule for word ending in inho
						if(position >= 2){
							if(RSLPException.isException(in, RSLPException.DEGREE_INHO) == false){
								in = in.substring(0, position + 1);
							}
						}
						position = STOP;
					break;
					}
				}
			break;
			case 16:
				//rule for word ending in alhão
				if(in.charAt(position) == 'a'){
					if(position >= 3){
						in = in.substring(0, position);
					}
				}
				position = STOP;
			break;
			case 17:
				if(in.charAt(position) == 'í'){
					state = 23;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 18:
				if(in.charAt(position) == 'é'){
					//rule for word ending in érrimo
					if(position >= 3){
						in = in.substring(0, position);
					}
				}
				position = STOP;
			break;
			case 20:
				if(in.charAt(position) == 'q'){
					//rule for word ending in quinho
					if(position >= 3){
						in = in.substring(0, position) + "c";
					}
				}else{
					//rule for word ending in uinho
					if(position >= 3){
						in = in.substring(0, position + 1);
					}
				}
				position = STOP;
			break;
			case 21:
				if(in.charAt(position) == 'a'){
					//rule for word ending in adinho
					if(position >= 2){
						in = in.substring(0, position);
					}
					position = STOP;
				}else{
					state = 15;
					position += 1;
					accept = true;
				}
			break;
			case 23:
				if(accept){
					//rule for word ending in íssimo
					if(position >= 2){
						in = in.substring(0, position + 1);
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'd':
						//rule for word ending in díssimo
						if(position >= 4){
							in = in.substring(0, position);
						}
						position = STOP;
					break;
					case 'l':
						state = 28;
						position--;
					break;
					default:
						//rule for word ending in íssimo
						if(position >= 2){
							in = in.substring(0, position + 1);
						}
						position = STOP;
					break;
					}
				}
			break;
			case 28:
				if(in.charAt(position) == 'i'){
					state = 29;
					position--;
				}else{
					state = 23;
					position += 1;
					accept = true;
				}
			break;
			case 29:
				if(in.charAt(position) == 'b'){
					state = 30;
					position--;
				}else{
					state = 23;
					position += 2;
					accept = true;
				}
			break;
			case 30:
				if(in.charAt(position) == 'a'){
					//rule for word ending in abilíssimo
					if(position >= 4){
						in = in.substring(0, position);
					}
					position = STOP;
				}else{
					state = 23;
					position += 3;
					accept = true;
				}
			break;
			case 33:
				if(in.charAt(position) == 'r'){
					state = 34;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 34:
				if(in.charAt(position) == 'a'){
					//rule for word ending in arra
					if(position >= 2){
						in = in.substring(0, position);
					}
				}
				position = STOP;
			break;
			case 36:
				if(in.charAt(position) == 'a'){
					state = 37;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 37:
				if(in.charAt(position) == 'r'){
					state = 38;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 38:
				if(in.charAt(position) == 'r'){
					state = 39;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 39:
				if(in.charAt(position) == 'a'){
					//rule for word ending in arraz
					if(position >= 3){
						in = in.substring(0, position);
					}
				}
				position = STOP;
			break;
			case 41:
				if(in.charAt(position) == 'z'){
					state = 42;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 42:
				if(in.charAt(position) == 'á'){
					//rule for word ending in ázio
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.DEGREE_AZIO) == false){
							in = in.substring(0, position);
						}
					}
				}
				position = STOP;
			break;
			case 44:
				if(in.charAt(position) == 'a'){
					//rule for word ending in adão
					if(position >= 3){
						in = in.substring(0, position);
					}
				}
				position = STOP;
			break;
			}
		}
		return in;
	}
	
	public String adverbReduction(String in){
		//rule for word ending in mente
		if(in.endsWith("mente") == true){
			if(RSLPException.isException(in, RSLPException.ADVERB_MENTE) == false){
				return in.replace("mente", "");
			}
		}
		return in;
	}
	
}
