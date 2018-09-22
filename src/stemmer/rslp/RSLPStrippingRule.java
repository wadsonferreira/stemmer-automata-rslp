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
/* File: RSLPStrippingRule.java                                                      */
/* --------------------------------------------------------------------------------- */

package stemmer.rslp;

/**
 * Stores a rule of the RSLP algorithm and one example of a word and its result after the rule is applied.
 * The rules was extracted from the appendix of 'A Stemming Algorithm for the Portuguese Language'
 * 
 * @version 0.0.1
 * @author Wadson Ferreira
 *
 */
public final class RSLPStrippingRule {

	/** Suffix to be matched */
	private String suffix;
	
	/** The minimum size of the residual word after the suffix removing */
	private int stemSize;
	
	/** A string to be attached in the residual word after the suffix removing */
	private String replacement;
	
	/** List of word that can not be processed by the rule */
	private String[] exceptions;
	
	/** Example of word that can be processed by the rule */
	private String example;
	
	/** Residual word for the word stored in the 'example' */
	private String exampleResult;
	
	/**
	 * Store a RSLP rule
	 * 
	 * @param suffix Suffix to be matched
	 * @param stemSize The minimum size of the residual word after the suffix removing
	 * @param replacement A string to be attached in the residual word after the suffix removing
	 * @param exceptions List of word that can not be processed by the rule. Use the lists of {@link RSLPException}
	 * @param example Example of word that can be processed by the rule
	 * @param exampleResult Residual word for the word stored in the 'example'
	 */
	public RSLPStrippingRule(String suffix, int stemSize, String replacement, String[] exceptions, String example, String exampleResult) {
		
		this.suffix = suffix;
		this.stemSize = stemSize;
		this.replacement = replacement;
		this.exceptions = exceptions;
		this.example = example;
		this.exampleResult = exampleResult;
		
	}
	
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
