package ex;

import java.util.ArrayList;

public class Parser {
	
	private int pos;
    private int profondeur;
    private ArrayList<Token> tokens;
    
    
/**
 * Renvoie l'analyse du Node n�cessaire dans l'interpr�teur
 * @param tokens un param�tre obtenu par le Lexer
 * @return un objet de type Node contenant le r�sultat de l'analyse du Lexer
 * @throws Exception
 */
    public Node analyse(ArrayList<Token> tokens) throws Exception {
        this.tokens = tokens;
        pos = 0;
        
        Node expr = new Node(NodeClass.nBlock);
        
        while(!isEOF())
        {
        	Token token = getToken();
        	switch(token.getCl())
        	{
        	
        	case tProc :
        		expr.appendNode(tProc(token));
        		break;
        		
        	case tForward :
        		expr.appendNode(tForward(token));
        		break;
        		
        	case tLeft :
        		expr.appendNode(tLeft(token));
        		break;
        		
        	case tRight :
        		expr.appendNode(tRight(token));
        		break;
        		
        	case tColor :
        		expr.appendNode(tColor(token));
        		break;
        		
        	case tRepeat :
        		expr.appendNode(tRepeat(token));
        		break;
        		
        	case tCall :
        		expr.appendNode(tCall(token));
        		break;
        		
        	default :
        		throw new Exception("Erreur de lecture - Mauvais token enregistr� lors de l'analyse");
        	}
        	
        }
        
        //System.out.println("Fin atteinte = " + (pos == tokens.size()));
        return expr;
    }

    
    
    
    
    /*

    m�thodes des symboles non terminaux

    */
    
    
    public String ident(Token t)
    {
    	return t.getValue();
    }
    
    
    public String intVal(Token t)
    {
    	return t.getValue();
    }

    
    /**
     * Classe illustrant les tokens utilisables lors de l'�criture d'une proc�dure
     * @param token, une proc�dure
     * @return un objet Node contenant toute la proc�dure
     * @throws Exception 
     */
    public Node bracketOpened(Token token) throws Exception
    {
    	Node node = new Node(NodeClass.nBlock);
    	
    	while(!isEOF())
    	{
    		Token tbis = getToken();
    		
    		if(tbis.getCl() == TokenClass.tForward)
    		{
    			node.appendNode(tForward(tbis));
    		}
    		
    		else if(tbis.getCl() == TokenClass.tLeft)
    		{
    			node.appendNode(tLeft(tbis));
    		}
    		
    		else if(tbis.getCl() == TokenClass.tRight)
    		{
    			node.appendNode(tRight(tbis));
    		}
    		
    		else if(tbis.getCl() == TokenClass.tColor)
    		{
    			node.appendNode(tColor(tbis));
    		}
    		
    		else if(tbis.getCl() == TokenClass.tRepeat)
    		{
    			node.appendNode(tRepeat(tbis));
    		}
    		
    		else if(tbis.getCl() == TokenClass.tCall)
    		{
    			node.appendNode(tCall(tbis));
    		}
    		
    		else if(tbis.getCl() == TokenClass.closeBracket)
    		{
    			return node;
    		}
    		
    		else
    		{
    			throw new Exception("Erreur de lecture - Mauvais Token enregistr� lors de l'analyse");
    		}
    	}
    	
		throw new Exception("Erreur de lecture - Bloc d'instructions non clos");
    	
    }
    
    
    public Node tProc(Token token) throws Exception
    {
    	
    	if(getTokenClass() == TokenClass.ident)
    	{
    		Node proc = new Node(NodeClass.nProc, ident(getToken()));
    		if(getTokenClass() == TokenClass.openBracket)
    		{
    			proc.appendNode(bracketOpened(getToken()));
    		}
    		
    		return proc;
    	}
    	else
    	{
    		throw new Exception("Token ident attendu pour instruction Proc�dure attribu�e");
    	}
    }
    
    
    public Node tForward(Token token) throws Exception
    {
    	if(getTokenClass() == TokenClass.intVal)
    	{
    		return new Node(NodeClass.nForward, intVal(getToken()));
    	}
    	else
    	{
    		throw new Exception("Token intVal attendu pour instruction Forward attribu�e");
    	}
    }
    
    
    public Node tLeft(Token token) throws Exception
    {
    	if(getTokenClass() == TokenClass.intVal)
    	{
    		return new Node(NodeClass.nLeft, intVal(getToken()));
    	}
    	else
    	{
    		throw new Exception("Token intVal attendu pour instruction Left attribu�e");
    	}
    }
    
    
    public Node tRight(Token token) throws Exception
    {
    	if(getTokenClass() == TokenClass.intVal)
    	{
    		return new Node(NodeClass.nRight, intVal(getToken()));
    	}
    	
    	else
    	{
    		throw new Exception("Token intVal attendu pour instruction Right attribu�e");
    	}
    }
    
    
    public Node tColor(Token token) throws Exception
    {
    	if(getTokenClass() == TokenClass.intVal)
    	{
    		return new Node(NodeClass.nColor, intVal(getToken()));
    	}
    	else
    	{
    		throw new Exception("Token intVal attendu pour instruction Color attribu�e");
    	}
    }
    
    
    public Node tRepeat(Token token) throws Exception
    {
    	if(getTokenClass() == TokenClass.intVal)
    	{
    		Node node = new Node(NodeClass.nRepeat, intVal(getToken()));
    		
    		if(getTokenClass() == TokenClass.openBracket)
    		{
    			node.appendNode(bracketOpened(getToken()));
    		}
    		return node;
    	}
    	else
    	{
    		throw new Exception("Token intVal ou [ attendu pour instruction Repeat attribu�e");
    	}
    }
    
    
    public Node tCall(Token token) throws Exception
    {
    	if(getTokenClass() == TokenClass.ident)
    	{
    		return new Node(NodeClass.nCall, ident(getToken()));
    	}
    	else
    	{
    		throw new Exception("Token ident attendu pour instruction Call attribu�e");
    	}
    }
    
    
    
    
    
    

    /*

    autres m�thodes

     */

    private boolean isEOF() {
        return pos >= tokens.size();
    }

    /**
     * Retourne la classe du prochain token � lire
     * SANS AVANCER au token suivant
     */
    private TokenClass getTokenClass() {
        if (pos >= tokens.size()) {
            return null;
        } else {
            return tokens.get(pos).getCl();
        }
    }

    /**
     * Retourne le prochain token � lire
     * ET AVANCE au token suivant
     */
    private Token getToken() {
        if (pos >= tokens.size()) {
            return null;
        } else {
            Token current = tokens.get(pos);
            pos++;
            return current;
        }
    }

    private void printNode(String s) {
        for(int i=0;i<profondeur;i++) {
            System.out.print("    ");
        }
        System.out.println(s);
    }

}
