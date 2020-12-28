package ex;

/**
 * Classe servant à dénombrer les tokens par la lecture de l'input dans la classe Lexer
 * Reprise du TP4
 * @author geo02
 *
 */
public class Token {
	
	private TokenClass cl;
    private String value;

    public Token(TokenClass cl) {
        this.cl=cl;
        this.value=null;
    }

    public Token(TokenClass cl, String value) {
        this.cl=cl;
        this.value=value;
    }

    public TokenClass getCl() {
        return this.cl;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        String res = cl.toString();
        if (value != null) res = res + "(" + value + ")";
        return res;
    }
    

}
