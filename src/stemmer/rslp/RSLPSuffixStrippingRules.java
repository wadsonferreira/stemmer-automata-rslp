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
/* File: RSLPSuffixStrippingRules.java                                               */
/* --------------------------------------------------------------------------------- */

package stemmer.rslp;

/**
 * Stores the rules of the RSLP algorithm.
 * The rules was extracted from the appendix of 'A Stemming Algorithm for the Portuguese Language'
 * 
 * @version 0.0.1
 * @author Wadson Ferreira
 *
 */
public final class RSLPSuffixStrippingRules {
	
	/** Stores the rule to be used in the plural reduction step */
	public static final RSLPStrippingRule[] PLURAL_REDUCTION_RULES = {
		new RSLPStrippingRule("ns", 1, "m", null, "bons", "bom"),
		new RSLPStrippingRule("�es", 3, "�o", null, "bal�es", "bal�o"),
		new RSLPStrippingRule("�es", 1, "�o", RSLPException.PLURAL_AES, "capit�es","capit�o"),
		new RSLPStrippingRule("ais", 1, "al", RSLPException.PLURAL_AIS, "normais","normal"),
		new RSLPStrippingRule("�is", 2, "el", null, "pap�is","papel"),
		new RSLPStrippingRule("eis", 2, "el", RSLPException.PLURAL_EIS, "am�veis","am�vel"),
		new RSLPStrippingRule("�is", 2, "ol", null, "len��is","len�ol"),
		new RSLPStrippingRule("is", 2, "il", RSLPException.PLURAL_IS, "barris","barril"),
		new RSLPStrippingRule("les", 3, "l", null, "males","mal"),
		new RSLPStrippingRule("res", 3, "r", RSLPException.PLURAL_RES, "mares","mar"),
		new RSLPStrippingRule("s", 2, "", RSLPException.PLURAL_S, "casas","casa")
	};

	/** Stores the rule to be used in the feminine reduction step */
	public static final RSLPStrippingRule[] FEMININE_REDUCTION_RULES = {
		new RSLPStrippingRule("ona", 3, "�o", RSLPException.FEMININE_ONA, "chefona", "chef�o"),
		new RSLPStrippingRule("�", 2, "�o", RSLPException.FEMININE_AA, "vil�", "vil�o"),
		new RSLPStrippingRule("ora", 3, "or", null, "professora", "professor"),
		new RSLPStrippingRule("na", 4, "no", RSLPException.FEMININE_NA, "americana", "americano"),
		new RSLPStrippingRule("inha", 3, "inho", RSLPException.FEMININE_INHA, "sozinha", "sozinho"),
		new RSLPStrippingRule("esa", 3, "�s", RSLPException.FEMININE_ESA, "inglesa", "ingl�s"),
		new RSLPStrippingRule("osa", 3, "oso", RSLPException.FEMININE_OSA, "famosa", "famoso"),
		new RSLPStrippingRule("�aca", 3, "�aco", null, "man�aca", "man�aco"),
		new RSLPStrippingRule("ica", 3, "ico", RSLPException.FEMININE_ICA, "pr�tica", "pr�tico"),
		new RSLPStrippingRule("ada", 2, "ado", RSLPException.FEMININE_ADA, "cansada", "cansado"),
		new RSLPStrippingRule("ida", 3, "ido", RSLPException.FEMININE_IDA, "mantida", "mantido"),
		new RSLPStrippingRule("�da", 3, "ido", RSLPException.FEMININE_IIDA, "tra�da", "tra�do"),
		new RSLPStrippingRule("ima", 3, "imo", RSLPException.FEMININE_IMA, "prima", "primo"),
		new RSLPStrippingRule("iva", 3, "ivo", RSLPException.FEMININE_IVA, "passiva", "passivo"),
		new RSLPStrippingRule("eira", 3, "eiro", RSLPException.FEMININE_EIRA, "primeira", "primeiro")
	};
	
	/** Stores the rule to be used in the adverb reduction step */
	public static final RSLPStrippingRule[] ADVERB_REDUCTION_RULES = {
		new RSLPStrippingRule("mente", 4, "", RSLPException.ADVERB_MENTE, "felizmente", "feliz")
	};

	/** Stores the rule to be used in the degree reduction step */
	public static final RSLPStrippingRule[] DEGREE_REDUCTION_RULES = {
		new RSLPStrippingRule("d�ssimo", 5, "", null, "cansad�ssimo", "cansa"),  //different from the article
		new RSLPStrippingRule("abil�ssimo", 5, "", null, "admirabil�ssimo", "admir"), //the word given at the original article (amabil�ssimo) does not make sense because the stem size will be three
		new RSLPStrippingRule("�ssimo", 3, "", null, "fort�ssimo", "fort"),
		new RSLPStrippingRule("�rrimo", 4, "", null, "chiqu�rrimo", "chiqu"),
		new RSLPStrippingRule("zinho", 2, "", null, "pezinho", "pe"),
		new RSLPStrippingRule("quinho", 4, "c", null, "maluquinho", "maluc"),
		new RSLPStrippingRule("uinho", 4, "", null, "amiguinho", "amig"),
		new RSLPStrippingRule("adinho", 3, "", null, "cansadinho", "cans"), //different from the article
		new RSLPStrippingRule("inho", 3, "", RSLPException.DEGREE_INHO, "carrinho", "carr"),
		new RSLPStrippingRule("alh�o", 4, "", null, "grandalh�o", "grand"),
		new RSLPStrippingRule("u�a", 4, "", null, "dentu�a", "dent"),
		new RSLPStrippingRule("a�o", 4, "", RSLPException.DEGREE_ACO, "rica�o", "ric"),
		new RSLPStrippingRule("ad�o", 4, "", null, "cansad�o", "cans"),
		new RSLPStrippingRule("�zio", 3, "", RSLPException.DEGREE_AZIO, "corp�zio", "corp"),
		new RSLPStrippingRule("arraz", 4, "", null, "pratarraz", "prat"),
		new RSLPStrippingRule("arra", 3, "", null, "bocarra", "boc"),
		new RSLPStrippingRule("z�o", 2, "", RSLPException.DEGREE_ZAO, "calorz�o", "calor"),
		new RSLPStrippingRule("�o", 3, "", RSLPException.DEGREE_AO, "menin�o", "menin")
	};

	/** Stores the rule to be used in the noun reduction step */
	public static final RSLPStrippingRule[] NOUN_REDUCTION_RULES = {
		new RSLPStrippingRule("encialista", 4, "", null, "existencialista", "exist"),
		new RSLPStrippingRule("alista", 5, "", null, "minimalista", "minim"),
		new RSLPStrippingRule("agem", 3, "", RSLPException.NOUN_AGEM, "contagem", "cont"),
		new RSLPStrippingRule("iamento", 4, "", RSLPException.NOUN_AMENTO, "gerenciamento", "gerenc"),
		new RSLPStrippingRule("amento", 3, "", RSLPException.NOUN_AMENTO, "monitoramento", "monitor"),//different from the article
		new RSLPStrippingRule("imento", 3, "", null, "nascimento", "nasc"),
		new RSLPStrippingRule("alizado", 4, "", null, "comercializado", "comerci"),
		new RSLPStrippingRule("atizado", 4, "", null, "traumatizado", "traum"),
		new RSLPStrippingRule("izado", 5, "", RSLPException.NOUN_IZADO, "alfabetizado", "alfabet"),
		new RSLPStrippingRule("ativo", 4, "", RSLPException.NOUN_ATIVO, "associativo", "associ"),
		new RSLPStrippingRule("tivo", 4, "", RSLPException.NOUN_TIVO, "contraceptivo", "contracep"),
		new RSLPStrippingRule("ivo", 4, "", RSLPException.NOUN_IVO, "coercivo", "coerc"), //the word given at the original article (esportivo) does not make sense because it will be processed by the rule 'tivo'
		new RSLPStrippingRule("ado", 2, "", RSLPException.NOUN_ADO, "abalado", "abal"),
		new RSLPStrippingRule("ido", 3, "", RSLPException.NOUN_IDO, "impedido", "imped"),
		new RSLPStrippingRule("ador", 3, "", null, "ralador", "ral"),
		new RSLPStrippingRule("edor", 3, "", null, "entendedor", "entend"),
		new RSLPStrippingRule("idor", 4, "", RSLPException.NOUN_IDOR, "cumpridor", "cumpr"),
		new RSLPStrippingRule("at�ria", 5, "", null, "obrigat�ria", "obrig"),
		new RSLPStrippingRule("or", 2, "", RSLPException.NOUN_OR, "produtor", "produt"),
		new RSLPStrippingRule("abilidade", 5, "", null, "comparabilidade", "compar"),
		new RSLPStrippingRule("icionista", 4, "", null, "abolicionista", "abol"),
		new RSLPStrippingRule("cionista", 5, "", null, "intervencionista", "interven"),
		new RSLPStrippingRule("ional", 4, "", null, "profissional", "profiss"),
		new RSLPStrippingRule("�ncia", 3, "", null, "refer�ncia", "refer"),
		new RSLPStrippingRule("�ncia", 4, "", RSLPException.NOUN_ANCIA, "repugn�ncia", "repugn"),
		new RSLPStrippingRule("edouro", 3, "", null, "abatedouro", "abat"),
		new RSLPStrippingRule("queiro", 3, "c", null, "fofoqueiro", "fofoc"),
		new RSLPStrippingRule("eiro", 3, "", RSLPException.NOUN_EIRO, "brasileiro", "brasil"),
		new RSLPStrippingRule("oso", 3, "", RSLPException.NOUN_OSO, "gostoso", "gost"),
		new RSLPStrippingRule("aliza�", 5, "", null, "comercializa�", "comerci"),
		new RSLPStrippingRule("ismo", 3, "", RSLPException.NOUN_ISMO, "consumismo", "consum"),
		new RSLPStrippingRule("iza�", 5, "", RSLPException.NOUN_IZAC, "concretiza�", "concret"),
		new RSLPStrippingRule("a�", 3, "", RSLPException.NOUN_AC, "alega�", "aleg"),
		new RSLPStrippingRule("i�", 3, "", RSLPException.NOUN_IC, "aboli�", "abol"),
		new RSLPStrippingRule("�rio", 3, "", RSLPException.NOUN_ARIO, "anedot�rio", "anedot"),
		new RSLPStrippingRule("�rio", 6, "", null, "minist�rio", "minist"),
		new RSLPStrippingRule("�s", 4, "", null, "chin�s", "chin"),
		new RSLPStrippingRule("eza", 3, "", null, "beleza", "bel"),
		new RSLPStrippingRule("ez", 4, "", null, "rigidez", "rigid"),
		new RSLPStrippingRule("esco", 4, "", null, "parentesco", "parent"),
		new RSLPStrippingRule("ante", 2, "", RSLPException.NOUN_ANTE, "ocupante", "ocup"),
		new RSLPStrippingRule("�stico", 4, "", RSLPException.NOUN_ASTICO, "bomb�stico", "bomb"),
		new RSLPStrippingRule("�tico", 3, "", null, "problem�tico", "problem"),
		new RSLPStrippingRule("ico", 4, "", RSLPException.NOUN_ICO, "pol�mico", "pol�m"),
		new RSLPStrippingRule("ividade", 5, "", null, "produtividade", "produt"),
		new RSLPStrippingRule("idade", 5, "", RSLPException.NOUN_IDADE, "profundidade", "profund"),
		new RSLPStrippingRule("oria", 4, "", RSLPException.NOUN_ORIA, "aposentadoria", "aposentad"),
		new RSLPStrippingRule("encial", 5, "", null, "existencial", "exist"),
		new RSLPStrippingRule("ista", 4, "", null, "vigarista", "vigar"), //the word given at the original article (artista) does not make sense because the stem size will be three
		new RSLPStrippingRule("quice", 4, "c", null, "maluquice", "maluc"),
		new RSLPStrippingRule("ice", 4, "", RSLPException.NOUN_ICE, "chatice", "chat"),
		new RSLPStrippingRule("�aco", 3, "", null, "demon�aco", "demon"),
		new RSLPStrippingRule("ente", 4, "", RSLPException.NOUN_ENTE, "decorrente", "decorr"),
		new RSLPStrippingRule("inal", 3, "", RSLPException.NOUN_INAL, "criminal", "crim"),
		new RSLPStrippingRule("ano", 4, "", null, "americano", "americ"),
		new RSLPStrippingRule("�vel", 2, "", RSLPException.NOUN_AVEL, "am�vel", "am"),
		new RSLPStrippingRule("�vel", 5, "", RSLPException.NOUN_IVEL, "combust�vel", "combust"),
		new RSLPStrippingRule("ura", 4, "", RSLPException.NOUN_URA, "cobertura", "cobert"),
		new RSLPStrippingRule("ual", 3, "", RSLPException.NOUN_UAL, "consensual", "consens"),
		new RSLPStrippingRule("ial", 3, "", null, "mundial", "mund"),
		new RSLPStrippingRule("al", 4, "", RSLPException.NOUN_AL, "experimental", "experiment")
	};

	/** Stores the rule to be used in the verb reduction step */
	public static final RSLPStrippingRule[] VERB_REDUCTION_RULES = {
		new RSLPStrippingRule("ar�amo", 2, "", null, "cantar�amo", "cant"),
		new RSLPStrippingRule("�ssemo", 2, "", null, "cant�ssemo", "cant"),
		new RSLPStrippingRule("er�amo", 2, "", null, "beber�amo", "beb"),
		new RSLPStrippingRule("�ssemo", 2, "", null, "beb�ssemo", "beb"),
		new RSLPStrippingRule("ir�amo", 3, "", null, "partir�amo", "part"),
		new RSLPStrippingRule("�ssemo", 3, "", null, "part�ssemo", "part"),
		new RSLPStrippingRule("�ramo", 2, "", null, "cant�ramo", "cant"),
		new RSLPStrippingRule("�rei", 2, "", null, "cant�rei", "cant"),
		new RSLPStrippingRule("aremo", 2, "", null, "cantaremo", "cant"),
		new RSLPStrippingRule("ariam", 2, "", null, "cantariam", "cant"),
		new RSLPStrippingRule("ar�ei", 2, "", null, "cantar�ei", "cant"),
		new RSLPStrippingRule("�ssei", 2, "", null, "cant�ssei", "cant"),
		new RSLPStrippingRule("assem", 2, "", null, "cantassem", "cant"),
		new RSLPStrippingRule("�vamo", 2, "", null, "cant�vamo", "cant"),
		new RSLPStrippingRule("�ramo", 3, "", null, "beb�ramo", "beb"),
		new RSLPStrippingRule("eremo", 3, "", null, "beberemo", "beb"),
		new RSLPStrippingRule("eriam", 3, "", null, "beberiam", "beb"),
		new RSLPStrippingRule("er�ei", 3, "", null, "beber�ei", "beb"),
		new RSLPStrippingRule("�ssei", 3, "", null, "beb�ssei", "beb"),
		new RSLPStrippingRule("essem", 3, "", null, "bebessem", "beb"),
		new RSLPStrippingRule("�ramo", 3, "", null, "partir�amo", "part"),
		new RSLPStrippingRule("iremo", 3, "", null, "partiremo", "part"),
		new RSLPStrippingRule("iriam", 3, "", null, "partiriam", "part"),
		new RSLPStrippingRule("ir�ei", 3, "", null, "partir�ei", "part"),
		new RSLPStrippingRule("�ssei", 3, "", null, "part�ssei", "part"),
		new RSLPStrippingRule("issem", 3, "", null, "partissem", "part"),
		new RSLPStrippingRule("ando", 2, "", null, "cantando", "cant"),
		new RSLPStrippingRule("endo", 3, "", null, "bebendo", "beb"),
		new RSLPStrippingRule("indo", 3, "", null, "partindo", "part"),
		new RSLPStrippingRule("ondo", 3, "", null, "propondo", "prop"),
		new RSLPStrippingRule("aram", 2, "", null, "cantaram", "cant"),
		new RSLPStrippingRule("arde", 2, "", null, "cantarde", "cant"),
		new RSLPStrippingRule("arei", 2, "", null, "cantarei", "cant"),
		new RSLPStrippingRule("arem", 2, "", null, "cantarem", "cant"),
		new RSLPStrippingRule("aria", 2, "", null, "cantaria", "cant"),
		new RSLPStrippingRule("armo", 2, "", null, "cantarmo", "cant"),
		new RSLPStrippingRule("asse", 2, "", null, "cantasse", "cant"),
		new RSLPStrippingRule("aste", 2, "", null, "cantaste", "cant"),
		new RSLPStrippingRule("avam", 2, "", RSLPException.VERB_AVAM, "cantavam", "cant"),
		new RSLPStrippingRule("�vei", 2, "", null, "cant�vei", "cant"),
		new RSLPStrippingRule("eram", 3, "", null, "beberam", "beb"),
		new RSLPStrippingRule("erde", 3, "", null, "beberde", "beb"),
		new RSLPStrippingRule("erei", 3, "", null, "beberei", "beb"),
		new RSLPStrippingRule("�rei", 3, "", null, "beb�rei", "beb"),
		new RSLPStrippingRule("erem", 3, "", null, "beberem", "beb"),
		new RSLPStrippingRule("eria", 3, "", null, "beberia", "beb"),
		new RSLPStrippingRule("ermo", 3, "", null, "bebermo", "beb"),
		new RSLPStrippingRule("esse", 3, "", null, "bebesse", "beb"),
		new RSLPStrippingRule("este", 3, "", RSLPException.VERB_ESTE, "bebeste", "beb"),
		new RSLPStrippingRule("�amo", 3, "", null, "beb�amo", "beb"),
		new RSLPStrippingRule("iram", 3, "", null, "partiram", "part"),
		new RSLPStrippingRule("�ram", 3, "", null, "conclu�ram", "conclu"),
		new RSLPStrippingRule("irde", 2, "", null, "partirde", "part"),
		new RSLPStrippingRule("irei", 3, "", RSLPException.VERB_IREI, "part�rei", "part"),
		new RSLPStrippingRule("irem", 3, "", RSLPException.VERB_IREM, "partirem", "part"),
		new RSLPStrippingRule("iria", 3, "", null, "partiria", "part"),
		new RSLPStrippingRule("irmo", 3, "", null, "partimo", "part"),
		new RSLPStrippingRule("isse", 3, "", null, "partisse", "part"),
		new RSLPStrippingRule("iste", 4, "", null, "partiste", "part"),
		new RSLPStrippingRule("amo", 2, "", null, "cantamo", "cant"),
		new RSLPStrippingRule("ara", 2, "", RSLPException.VERB_ARA, "cantara", "cant"),
		new RSLPStrippingRule("ar�", 2, "", RSLPException.VERB_ARAA, "cantar�", "cant"),
		new RSLPStrippingRule("are", 2, "", RSLPException.VERB_ARE, "cantare", "cant"),
		new RSLPStrippingRule("ava", 2, "", RSLPException.VERB_AVA, "cantava", "cant"),
		new RSLPStrippingRule("emo", 2, "", null, "cantemo", "cant"),
		new RSLPStrippingRule("era", 3, "", RSLPException.VERB_ERA, "bebera", "beb"),
		new RSLPStrippingRule("er�", 3, "", null, "beber�", "beb"),
		new RSLPStrippingRule("ere", 3, "", RSLPException.VERB_ERE, "bebere", "beb"),
		new RSLPStrippingRule("iam", 3, "", RSLPException.VERB_IAM, "bebiam", "beb"),
		new RSLPStrippingRule("�ei", 3, "", null, "beb�ei", "beb"),
		new RSLPStrippingRule("imo", 3, "", RSLPException.VERB_IMO, "partimo", "part"),
		new RSLPStrippingRule("ira", 3, "", RSLPException.VERB_IRA, "partira", "part"),
		new RSLPStrippingRule("ir�", 3, "", null, "partir�", "part"),
		new RSLPStrippingRule("ire", 3, "", RSLPException.VERB_IRE, "partire", "part"),
		new RSLPStrippingRule("omo", 3, "", null, "compomo", "comp"),
		new RSLPStrippingRule("ai", 2, "", null, "cantai", "cant"),
		new RSLPStrippingRule("am", 2, "", null, "cantam", "cant"),
		new RSLPStrippingRule("ear", 4, "", RSLPException.VERB_EAR, "barbear", "barb"),
		new RSLPStrippingRule("ar", 2, "", RSLPException.VERB_AR, "cantar", "cant"),
		new RSLPStrippingRule("uei", 3, "", null, "cheguei", "cheg"),
		new RSLPStrippingRule("ei", 3, "", null, "cantei", "cant"),
		new RSLPStrippingRule("em", 2, "", RSLPException.VERB_EM, "cantem", "cant"),
		new RSLPStrippingRule("er", 2, "", RSLPException.VERB_ER, "beber", "beb"),
		new RSLPStrippingRule("eu", 3, "", RSLPException.VERB_EU, "bebeu", "beb"),
		new RSLPStrippingRule("ia", 3, "", RSLPException.VERB_IA, "bebia", "beb"),
		new RSLPStrippingRule("ir", 3, "", RSLPException.VERB_IR, "partir", "part"),
		new RSLPStrippingRule("iu", 3, "", null, "partiu", "part"),
		new RSLPStrippingRule("ou", 3, "", null, "chegou", "cheg"),
		new RSLPStrippingRule("i", 3, "", null, "bebi", "beb")
	};
	
	/** Stores the rule to be used in the vowel remove step */
	public static final RSLPStrippingRule[] VOWEL_REDUCTION_RULES = {
		new RSLPStrippingRule("a", 3, "", null, "menina", "menin"),
		new RSLPStrippingRule("e", 3, "", null, "grande", "grand"),
		new RSLPStrippingRule("o", 3, "", null, "menino", "menin")
	};
	
}
