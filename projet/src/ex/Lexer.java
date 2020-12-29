package ex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Lexer {
	
	// Gère les différents modules dans une chaîne 
	// pour que le programme les lise comme des espaces
	static List<Character> WHITESPACE = Arrays.asList(' ', '\t', '\n', ',');
	
	static int ETAT_INITIAL = 0;
	
	String input;
	
	
	
	/**
	 * tableau des transitions d'état
	 * les états au-delà de 100 ont été numérotés de manière arbitraire
	 */
    static Integer transitions[][] = {
//             			espace    lettre    intVal     [       ]     autre
/*  0 */			{      0,       1,        2,       3,      4,     null      },
/*  1 */		    {    101,       1,        1,     101,    101,     null      },
/*  2 */		    {    102,     102,        2,     102,    102,     null      },
/*  3 */		    {    103,     103,      103,     103,    103,     null      },
/*  4 */		    {    104,     104,      104,     104,    104,     null      },

			// 101 accepte identifiant ou mot clé  		(goBack : oui)
			// 102 accepte entier                        	"	"
			// 103 accepte [                        		"	"
			// 104 accepte ]                        		"	"
	};
    
    
	private int indiceSymbole(Character c) {
		if (c == null) return 0;
		if (WHITESPACE.contains(c)) return 0;
		if (Character.isLetter(c)) return 1;
		if (Character.isDigit(c)) return 2;
		if (c == '[') return 3;
		if (c == ']') return 4;
		return 5;
	}
    
    
	public ArrayList<Token> lexer(SourceReader sr) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		input="";
		int etat = ETAT_INITIAL;
		
		while (true) {
			Character c = sr.lectureSymbole();
			Integer e = transitions[etat][indiceSymbole(c)];
			
			System.out.println("Symbole " + c + " transition " + etat + " -> " + e);
			
			if (e == null) {
				
				System.out.println(" pas de transition depuis état " + etat + " avec symbole " + c);
				return new ArrayList<Token>(); // renvoie une liste vide
				
			}
			if (e >= 100) 
			{
				
				if (e == 101) 
				{
					
					// System.out.println("Accepte identifiant ou mot-clé "+ buf);
					// Méthode d'analyse de fonction, retour à la lecture si aucune concordance
					tokens.add(analyseFonction(input));
					sr.goBack();
					
				} 
				
				else if (e == 102) 
				{
					
					// System.out.println("Accepte entier " + buf);
					tokens.add(new Token(TokenClass.intVal, input));
					sr.goBack();
					
				} 
				
				else if (e == 103) 
				{
					
					// System.out.println("Accepte [ " + buf);
					tokens.add(new Token(TokenClass.openBracket, input));
					//sr.goBack();
					
				} 
				
				else if (e == 104) 
				{
					
					// System.out.println("Accepte [ " + buf);
					tokens.add(new Token(TokenClass.closeBracket, input));
					//sr.goBack();
					
				}
				
				etat = 0;
				input = "";
				
			} 
			
			else 
			{
				etat = e;
				if (etat>0) input = input + c;
			}
			
			if (c==null) break;
		}
		return tokens;
	}
    
    /**
     * Analyse la chaîne fournie et renvoie le token associé à la fonction désirée
     * @param une chaîne buf
     * @return un token
     */
	private Token analyseFonction(String input)
	{
		switch(input)
		{
			case "procedure" :
				return new Token(TokenClass.tProc);
				
			case "forward" :
				return new Token(TokenClass.tForward);
			
			case "left" :
				return new Token(TokenClass.tLeft);
			
			case "right" :
				return new Token(TokenClass.tRight);
			
			case "repeat" :
				return new Token(TokenClass.tRepeat);
			
			case "color" :
				return new Token(TokenClass.tColor);
			
			case "call" :
				return new Token(TokenClass.tCall);
		}
		return new Token(TokenClass.ident, input);
	}
	
	

	

}
