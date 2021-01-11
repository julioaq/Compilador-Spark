package LenguajesyAutomatas;
import java.util.List;
import java.util.ArrayList;
class nodo_token // objeto de Tokens 
{
    String lexema="";
    int token;
    int renglon;
    public nodo_token(String lexema, int token, int renglon)
    {
        this.lexema = lexema;
        this.token = token;
        this.renglon = renglon;
    }
}
public class lexico {
    String descripcion_error="";  // Descripción del error lexico, si lo hay.
    List <nodo_token> lista_tokens = new ArrayList<>();      // Lista donde se almacenan los tokens generados en el analisis lexico
    static String [][] palabras_reservadas = new String[][]  // Array con palabras reservadas junto con su token respectivo
    {        
        {"200", "procedure"},
        {"201", "is"},
        {"202", "begin"},
        {"203", "end"},
        {"204", "and"},
        {"205", "or"},
        {"206", "xor"},
        {"207", "not"},
        {"208", "integer"},
        {"209", "float"},
        {"210", "string"},
        {"211", "if"},
        {"212", "for"},
        {"213", "while"},
        {"214", "loop"},
        {"215", "print"},
        {"216", "true"},
        {"217", "false"},
        {"218", "then"},
        {"219", "elsif"},
        {"220", "else"},
        {"221", "exit"},
        {"222", "when"}
    };
    
    static String [][] errores_lexicos = new String[][] // Array conteniendo los errores lexicos junto con us código de error
    {
        {"500", "Error 500: se esperaba un digito en el renglon = "},
        {"501", "Error 501: end of line inesperado en el renglon = "},
        {"502", "Error 502: end of file inesperado en el renglon = "},
        {"503", "Error 503: simbolo no reconocido en el renglon = "}
    };
    static int [][] matriz_transicion_lexico = new int[][]
    {
    //          0       1       2       3       4       5       6       7       8       9       10      11      12      13      14      15      16      17      18      19      20      21
    // Col      letra   digito    .     +       -       *       /       >       <       =       ,       ;       :       (       )       "       oc      eb      \t      \r      \n      eof
                {1,	2,	103,	104,	5,	106,	7,	8,	9,	113,	114,	115,	10,	118,	119,	11,	503,	0,	0,	0,	0,	0},
                {1,	1,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100,	100},
                {101,	2,	3,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101},
                {500,	4,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500,	500},
                {102,	4,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102},
                {105,	105,	105,	105,	6,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105,	105},
                {6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	0,	6},
                {108,	108,	108,	108,	108,	108,	108,	108,	108,	107,	108,	108,	108,	108,	108,	108,	108,	108,	108,	108,	108,	108},
                {110,	110,	110,	110,	110,	110,	110,	110,	110,	109,	110,	110,	110,	110,	110,	110,	110,	110,	110,	110,	110,	110},
                {112,	112,	112,	112,	112,	112,	112,	112,	112,	111,	112,	112,	112,	112,	112,	112,	112,	112,	112,	112,	112,	112},
                {117,	117,	117,	117,	117,	117,	117,	117,	117,	116,	117,	117,	117,	117,	117,	117,	117,	117,	117,	117,	117,	117},
                {11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	11,	120,	11,	11,	11,	11,	501,	502}
    };
    
    public void comprobar_lexico(String archivo)
    {
        int estado_actual = 0;
        int column_actual;
        int valor_matriz;
        int renglon=1;
        char caracter_actual;
        String lexema="";

        for(int i = 0; i < archivo.length(); i++) // Bucle que recorre todo el archivo captado por el parametro.
        {
            caracter_actual = archivo.charAt(i); // Inicializa la variable en el caracter actual del bucle.
            
            if(Character.isLetter(caracter_actual))
            {
                column_actual = 0;
            }
            else if(Character.isDigit(caracter_actual))
            {
                column_actual = 1;
            }
            else
            {
                switch(caracter_actual)
                {
                    case '.':
                        column_actual = 2;
                        break;
                    case '+':
                        column_actual = 3;
                        break;
                    case '-':
                        column_actual = 4;
                        break;
                    case '*':
                        column_actual = 5;
                        break;
                    case '/':
                        column_actual = 6;
                        break;
                    case '>':
                        column_actual = 7;
                        break;
                    case '<':
                        column_actual = 8;
                        break;
                    case '=':
                        column_actual = 9;
                        break;
                    case ',':
                        column_actual = 10;
                        break;
                    case ';':
                        column_actual = 11;
                        break;
                    case ':':
                        column_actual = 12;
                        break;
                    case '(':
                        column_actual = 13;
                        break;
                    case ')':
                        column_actual = 14;
                        break;
                    case '"':
                        column_actual = 15;
                        break;
                    case ' ':
                        column_actual = 17;
                        break;
                    case '\t':
                        column_actual = 18;
                        break;
                    case '\r':
                        column_actual = 19;
                        break;
                    case '\n':
                        renglon++;
                        column_actual = 20;
                        break;
                    case '\0':
                        column_actual = 21;
                        break;
                    default: // otra cosa
                        column_actual = 16;
                        break;
                }
            }           
            
            valor_matriz = matriz_transicion_lexico[estado_actual][column_actual]; // Asigna un valor de la variable en los indices [n][m].
            if(valor_matriz >= 0 && valor_matriz <= 99) // Atrapa los valores de la matriz del 0 al 99 (Estados de transicion).
            {
                if(valor_matriz > 0) lexema += caracter_actual;
                if(estado_actual == 6) lexema = "";
                
                estado_actual = valor_matriz;  
                
            }
            
            if(valor_matriz >= 100 && valor_matriz <= 499)
            {
                switch(valor_matriz)
                {
                    case 100: // id
                        buscar_palabra_reservada(lexema, valor_matriz, renglon);
                        i--;
                        lexema="";
                        estado_actual=0;
                        break;
                    case 101:
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        i--;
                        lexema = "";
                        estado_actual = 0;
                        break;  
                    case 102:
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        i--;
                        lexema="";
                        estado_actual=0;   
                        break;
                    case 103: // punto decimal
                        lexema = ".";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                        
                    case 104: // operador suma 
                        lexema = "+";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                        
                    case 105: // operador resta
                        lexema = "-";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        i--;
                        estado_actual=0;
                        lexema="";
                        break;
                        
                    case 106: // operador multiplicacion
                        lexema = "*";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 107: // /= Diferente de
                        
                        lexema = lexema + "=";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 108: // division
                        lexema = "/";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        i--;
                        estado_actual=0;
                        lexema="";
                        break;
                    case 109:
                        lexema = lexema + "=";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 110:
                        lexema = ">";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        i--;
                        estado_actual=0;
                        lexema="";
                        break;
                    case 111:
                        lexema = lexema + "=";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 112:
                        lexema = "<";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        i--;
                        estado_actual=0;
                        lexema="";
                        break;
                    case 113:
                        lexema = "=";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 114:
                        lexema = ",";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 115:
                        lexema = ";";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 116:
                        lexema = lexema + "=";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 117:
                        lexema = ":";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        i--;
                        estado_actual=0;
                        lexema="";
                        break;
                    case 118:
                        lexema = "(";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 119:
                        lexema = ")";
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                    case 120:
                        lexema = lexema + '"';
                        lista_tokens.add(new nodo_token(lexema, valor_matriz, renglon));
                        estado_actual=0;
                        lexema="";
                        break;
                        
                }         
            }
            if(valor_matriz >= 500) // errores lexicos
            {
                buscar_error_lexico(valor_matriz, renglon);
                break;
            }
        }
        
    }
    
    public void buscar_palabra_reservada(String lexema, int valor_mt, int renglon)
    {
        for (String[] i : palabras_reservadas) {
            if(lexema.equals(i[1]))
            {
                valor_mt = Integer.parseInt(i[0]);
                break;
            }
        }
        lista_tokens.add(new nodo_token(lexema, valor_mt, renglon));
        
    }
    
    public void buscar_error_lexico(int valor_matriz, int renglon)
    {
        for(String[] i : errores_lexicos)
        {
            if(String.valueOf(valor_matriz).equals(i[0]))
            {
                if(String.valueOf(valor_matriz).equals("503")) renglon --;
                descripcion_error = i[1];
                descripcion_error = descripcion_error + String.valueOf(renglon) + ".";
                break;
            }
        }      
    }
    
    public List obtener_lista_tokens() 
    { 
        return this.lista_tokens; 
    }
    
    public String recuperar_error_lexico()
    {
        if(descripcion_error.length() > 0) return descripcion_error;
        return "";
    }
}
