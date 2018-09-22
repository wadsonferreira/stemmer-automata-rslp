/* --------------------------------------------------------------------------------- */
/*                              Author: Wadson Ferreira                              */
/*                            wadson.ferreira@outlook.com                            */
/*                                                                                   */
/*               **     This banner notice must not be removed      **               */
/* --------------------------------------------------------------------------------- */
/*  Copyright(c) 2015, Wadson Ferreira                                               */
/*  All rights reserved.                                                             */
/*                                                                                   */
/*  Redistribution and use in source and binary forms, with or without               */
/*  modification, are permitted provided that the following conditions are met :     */
/*                                                                                   */
/*  1. Redistributions of source code must retain the above copyright notice, this   */
/*     list of conditions and the following disclaimer.                              */
/*  2. Redistributions in binary form must reproduce the above copyright notice,     */
/*     this list of conditions and the following disclaimer in the documentation     */
/*     and / or other materials provided with the distribution.                      */
/*                                                                                   */
/*  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND  */
/*  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED    */
/*  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE           */
/*  DISCLAIMED.IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR   */
/*  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES   */
/*  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;     */
/*  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND      */
/*  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT       */
/*  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS    */
/*  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                     */
/*                                                                                   */
/*  The views and conclusions contained in the software and documentation are those  */
/*  of the authors and should not be interpreted as representing official policies,  */
/*  either expressed or implied, of the FreeBSD Project.                             */
/* --------------------------------------------------------------------------------- */
/* File: RSLPAutomata.java                                                           */
/* --------------------------------------------------------------------------------- */

package stemmer.rslp;

/**
 * Implements the RSLP algorithm in the automata based version described in the <a href="https://dl.acm.org/citation.cfm?id=2952670">Assessing the Efficiency of Suffix Stripping Approaches for Portuguese Stemming</a>
 * 
 * @version 0.0.1
 * @author Wadson Ferreira
 *
 */
public class RSLPAutomata {
	
	/** Constant to inform that IS NOT to remove accents from the processed word */
	public static final boolean KEEP_ACCENTS = true;
	
	/** Constant to inform that IS to remove accents from the processed word */
	public static final boolean REMOVE_ACCENTS = false;
	
	/** Informs if  some suffix was remove after noun reduction or verb reduction */
	private boolean suffixRemoved = false;
	
	/** Constant used to stop the searching on the given word */
	private static final int STOP = -1;
	
	/**
	 * Applies the stemming process using automatas
	 * 
	 * @param in Word to be processed
	 * @param accents Receives one of the class constant to execute or not the remove accents step
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
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
	
	/**
	 * Executes the plural reduction step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
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
	
	/**
	 * Executes the feminine reduction step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
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
	
	/**
	 * Executes the degree reduction step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
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
	
	/**
	 * Executes the adverb reduction step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
	public String adverbReduction(String in){
		//rule for word ending in mente
		if(in.endsWith("mente") == true){
			if(RSLPException.isException(in, RSLPException.ADVERB_MENTE) == false){
				return in.replace("mente", "");
			}
		}
		return in;
	}

	/**
	 * Executes the noun reduction step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
	public String nounReduction(String in){

		int position = in.length() - 1;
		int state = 1;
		boolean accept = false;

		while(position >= 0){
			switch(state){
			case 1:
				switch(in.charAt(position)){
				case 'a':
					state = 2;
					position--;
				break;
				case 'e':
					state = 34;
					position--;
				break;
				case 'o':
					state = 71;
					position--;
				break;
				case 'ç':
					state = 27;
					position--;
				break;
				case 'm':
					state = 67;
					position--;
				break;
				case 'r':
					state = 121;
					position--;
				break;
				case 's':
					state = 128;
					position--;
				break;
				case 'z':
					state = 130;
					position--;
				break;
				case 'l':
					state = 50;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 2:
				switch(in.charAt(position)){
				case 't':
					state = 3;
					position--;
				break;
				case 'i':
					state = 4;
					position--;
				break;
				case 'r':
					state = 126;
					position--;
				break;
				case 'z':
					state = 5;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 3:
				if(in.charAt(position) == 's'){
					state = 6;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 4:
				switch(in.charAt(position)){
				case 'r':
					state = 7;
					position--;
				break;
				case 'c':
					state = 8;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 5:
				if(in.charAt(position) == 'e'){
					//rule for word ending in eza
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 6:
				if(in.charAt(position) == 'i'){
					state = 10;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 7:
				switch(in.charAt(position)){
				case 'o':
					//rule for word ending in oria
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_ORIA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				case 'ó':
					state = 11;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 8:
				if(in.charAt(position) == 'n'){
					state = 12;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 10:
				if(accept){
					//rule for word ending in ista
					if(position >= 3){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'l':
						state = 14;
						position--;
					break;
					case 'n':
						state = 15;
						position--;
					break;
					default:
						//rule for word ending in ista
						if(position >= 3){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					break;
					}
				}
			break;
			case 11:
				if(in.charAt(position) == 't'){
					state = 21;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 12:
				switch(in.charAt(position)){
				case 'ê':
					//rule for word ending in ência
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'â':
					//rule for word ending in ância
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_ANCIA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				}
				position = STOP;
			break;
			case 14:
				if(in.charAt(position) == 'a'){
					state = 16;
					position--;
				}else{
					state = 10;
					position += 1;
					accept = true;
				}
			break;
			case 15:
				if(in.charAt(position) == 'o'){
					state = 134;
					position--;
				}else{
					state = 10;
					position += 1;
					accept = true;
				}
			break;
			case 16:
				if(accept){
					//rule for word ending in alista
					if(position >= 4){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'i'){
						state = 17;
						position--;
					}else{
						//rule for word ending in alista
						if(position >= 4){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					}
				}
			break;
			case 17:
				if(in.charAt(position) == 'c'){
					state = 18;
					position--;
				}else{
					state = 16;
					position += 1;
					accept = true;
				}
			break;
			case 18:
				if(in.charAt(position) == 'n'){
					state = 19;
					position--;
				}else{
					state = 16;
					position += 2;
					accept = true;
				}
			break;
			case 19:
				if(in.charAt(position) == 'e'){
					//rule for word ending in encialista
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 16;
					position += 3;
					accept = true;
				}
			break;
			case 21:
				if(in.charAt(position) == 'a'){
					//rule for word ending in atória
					if(position >= 4){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 24:
				if(in.charAt(position) == 'c'){
					state = 25;
					position--;
				}else{
					state = 10;
					position += 3;
					accept = true;
				}
			break;
			case 25:
				if(in.charAt(position) == 'i'){
					//rule for word ending in icionista
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}else{
					//rule for word ending in cionista
					if(position >= 4){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 27:
				switch(in.charAt(position)){
				case 'a':
					state = 28;
					position--;
				break;
				case 'i':
					//rule for word ending in iç
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_IC) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 28:
				if(accept){
					//rule for word ending in aç
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_AC) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'z'){
						state = 30;
						position--;
					}else{
						//rule for word ending in aç
						if(position >= 2){
							if(RSLPException.isException(in, RSLPException.NOUN_AC) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 30:
				if(in.charAt(position) == 'i'){
					state = 31;
					position--;
				}else{
					state = 28;
					position += 1;
					accept = true;
				}
			break;
			case 31:
				if(accept){
					//rule for word ending in izaç
					if(position >= 4){
						if(RSLPException.isException(in, RSLPException.NOUN_IZAC) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'l'){
						state = 32;
						position--;
					}else{
						//rule for word ending in izaç
						if(position >= 4){
							if(RSLPException.isException(in, RSLPException.NOUN_IZAC) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 32:
				if(in.charAt(position) == 'a'){
					//rule for word ending in alizaç
					if(position >= 4){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 31;
					position += 1;
					accept = true;
				}
			break;
			case 34:
				switch(in.charAt(position)){
				case 'd':
					state = 35;
					position--;
				break;
				case 't':
					state = 36;
					position--;
				break;
				case 'c':
					state = 37;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 35:
				if(in.charAt(position) == 'a'){
					state = 39;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 36:
				if(in.charAt(position) == 'n'){
					state = 38;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 37:
				if(in.charAt(position) == 'i'){
					state = 64;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 38:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in ante
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.NOUN_ANTE) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'e':
					//rule for word ending in ente
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_ENTE) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				}
				position = STOP;
			break;
			case 39:
				if(in.charAt(position) == 'd'){
					state = 42;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 42:
				if(in.charAt(position) == 'i'){
					state = 43;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 43:
				if(accept){
					//rule for word ending in idade
					if(position >= 4){
						if(RSLPException.isException(in, RSLPException.NOUN_IDADE) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'v':
						state = 44;
						position--;
					break;
					case 'l':
						state = 46;
						position--;
					break;
					default:
						//rule for word ending in idade
						if(position >= 4){
							if(RSLPException.isException(in, RSLPException.NOUN_IDADE) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					break;
					}
				}
			break;
			case 44:
				if(in.charAt(position) == 'i'){
					//rule for word ending in ividade
					if(position >= 4){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 43;
					position += 1;
					accept = true;
				}
			break;
			case 46:
				if(in.charAt(position) == 'i'){
					state = 47;
					position--;
				}else{
					state = 43;
					position += 1;
					accept = true;
				}
			break;
			case 47:
				if(in.charAt(position) == 'b'){
					state = 48;
					position--;
				}else{
					state = 43;
					position += 2;
					accept = true;
				}
			break;
			case 48:
				if(in.charAt(position) == 'a'){
					//rule for word ending in abilidade
					if(position >= 4){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 43;
					position += 3;
					accept = true;
				}
			break;
			case 50:
				switch(in.charAt(position)){
				case 'a':
					state = 51;
					position--;
				break;
				case 'e':
					state = 54;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 51:
				if(accept){
					//rule for word ending in al
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_AL) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'u':
						//rule for word ending in ual
						if(position >= 2){
							if(RSLPException.isException(in, RSLPException.NOUN_UAL) == false){
								in = in.substring(0, position);
								suffixRemoved = true;
							}
						}
						position = STOP;
					break;
					case 'i':
						state = 52;
						position--;
					break;
					case 'n':
						state = 57;
						position--;
					break;
					default:
						//rule for word ending in al
						if(position >= 3){
							if(RSLPException.isException(in, RSLPException.NOUN_AL) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					break;
					}
				}
			break;
			case 52:
				if(accept){
					//rule for word ending in ial
					if(position >= 2){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'c'){
						state = 59;
						position--;
					}else{
						//rule for word ending in ial
						if(position >= 2){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					}
				}
			break;
			case 54:
				if(in.charAt(position) == 'v'){
					state = 55;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 55:
				switch(in.charAt(position)){
				case 'á':
					//rule for word ending in ável
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.NOUN_AVEL) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'í':
					//rule for word ending in ível
					if(position >= 4){
						if(RSLPException.isException(in, RSLPException.NOUN_IVEL) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				}
				position = STOP;
			break;
			case 57:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in inal
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_INAL) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				case 'o':
					state = 62;
					position--;
				break;
				default:
					state = 51;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 59:
				if(in.charAt(position) == 'n'){
					state = 60;
					position--;
				}else{
					state = 52;
					position += 1;
					accept = true;
				}
			break;
			case 60:
				if(in.charAt(position) == 'e'){
					//rule for word ending in encial
					if(position >= 4){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 52;
					position += 2;
					accept = true;
				}
			break;
			case 62:
				if(in.charAt(position) == 'i'){
					//rule for word ending in ional
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 51;
					position += 2;
					accept = true;
				}
			break;
			case 64:
				if(accept){
					//rule for word ending in ice
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_ICE) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'u'){
						state = 65;
						position--;
					}else{
						//rule for word ending in ice
						if(position >= 3){
							if(RSLPException.isException(in, RSLPException.NOUN_ICE) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 65:
				if(in.charAt(position) == 'q'){
					//rule for word ending in quice
					if(position >= 3){
						in = in.substring(0, position) + "c";
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 64;
					position += 1;
					accept = true;
				}
			break;
			case 67:
				if(in.charAt(position) == 'e'){
					state = 68;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 68:
				if(in.charAt(position) == 'g'){
					state = 69;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 69:
				if(in.charAt(position) == 'a'){
					//rule for word ending in agem
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_AGEM) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			case 71:
				switch(in.charAt(position)){
				case 'n':
					state = 119;
					position--;
				break;
				case 't':
					state = 72;
					position--;
				break;
				case 'd':
					state = 73;
					position--;
				break;
				case 'v':
					state = 74;
					position--;
				break;
				case 'r':
					state = 75;
					position--;
				break;
				case 's':
					state = 76;
					position--;
				break;
				case 'm':
					state = 77;
					position--;
				break;
				case 'i':
					state = 78;
					position--;
				break;
				case 'c':
					state = 79;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 72:
				if(in.charAt(position) == 'n'){
					state = 80;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 73:
				switch(in.charAt(position)){
				case 'a':
					state = 81;
					position--;
				break;
				case 'i':
					//rule for word ending in ido
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_IDO) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 74:
				if(in.charAt(position) == 'i'){
					state = 82;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 75:
				switch(in.charAt(position)){
				case 'u':
					state = 84;
					position--;
				break;
				case 'i':
					state = 85;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 76:
				if(in.charAt(position) == 'o'){
					//rule for word ending in oso
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_OSO) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			case 77:
				if(in.charAt(position) == 's'){
					state = 105;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 78:
				if(in.charAt(position) == 'r'){
					state = 107;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 79:
				switch(in.charAt(position)){
				case 'i':
					state = 110;
					position--;
				break;
				case 's':
					state = 115;
					position--;
				break;
				case 'a':
					state = 117;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 80:
				if(in.charAt(position) == 'e'){
					state = 88;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 81:
				if(accept){
					//rule for word ending in ado
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.NOUN_ADO) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'z'){
						state = 93;
						position--;
					}else{
						//rule for word ending in ado
						if(position >= 1){
							if(RSLPException.isException(in, RSLPException.NOUN_ADO) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 82:
				if(in.charAt(position) == 't'){
					state = 98;
					position--;
				}else{
					//rule for word ending in ivo
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_IVO) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}
			break;
			case 84:
				if(in.charAt(position) == 'o'){
					state = 100;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 85:
				if(in.charAt(position) == 'e'){
					state = 86;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 86:
				if(accept){
					//rule for word ending in eiro
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_EIRO) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'u'){
						state = 103;
						position--;
					}else{
						//rule for word ending in eiro
						if(position >= 2){
							if(RSLPException.isException(in, RSLPException.NOUN_EIRO) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 88:
				if(in.charAt(position) == 'm'){
					state = 89;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 89:
				switch(in.charAt(position)){
				case 'a':
					state = 90;
					position--;
				break;
				case 'i':
					//rule for word ending in imento
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 90:
				if(in.charAt(position) == 'i'){
					//rule for word ending in iamento
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}else{
					//rule for word ending in amento
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_AMENTO) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			case 93:
				if(in.charAt(position) == 'i'){
					state = 94;
					position--;
				}else{
					state = 81;
					position += 1;
					accept = true;
				}
			break;
			case 94:
				if(accept){
					//rule for word ending in izado
					if(position >= 4){
						if(RSLPException.isException(in, RSLPException.NOUN_IZADO) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'l':
						state = 95;
						position--;
					break;
					case 't':
						state = 96;
						position--;
					break;
					default:
						//rule for word ending in izado
						if(position >= 4){
							if(RSLPException.isException(in, RSLPException.NOUN_IZADO) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					break;
					}
				}
			break;
			case 95:
				if(in.charAt(position) == 'a'){
					//rule for word ending in alizado
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 94;
					position += 1;
					accept = true;
				}
			break;
			case 96:
				if(in.charAt(position) == 'a'){
					//rule for word ending in atizado
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 94;
					position += 1;
					accept = true;
				}
			break;
			case 98:
				if(in.charAt(position) == 'a'){
					//rule for word ending in ativo
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_ATIVO) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				}else{
					//rule for word ending in tivo
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_TIVO) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			case 100:
				if(in.charAt(position) == 'd'){
					state = 101;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 101:
				if(in.charAt(position) == 'e'){
					//rule for word ending in edouro
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 103:
				if(in.charAt(position) == 'q'){
					//rule for word ending in queiro
					if(position >= 2){
						in = in.substring(0, position) + "c";
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 86;
					position += 1;
					accept = true;
				}
			break;
			case 105:
				if(in.charAt(position) == 'i'){
					//rule for word ending in ismo
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_ISMO) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			case 107:
				switch(in.charAt(position)){
				case 'á':
					//rule for word ending in ário
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.NOUN_ARIO) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'é':
					//rule for word ending in ério
					if(position >= 5){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 110:
				if(accept){
					//rule for word ending in ico
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_ICO) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 't'){
						state = 111;
						position--;
					}else{
						//rule for word ending in ico
						if(position >= 3){
							if(RSLPException.isException(in, RSLPException.NOUN_ICO) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 111:
				switch(in.charAt(position)){
				case 'á':
					//rule for word ending in ático
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 's':
					state = 112;
					position--;
				break;
				default:
					state = 110;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 112:
				if(in.charAt(position) == 'á'){
					//rule for word ending in ástico
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_ASTICO) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					state = 110;
					position += 2;
					accept = true;
				}
			break;
			case 115:
				if(in.charAt(position) == 'e'){
					//rule for word ending in esco
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 117:
				if(in.charAt(position) == 'í'){
					//rule for word ending in íaco
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 119:
				if(in.charAt(position) == 'a'){
					//rule for word ending in ano
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 121:
				if(in.charAt(position) == 'o'){
					state = 122;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 122:
				if(accept){
					//rule for word ending in or
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.NOUN_OR) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'd'){
						state = 123;
						position--;
					}else{
						//rule for word ending in or
						if(position >= 1){
							if(RSLPException.isException(in, RSLPException.NOUN_OR) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 123:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in ador
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					//rule for word ending in edor
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'i':
					//rule for word ending in idor
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_IDOR) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				default:
					state = 122;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 126:
				if(in.charAt(position) == 'u'){
					//rule for word ending in ura
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.NOUN_URA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			case 128:
				if(in.charAt(position) == 'ê'){
					//rule for word ending in ês
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 130:
				if(in.charAt(position) == 'e'){
					//rule for word ending in ez
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				}
				position = STOP;
			break;
			case 134:
				if(in.charAt(position) == 'i'){
					state = 24;
					position--;
				}else{
					state = 10;
					position += 2;
					accept = true;
				}
			break;
			}
		}
		return in;
	}
	
	/**
	 * Executes the verb reduction step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
	public String verbReduction(String in){

		int position = in.length() - 1;
		int state = 0;
		boolean accept = false;

		while(position >= 0){
			switch(state){
			case 0:
				switch(in.charAt(position)){
				case 'a':
					state = 4;
					position--;
				break;
				case 'e':
					state = 5;
					position--;
				break;
				case 'i':
					state = 2;
					position--;
				break;
				case 'o':
					state = 1;
					position--;
				break;
				case 'u':
					state = 8;
					position--;
				break;
				case 'r':
					state = 7;
					position--;
				break;
				case 'á':
					state = 6;
					position--;
				break;
				case 'm':
					state = 3;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 1:
				switch(in.charAt(position)){
				case 'm':
					state = 9;
					position--;
				break;
				case 'd':
					state = 32;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 2:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in ai
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					state = 44;
					position--;
				break;
				default:
					//rule for word ending in i
					if(position >= 2){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				}
			break;
			case 3:
				switch(in.charAt(position)){
				case 'a':
					state = 66;
					position--;	
				break;
				case 'e':
					state = 70;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 4:
				switch(in.charAt(position)){
				case 'i':
					state = 89;
					position--;
				break;
				case 'v':
					state = 96;
					position--;
				break;
				case 'r':
					state = 94;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 5:
				switch(in.charAt(position)){
				case 'r':
					state = 116;
					position--;
				break;
				case 'd':
					state = 100;
					position--;
				break;
				case 's':
					state = 103;
					position--;
				break;
				case 't':
					state = 106;
					position--;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 6:
				if(in.charAt(position) == 'r'){
					state = 120;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 7:
				switch(in.charAt(position)){
				case 'a':
					state = 124;
					position--;
				break;
				case 'e':
					//rule for word ending in er
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_ER) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				case 'i':
					//rule for word ending in ir
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_IR) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 8:
				switch(in.charAt(position)){
				case 'o':
					//rule for word ending in ou
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'e':
					//rule for word ending in eu
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_EU) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'i':
					//rule for word ending in iu
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 9:
				switch(in.charAt(position)){
				case 'a':
					state = 10;
					position--;
				break;
				case 'e':
					state = 14;
					position--;
				break;
				case 'r':
					state = 38;
					position--;
				break;
				case 'o':
					//rule for word ending in omo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'i':
					//rule for word ending in imo
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_IMO) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				default:
					position = STOP;
				break;
				}
			break;
			case 10:
				if(accept){
					//rule for word ending in amo
					if(position >= 1){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'í':
						state = 11;
						position--;
					break;
					case 'v':
						state = 26;
						position--;
					break;
					case 'r':
						state = 22;
						position--;
					break;
					default:
						//rule for word ending in amo
						if(position >= 1){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					break;
					}
				}
			break;
			case 11:
				if(accept){
					//rule for word ending in íamo
					if(position >= 2){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'r'){
						state = 12;
						position--;
					}else{
						//rule for word ending in íamo
						if(position >= 2){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					}
				}
			break;
			case 12:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in iríamo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					//rule for word ending in eríamo
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'a':
					//rule for word ending in aríamo
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 11;
					position += 1;
					accept = true;
				}
			break;
			case 14:
				if(accept){
					//rule for word ending in emo
					if(position >= 1){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 's':
						state = 15;
						position--;
					break;
					case 'r':
						state = 24;
						position--;
					break;
					default:
						//rule for word ending in emo
						if(position >= 1){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					break;
					}
				}
			break;
			case 15:
				if(in.charAt(position) == 's'){
					state = 16;
					position--;
				}else{
					state = 14;
					position += 1;
					accept = true;
				}
			break;
			case 16:
				switch(in.charAt(position)){
				case 'ê':
					//rule for word ending in êssemo
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'í':
					//rule for word ending in íssemo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'á':
					//rule for word ending in ássemo
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 14;
					position += 2;
					accept = true;
				break;
				}
			break;
			case 22:
				switch(in.charAt(position)){
				case 'í':
					//rule for word ending in íramo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'ê':
					//rule for word ending in êramo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'á':
					//rule for word ending in áramo
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 10;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 24:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in aremo
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					//rule for word ending in eremo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'i':
					//rule for word ending in iremo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 14;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 26:
				if(in.charAt(position) == 'á'){
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 10;
					position += 1;
					accept = true;
				}
			break;
			case 32:
				if(in.charAt(position) == 'n'){
					state = 33;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 33:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in ando
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'e':
					//rule for word ending in endo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'i':
					//rule for word ending in indo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'o':
					//rule for word ending in ondo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 38:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in armo
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'e':
					//rule for word ending in ermo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'i':
					//rule for word ending in irmo
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 44:
				if(accept){
					//rule for word ending in ei
					if(position >= 2){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'r':
						state = 45;
						position--;
					break;
					case 's':
						state = 51;
						position--;
					break;
					case 'v':
						state = 59;
						position--;
					break;
					case 'u':
						//rule for word ending in uei
						if(position >= 2){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
						position = STOP;
					break;
					case 'í':
						state = 47;
						position--;
					break;
					default:
						//rule for word ending in ei
						if(position >= 2){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					break;
					}
				}
			break;
			case 45:
				switch(in.charAt(position)){
				case 'ê':
					//rule for word ending in êrei
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'a':
					//rule for word ending in arei
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					//rule for word ending in erei
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'á':
					//rule for word ending in árei
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'í':
					//rule for word ending in írei
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_IREI) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				default:
					state = 44;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 47:
				if(accept){
					if(position >= 2){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'r'){
						state = 49;
						position--;
					}else{
						if(position >= 2){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					}
				}
			break;
			case 49:
				switch(in.charAt(position)){
				case 'i':
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'a':
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 47;
					position += 1;
					accept = true;
				break;
				}
				
			break;
			case 51:
				if(in.charAt(position) == 's'){
					state = 52;
					position--;
				}else{
					state = 44;
					position += 1;
					accept = true;
				}
			break;
			case 52:
				switch(in.charAt(position)){
				case 'á':
					//rule for word ending in ássei
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'ê':
					//rule for word ending in êssei
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'í':
					//rule for word ending in íssei
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 44;
					position += 2;
					accept = true;
				break;
				}
			break;
			case 59:
				if(in.charAt(position) == 'á'){
					//rule for word ending in ávei
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					state = 44;
					position += 1;
					accept = true;
				}
			break;
			case 66:
				if(accept){
					//rule for word ending in am
					if(position >= 1){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 'i':
						state = 67;
						position--;
					break;
					case 'r':
						state = 78;
						position--;
					break;
					case 'v':
						state = 82;
						position--;
					break;
					default:
						//rule for word ending in am
						if(position >= 1){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
						position = STOP;
					break;
					}
				}
			break;
			case 67:
				//rule for word ending in iam
				if(accept){
					if(position >= 2){
						in = in.substring(0, position + 1);
						suffixRemoved = true;
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'r'){
						state = 68;
						position--;
					}else{
						//rule for word ending in iam
						if(position >= 2){
							if(RSLPException.isException(in, RSLPException.VERB_IAM) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 68:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in iriam
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					//rule for word ending in eriam
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'a':
					//rule for word ending in ariam
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 67;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 70:
				if(accept){
					//rule for word ending in em
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_EM) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					switch(in.charAt(position)){
					case 's':
						state = 71;
						position--;
					break;
					case 'r':
						state = 80;
						position--;
					break;
					default:
						//rule for word ending in em
						if(position >= 1){
							if(RSLPException.isException(in, RSLPException.VERB_EM) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					break;
					}
				}
			break;
			case 71:
				if(in.charAt(position) == 's'){
					state = 72;
					position--;
				}else{
					state = 70;
					position += 1;
					accept = true;
				}
			break;
			case 72:
				switch(in.charAt(position)){
				case 'e':
					//rule for word ending in essem
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'a':
					//rule for word ending in assem
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'i':
					//rule for word ending in issem
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 70;
					position += 2;
					accept = true;
				break;
				}
			break;
			case 78:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in iram
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'a':
					//rule for word ending in aram
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					//rule for word ending in eram
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'í':
					//rule for word ending in íram
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 66;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 80:
				switch(in.charAt(position)){
				case 'a':
					//rule for word ending in arem
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'e':
					//rule for word ending in erem
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'i':
					//rule for word ending in irem
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_IREM) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				break;
				default:
					state = 70;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 82:
				if(in.charAt(position) == 'a'){
					//rule for word ending in avam
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_AVAM) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					state = 66;
					position += 1;
					accept = true;
				}
			break;
			case 89:
				if(accept){
					//rule for word ending in ia
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_IA) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
					position = STOP;
				}else{
					if(in.charAt(position) == 'r'){
						state = 90;
						position--;
					}else{
						//rule for word ending in ia
						if(position >= 2){
							if(RSLPException.isException(in, RSLPException.VERB_IA) == false){
								in = in.substring(0, position + 1);
								suffixRemoved = true;
							}
						}
						position = STOP;
					}
				}
			break;
			case 90:
				switch(in.charAt(position)){
				case 'e':
					//rule for word ending in eria
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'a':
					//rule for word ending in aria
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				case 'i':
					//rule for word ending in iria
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
					position = STOP;
				break;
				default:
					state = 89;
					position += 1;
					accept = true;
				break;
				}
			break;
			case 94:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in ira
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_IRA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'a':
					//rule for word ending in ara
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_ARA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'e':
					//rule for word ending in era
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_ERA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				}
				position = STOP;
			break;
			case 96:
				if(in.charAt(position) == 'a'){
					//rule for word ending in ava
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_AVA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			case 100:
				if(in.charAt(position) == 'r'){
					state = 101;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 101:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in irde
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'e':
					//rule for word ending in erde
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'a':
					//rule for word ending in arde
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 103:
				if(in.charAt(position) == 's'){
					state = 104;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 104:
				switch(in.charAt(position)){
				case 'e':
					//rule for word ending in esse
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'i':
					//rule for word ending in isse
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'a':
					//rule for word ending in asse
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 106:
				if(in.charAt(position) == 's'){
					state = 107;
					position--;
				}else{
					position = STOP;
				}
			break;
			case 107:
				switch(in.charAt(position)){
				case 'e':
					//rule for word ending in este
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_ESTE) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'a':
					//rule for word ending in aste
					if(position >= 1){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'i':
					//rule for word ending in iste
					if(position >= 3){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 116:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in ire
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_IRE) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'a':
					//rule for word ending in are
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_ARE) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'e':
					//rule for word ending in ere
					if(position >= 2){
						if(RSLPException.isException(in, RSLPException.VERB_ERE) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				}
				position = STOP;
			break;
			case 120:
				switch(in.charAt(position)){
				case 'i':
					//rule for word ending in irá
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				case 'a':
					//rule for word ending in ará
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_ARAA) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				break;
				case 'e':
					//rule for word ending in erá
					if(position >= 2){
						in = in.substring(0, position);
						suffixRemoved = true;
					}
				break;
				}
				position = STOP;
			break;
			case 124:
				if(in.charAt(position) == 'e'){
					//rule for word ending in ear
					if(position >= 3){
						if(RSLPException.isException(in, RSLPException.VERB_EAR) == false){
							in = in.substring(0, position);
							suffixRemoved = true;
						}
					}
				}else{
					//rule for word ending in ar
					if(position >= 1){
						if(RSLPException.isException(in, RSLPException.VERB_AR) == false){
							in = in.substring(0, position + 1);
							suffixRemoved = true;
						}
					}
				}
				position = STOP;
			break;
			}
		}
		return in;
	}
	
	/**
	 * Executes the vowel remove step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
	public String removeVowel(String in){
		if(in.length() > 2 && RSLPException.isException(in, RSLPException.VOWEL) == false){
			if(in.endsWith("a") || in.endsWith("e") || in.endsWith("o")){
				return in.substring(0, in.length() - 1);
			}
		}
		return in;
	}

	/**
	 * Executes the accents remove step for the given word
	 * 
	 * @param in Word to be processed
	 * @return Processed word. It can be the original word if none of the reductions was applied or a modificated word
	 */
	public String removeAccents(String in){
		return in.replaceAll("à|ã|á|â", "a").replaceAll("é|ê", "e").replaceAll("í", "i").replaceAll("ó|õ|ô", "o").replaceAll("ú", "");
	}

}
