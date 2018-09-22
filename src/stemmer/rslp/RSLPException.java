package stemmer.rslp;

public class RSLPException {

	public static final String[]
			
	PLURAL_AES = {"m�es"},
	PLURAL_AIS = {"cais", "mais"},
	PLURAL_EIS = {"leis"},
	PLURAL_RES = {"pires"},
	PLURAL_IS = {"b�quinis", "cr�cis", "depois", "dois", "leis", "l�pis", "pois"},
	PLURAL_S = {"ali�s", "ambas", "ambos", "ap�s", "atrav�s", "atr�s", "cais", "conv�s", "cr�cis", "fezes", "f�rias", "g�s", "l�pis", "mais", "mas", "menos", "messias", "mois�s", "pa�s", "p�sames"},

	FEMININE_ONA = {"abandona", "acetona", "carona", "cortisona", "detona", "iona", "lona", "maratona", "mon�tona"},
	FEMININE_NA = {"abandona", "acetona", "banana", "campana", "caravana", "carona", "cortisona", "detona", "grana", "guiana", "iona", "lona", "maratona", "mon�tona", "paisana"},
	FEMININE_INHA = {"linha", "minha", "rainha"},
	FEMININE_ESA = {"ilesa", "mesa", "obesa", "pesa", "presa", "princesa", "turquesa"},
	FEMININE_OSA = {"mucosa", "prosa"},
	FEMININE_ICA = {"dica"},
	FEMININE_ADA = {"pitada"},
	FEMININE_IDA = {"vida"},
	FEMININE_IIDA = {"reca�da", "sa�da"},
	FEMININE_IMA = {"v�tima"},
	FEMININE_IVA = {"oliva", "saliva"},
	FEMININE_EIRA = {"bandeira", "barreira", "beira", "besteira", "cadeira", "capoeira", "feira", "frigideira", "fronteira", "poeira"},
	FEMININE_AA = {"amanh�", "arapu�", "div�", "f�"},

	ADVERB_MENTE = {"experimente"},

	DEGREE_INHO = {"caminho", "cominho"},
	DEGREE_AZIO = {"top�zio"},
	DEGREE_ACO = {"antebra�o"},
	DEGREE_ZAO = {"coaliz�o"},
	DEGREE_AO = {"aptid�o", "bar�o", "bilh�o", "camar�o", "campe�o", "can��o", "capit�o", "chimarr�o", "colch�o", "cora��o", "cord�o", "crist�o", "embri�o", "espi�o", "esta��o", "falc�o", "fei��o", "fic��o", "fog�o", "foli�o", "furac�o", "fus�o", "gam�o", "glut�o", "grot�o", "ilus�o", "lampi�o", "leil�o", "le�o", "lim�o", "macac�o", "mam�o", "mel�o", "milh�o", "na��o", "org�o", "patr�o", "port�o", "quinh�o", "rinc�o", "sen�o", "tra��o", "�rf�o"},

	NOUN_ASTICO = {"eclesi�stico"},
	NOUN_AMENTO = {"departamento", "firmamento", "fundamento"},
	NOUN_IDADE = {"autoridade", "comunidade"},
	NOUN_IZADO = {"organizado", "pulverizado"},
	NOUN_ATIVO = {"pejorativo", "relativo"},
	NOUN_ANCIA = {"ambul�ncia"},
	NOUN_IZAC = {"organiza�"},
	NOUN_ARIO = {"anivers�rio", "arm�rio", "di�rio", "lion�rio", "sal�rio", "volunt�rio"},
	NOUN_ANTE = {"adiante", "elefante", "gigante", "instante", "possante", "restaurante"},
	NOUN_ORIA = {"categoria"},
	NOUN_INAL = {"afinal"},
	NOUN_AVEL = {"af�vel", "pot�vel", "razo�vel", "vulner�vel"},
	NOUN_IVEL = {"poss�vel"},
	NOUN_ENTE = {"acrescente", "alimente", "aparente", "freq�ente", "oriente", "permanente"},
	NOUN_TIVO = {"relativo"},
	NOUN_AGEM = {"carruagem", "chantagem", "coragem", "vantagem"},
	NOUN_IDOR = {"ouvidor"},
	NOUN_EIRO = {"desfiladeiro", "mosteiro", "pioneiro"},
	NOUN_ISMO = {"cinismo"},
	NOUN_ICO = {"explico", "p�blico", "tico"},
	NOUN_ICE = {"c�mplice"},
	NOUN_URA = {"acupuntura", "costura", "imatura"},
	NOUN_UAL = {"bissexual", "pontual", "virtual", "visual"},
	NOUN_IVO = {"passivo", "pejorativo", "possessivo"},
	NOUN_ADO = {"grado"},
	NOUN_IDO = {"consolido", "c�ndido", "decido", "duvido", "marido", "r�pido", "t�mido"},
	NOUN_OSO = {"precioso"},
	NOUN_AC = {"equa�", "rela�"},
	NOUN_IC = {"elei�"},
	NOUN_AL = {"animal", "bissexual", "desleal", "estatal", "fiscal", "formal", "liberal", "pessoal", "pontual", "postal", "sideral", "sucursal", "virtual", "visual"},
	NOUN_OR = {"assessor", "autor", "benfeitor", "favor", "melhor", "motor", "pastor", "redor", "rigor", "sensor", "tambor", "terior", "tumor"},

	VERB_AVAM = {"agravam"},
	VERB_ESTE = {"agreste", "faroeste"},
	VERB_IREI = {"admirei"},
	VERB_IREM = {"adquirem"},
	VERB_ARA = {"arara", "prepara"},
	VERB_ARAA = {"alvar�"},
	VERB_ARE = {"prepare"},
	VERB_AVA = {"agrava"},
	VERB_ERA = {"acelera", "espera"},
	VERB_ERE = {"espere"},
	VERB_IAM = {"ampliam", "elogiam", "enfiam", "ensaiam"},
	VERB_IMO = {"intimo", "nimo", "queimo", "reprimo", "ximo", "�ntimo"},
	VERB_IRA = {"fronteira", "s�tira"},
	VERB_IRE = {"adquire"},
	VERB_EAR = {"alardear", "nuclear"},
	VERB_AR = {"azar", "bazaar", "patamar"},
	VERB_EM = {"alem", "virgem"},
	VERB_ER = {"pier", "�ter"},
	VERB_EU = {"chapeu"},
	VERB_IA = {"acia", "aprecia", "arredia", "cheia", "elogia", "est�ria", "fatia", "l�bia", "mania", "pol�cia", "praia", "�sia"},
	VERB_IR = {"freir"},

	VOWEL = {"beb�", "�sia", "�o"};
	
	public static boolean isException(String word, String[] exceptions) {
		
		int start = 0;
		int end = exceptions.length - 1;
		int pointer = 0;
		int status = 0;
		
		while(start <= end) {
			pointer = (end + start) / 2;
			status = word.compareTo(exceptions[pointer]);
			if(status == 0) return true;
			else if(status > 0) start = pointer + 1;
			else end = pointer - 1;
		}
		
		return false;
		
	}
	
}
