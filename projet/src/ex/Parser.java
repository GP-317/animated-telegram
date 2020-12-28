package ex;

import java.util.ArrayList;

public class Parser {
	
	private int pos;
    private int profondeur;
    private ArrayList<Token> tokens;
    
    
/**
 * Renvoie l'analyse du Node nécessaire dans l'interpréteur
 * @param tokens un paramètre obtenu par le Lexer
 * @return un objet de type Node contenant le résultat de l'analyse du Lexer
 * @throws Exception
 */
    public Node analyse(ArrayList<Token> tokens) throws Exception {
        this.tokens = tokens;
        pos = 0;
        
        Node expr = new Node(NodeClass.nBlock);
        
        System.out.println("Fin atteinte = " + (pos == tokens.size()));
        return expr;
    }

    /*

    méthodes des symboles non terminaux

      */

    private Node S() throws Exception {

        if (getTokenClass() == TokenClass.intVal ||
                getTokenClass() == TokenClass.leftPar) {

            // production S -> AS'

            profondeur++;
            Node n1 = A();
            profondeur--;
            Node n2 = S_prime();
            if (n2 != null) {
                n2.prepend(n1);
                return n2;
            } else {
                return n1;
            }
        }
        throw new Exception("intVal ou ( attendu");
    }

    private Node S_prime() throws Exception {

        if (getTokenClass() == TokenClass.add) {

            // production S' -> +S

            Token t = getToken();
            printNode("+");
            profondeur++;
            Node n = new Node(t);
            n.append(S());
            profondeur--;
            return n;
        }

        if (getTokenClass() == TokenClass.rightPar || isEOF()) {

            // production S' -> epsilon

            return null;
        }

        throw new Exception("+ ou ) attendu");
    }

    private Node A() throws Exception {

        if (getTokenClass() == TokenClass.leftPar) {

            // production A -> ( S ) A'

            getToken();
            profondeur++;
            Node n1 = S();
            profondeur--;
            if (getTokenClass() == TokenClass.rightPar) {
                getToken();
                Node n2 = A_prime();
                if (n2 != null) {
                    n2.prepend(n1);
                    return n2;
                } else {
                    return n1;
                }
            }
            throw new Exception(") attendu");
        }

        if (getTokenClass() == TokenClass.intVal) {

            // production A -> intVal A'

            Token tokIntVal = getToken();
            printNode(tokIntVal.getValue()); // affiche la valeur int
            Node n1 = new Node(tokIntVal);
            Node n2 = A_prime();
            if (n2 != null) {
                n2.prepend(n1);
                return n2;
            } else {
                return n1;
            }
        }

        throw new Exception("intVal ou ( attendu");
    }

    private Node A_prime() throws Exception {
        if (getTokenClass() == TokenClass.multiply) {

            // production A' -> * A

            Token t = getToken();
            printNode("*");
            Node n = new Node(t);
            n.append(A());
            return n;
        }

        if (getTokenClass() == TokenClass.add ||
                getTokenClass() == TokenClass.rightPar ||
                isEOF()) {

            // production A' -> epsilon

            return null;
        }
        throw new Exception("* + ou ) attendu");

    }

    /*

    autres méthodes

     */

    private boolean isEOF() {
        return pos >= tokens.size();
    }

    /**
     * Retourne la classe du prochain token à lire
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
     * Retourne le prochain token à lire
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
