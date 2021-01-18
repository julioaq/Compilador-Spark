package LenguajesyAutomatas;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.ListIterator;
class nodo_variable
{
    String nombre_var;
    int tipo_var;
    nodo_variable(String nombre_variable, int tipo_variable)
    {
        this.nombre_var = nombre_variable;
        this.tipo_var = tipo_variable;  
    }
}
public class sintactico {
    
    List <nodo_token> lista_tokens = new ArrayList<>();  // lista que contiene los tokens generados en el lexico.
    List <nodo_variable> lista_variables = new ArrayList<>(); // lista de variables.
    List < String > nombre_vars_temp = new ArrayList<>(); // lista de nombres de variables temporal.
    
    int puntero; // puntero que señala al siguiente nodo en la lista.
    String identificador_programa=""; // id del programa.
    
    List < String > lista_errores_sintacticos = new ArrayList<>();
    List < String > lista_errores_semanticos = new ArrayList<>();

    CopyOnWriteArrayList<Integer>lista_polish = new CopyOnWriteArrayList<>();
    
    List < String > lista_polish_buffer = new ArrayList<>();
    
    Stack <Integer>pila_auxiliar_operadores = new Stack();
    Stack <Integer>pila_auxiliar_operandos = new Stack<>(); 
    
    boolean sintaxis_completada = false;
    boolean coma_usada=false;
    /*=== Etiquetas del polish ====*/
    int A=0;
    int B=0;
    int E=0;
    int ifs_anidados=0;
    
    /*=== Etiquetas del polish ====*/
    static String [][] errores_sintacticos = new String[][] 
    {
        {"502", "Error 502: end of file inesperado en el renglon = "},
        {"503", "Error 503: se esperaba la palabra procedure en el renglon = "},
        {"504", "Error 504: se esperaba un identificador en el renglon = "},
        {"505", "Error 505: se esperaba la palabra is en el renglon = "},
        {"506", "Error 506: se esperaba la palabra begin en el renglon = "},
        {"507", "Error 507: se esperaba la palabra end en el renglon = "},
        {"508", "Error 508: se esperaba el identificador del programa en el renglon = "},
        {"509", "Error 509: se esperaba el simbolo ; en el renglon = "},
        {"510", "Error 510: se esperaba el simbolo : en el renglon = "},
        {"511", "Error 511: se esperaba un tipo de variable en el renglon = "},
        {"512", "Error 512: se encontro una expresion ilegal en el renglon = "},
        {"513", "Error 513: se esperaba fin de parentesis en el renglon = "},
        {"514", "Error 514: se esperaba la palabra if en el renglon ="},
    };
    
    static String[][] errores_semanticos = new String[][] 
    {
        {"520", "Error 520: identificador repetido en el renglon = "},
        {"521", "Error 521: variable no declarada en el renglon = "},
        {"522", "Error 522: incompatibilidad de tipos en el renglon = "}
    };
    
    sintactico(List <nodo_token> lista_tokens) 
    {
        this.puntero = 0;
        this.lista_tokens = lista_tokens;
    }
    
    public void comprobar_sintaxis(String archivo) throws IOException
    { 
        if(lista_tokens.get(puntero).token == 200) // palabra 'procedure'
        {
            try
            {
                puntero++;
                if(lista_tokens.get(puntero).token == 100) // Identificador del programa
                {
                    identificador_programa = lista_tokens.get(puntero).lexema;
                    puntero++;
                    if(lista_tokens.get(puntero).token == 201) // palabra 'is'
                    {
                        do
                        {
                            if(lista_tokens.get(puntero).token != 202 && puntero == lista_tokens.size()-1)
                            {
                                agregar_error_sintactico(506, lista_tokens.get(puntero).renglon);
                                break;
                            }
                            else if(lista_tokens.get(puntero).token == 202) 
                            {
                                block();
                                break;
                            }
                            else 
                            {
                                puntero++;
                                if(lista_tokens.get(puntero).token != 203 && !(lista_tokens.get(puntero).lexema.equals(identificador_programa)))
                                    declaracion_variables();
                            }
                        }
                        while (true);
                        if(lista_tokens.get(puntero).token ==  203) // end
                        {
                            puntero++;
                            if(lista_tokens.get(puntero).lexema == null ? identificador_programa == null : lista_tokens.get(puntero).lexema.equals(identificador_programa))
                            { 
                                lista_polish_buffer.stream().forEach(item -> System.out.println(item));
                                if(lista_errores_sintacticos.isEmpty() && lista_errores_semanticos.isEmpty()) 
                                {
                                    this.sintaxis_completada = true;
                                    vaciar_pilas();
                                    /*
                                    for(int i = 0; i < lista_variables.size(); i++)
                                    {
                                        pila_auxiliar_operandos.add(lista_variables.get(i).tipo_var);
                                    }
                                    
                                    for(int i = 0; i < lista_polish_buffer.size(); i++)
                                    {
                                        if(lista_polish_buffer.get(i).equals("+"))
                                        {
                                            pila_auxiliar_operadores.add(104);
                                        }
                                        if(lista_polish_buffer.get(i).equals("-"))
                                        {
                                            pila_auxiliar_operadores.add(105);
                                        }
                                        if(lista_polish_buffer.get(i).equals(":="))
                                        {
                                            pila_auxiliar_operadores.add(116);
                                        }
                                    }
                                    System.out.println("");
                                    for(int i = pila_auxiliar_operandos.size()-1; i >= 0 ; i--)
                                    {
                                        System.out.println(pila_auxiliar_operandos.get(i));
                                    }
                                    
                                    for(int i = pila_auxiliar_operadores.size()-1; i >= 0 ; i--)
                                    {
                                        System.out.println(pila_auxiliar_operadores.get(i));
                                    }
                                    
                                    
                                   /* for(int i = pila_auxiliar_operandos.size()-1; 0 <= i; i++)
                                    {
                                        System.out.println(pila_auxiliar_operandos.get(i));
                                    }
                                    for(int i = pila_auxiliar_operadores.size()-1; 0 <= i; i++)
                                    {
                                        System.out.println(pila_auxiliar_operadores.get(i));
                                    }*/
                                    ensamblador();
                                }
                            }
                            else
                            {
                                agregar_error_sintactico(508, lista_tokens.get(puntero).renglon);
                            }
                        }
                        else
                        {
                            agregar_error_sintactico(507, lista_tokens.get(puntero).renglon);
                        }
                    }
                    else
                    {
                        agregar_error_sintactico(505, lista_tokens.get(puntero).renglon);
                    }
                }
            }
            catch(IndexOutOfBoundsException | NullPointerException ex)
            {
                puntero--;
                agregar_error_sintactico(502, lista_tokens.get(puntero).renglon); 
            }           
        }
        else
        {
            agregar_error_sintactico(503, lista_tokens.get(puntero).renglon);
        }
    }
    
    public void agregar_error_sintactico(int codigo_error, int renglon)
    {
        for(String[] i : errores_sintacticos)
        {
            if(String.valueOf(codigo_error).equals(i[0]))
            {
                lista_errores_sintacticos.add(i[1] + renglon);
                break;
            }
        }
    }
    
    public void agregar_error_semantico(int codigo_error, int renglon)
    {
        for(String[] i : errores_semanticos)
        {
            if(String.valueOf(codigo_error).equals(i[0]))
            {
                lista_errores_semanticos.add(i[1] + renglon);
                break;
            }
        }
    }
    
    public void block()
    {
        do
        {
            lista_sentencias();
            if(lista_tokens.get(puntero).token == 203) // end
            {
                break;
            }
            else puntero++;
        }
        while (true);
    }
    
   
    public void declaracion_variables()
    {
        if(lista_tokens.get(puntero).token == 100) // identificador
        {      
            nombre_vars_temp.add(lista_tokens.get(puntero).lexema); 
            puntero++;
            coma_usada=false;

            switch (lista_tokens.get(puntero).token) 
            {
                case 117:
                    puntero++;
                    tipos_variables();
                    break;
                case 114:
                    coma_usada=true;
                    puntero++;
                    declaracion_variables();
                    break;
                default:
                    agregar_error_sintactico(510, lista_tokens.get(puntero).renglon);
                    break;
            } 
        }
        else 
        {
            if(coma_usada)
            {
                coma_usada=false;
                agregar_error_sintactico(504, lista_tokens.get(puntero).renglon);
            }
        }
    }
    /*
        Aquí se almacenan las variables
        Existen tres tipos de variables: integer, float y string
    */
    public void tipos_variables()
    {
        if(lista_tokens.get(puntero).token == 208) // integer
        {
            puntero++;
            if(lista_tokens.get(puntero).token == 115) // ;
            {
                nombre_vars_temp.forEach((String nombre_var) -> {
                    if(identificador_programa.equals(nombre_var) || lista_variables.stream().anyMatch(var -> var.nombre_var.equals(nombre_var)))
                        
                        agregar_error_semantico(520, lista_tokens.get(puntero).renglon);
                    
                    else
                        lista_variables.add(new nodo_variable(nombre_var, 101));
                });
                nombre_vars_temp.clear();
                puntero++;
                declaracion_variables();    
            }
            else
            {
                agregar_error_sintactico(509, lista_tokens.get(puntero).renglon);
            }
        }
        if(lista_tokens.get(puntero).token == 209) // float 
        {
            puntero++;
            if(lista_tokens.get(puntero).token == 115) // ;
            {
                nombre_vars_temp.forEach((String nombre_var) -> {
                    if(identificador_programa.equals(nombre_var) || lista_variables.stream().anyMatch(var -> var.nombre_var.equals(nombre_var)))
                        
                        agregar_error_semantico(520, lista_tokens.get(puntero).renglon);
                    
                    else
                        lista_variables.add(new nodo_variable(nombre_var, 102));
                });
                nombre_vars_temp.clear();
                puntero++;
                declaracion_variables();    
            }
            else
            {
                agregar_error_sintactico(509, lista_tokens.get(puntero).renglon);
            }
        }
        if(lista_tokens.get(puntero).token == 210) // string
        {
            puntero++;
            if(lista_tokens.get(puntero).token == 115) // ;
            {
                nombre_vars_temp.forEach((String nombre_var) -> {
                    if(identificador_programa.equals(nombre_var) || lista_variables.stream().anyMatch(var -> var.nombre_var.equals(nombre_var)))
                        
                        agregar_error_semantico(520, lista_tokens.get(puntero).renglon);
                    
                    else
                        lista_variables.add(new nodo_variable(nombre_var, 120));
                });
                nombre_vars_temp.clear();
                puntero++;
                declaracion_variables();    
            }
            else
            {
                agregar_error_sintactico(509, lista_tokens.get(puntero).renglon);
            }
        }
    }
    public void lista_sentencias()
    {
        if(lista_tokens.get(puntero).token == 100) sentencia_asignacion(); // asignacion
        if(lista_tokens.get(puntero).token == 211) sentencia_if(); // if
        if(lista_tokens.get(puntero).token == 215) sentencia_print(); // print
        if(lista_tokens.get(puntero).token == 214) sentencia_loop(); // loop
    }
    
    public int obtener_tipo_variable(String nombre_var)
    {
        int tipo = 0;
        for (nodo_variable lista_variable : lista_variables) {
            if (lista_variable.nombre_var.equals(nombre_var)) {
                tipo = lista_variable.tipo_var;
                break;
            }
        }
        return tipo;
    }
    
    public void sentencia_asignacion()
    { 
        boolean variable_declarada = lista_variables.stream().anyMatch(var -> var.nombre_var.equals(lista_tokens.get(puntero).lexema));
        if(!variable_declarada)  agregar_error_semantico(521, lista_tokens.get(puntero).renglon); 
        
        int temp_tipo = obtener_tipo_variable(lista_tokens.get(puntero).lexema);
        lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
        agregar_operando(temp_tipo);
        
        
        puntero++;
        if(lista_tokens.get(puntero).token == 116) // :=
        {
            agregar_operador(lista_tokens.get(puntero).token);
            puntero++;
            expresiones_simples();
            if(lista_tokens.get(puntero).token == 115) // ;
            {
                ListIterator<Integer> iterador_pila = pila_auxiliar_operadores.listIterator(pila_auxiliar_operadores.size());
                while(iterador_pila.hasPrevious())
                {
                    lista_polish.add(iterador_pila.previous());
                }
                for(int i = pila_auxiliar_operadores.size()-1; i >= 0; i--)
                {
                    switch ( pila_auxiliar_operadores.get(i) )
                    {

                        case 110: lista_polish_buffer.add(">"); break;
                        case 107: lista_polish_buffer.add("!="); break;
                        case 109: lista_polish_buffer.add(">="); break;
                        case 111: lista_polish_buffer.add("<="); break;
                        case 112: lista_polish_buffer.add("<"); break;
                        case 113: lista_polish_buffer.add("="); break; 
                        case 106: lista_polish_buffer.add("*"); break;
                        case 108: lista_polish_buffer.add("/"); break;
                        case 104: lista_polish_buffer.add("+"); break;
                        case 105: lista_polish_buffer.add("-"); break;
                        case 116: lista_polish_buffer.add(":="); break; 
                    }
                }
                comprobar_tipos();
                vaciar_pilas();
                vaciar_lista_polish();
                puntero++;
                lista_sentencias();
            }
            else
            {
                agregar_error_sintactico(509, lista_tokens.get(puntero).renglon);
            }
        }
        else
        {
            agregar_error_sintactico(512, lista_tokens.get(puntero).renglon);
        }
    }
    
    public void sentencia_if()
    {
        A++;
        B++;
        puntero++;
        expresiones_relacionales();
        if(lista_tokens.get(puntero).token == 218) // then
        {
            ifs_anidados++;
            ListIterator<Integer> iterador_pila = pila_auxiliar_operadores.listIterator(pila_auxiliar_operadores.size());
            
            while(iterador_pila.hasPrevious())
            {
                lista_polish.add(iterador_pila.previous());
            }
            
            for(int i = 0; i < pila_auxiliar_operadores.size(); i++)
            {
                switch ( pila_auxiliar_operadores.get(i) )
                {

                    case 110: lista_polish_buffer.add(">"); break;
                    case 107: lista_polish_buffer.add("!="); break;
                    case 109: lista_polish_buffer.add(">="); break;
                    case 111: lista_polish_buffer.add("<="); break;
                    case 112: lista_polish_buffer.add("<"); break;
                    case 113: lista_polish_buffer.add("="); break; 
                    case 106: lista_polish_buffer.add("*"); break;
                    case 108: lista_polish_buffer.add("/"); break;
                    case 104: lista_polish_buffer.add("+"); break;
                    case 105: lista_polish_buffer.add("-"); break;
                    case 116: lista_polish_buffer.add(":="); break; 
                }
            }
            lista_polish_buffer.add("BRF-"+ (A == 1 ? "A" : "A"+A));

            comprobar_tipos();
            vaciar_pilas();
            vaciar_lista_polish();
            puntero++;
            lista_sentencias();
            
            if(lista_tokens.get(puntero).token == 220) // else
            {
                puntero++;
                lista_sentencias();
            }

            if(lista_tokens.get(puntero).token == 203) // end
            {
                puntero++;
                if(lista_tokens.get(puntero).token == 211) // if
                {
                    puntero++;
                    if(lista_tokens.get(puntero).token == 115) // punto y coma
                    {                       
                        puntero++;
                        lista_polish_buffer.add("BRI-" + (B == 1 ? "B" : "B"+B));
                        lista_polish_buffer.add((A == 1 ? "A" : "A"+A));
                        lista_polish_buffer.add((B == 1 ? "B" : "B"+B));
                       // lista_polish_buffer.add("BRI-B"+B);
                       // lista_polish_buffer.add("A"+A);
                       // lista_polish_buffer.add("B"+B);
                        A--;
                        B--;
                        if(lista_tokens.get(puntero).token != 203) lista_sentencias();
                    }
                }
            } 
        }
        /*if(lista_tokens.get(puntero).token == 218) // then
        {
            ListIterator<Integer> iterador_pila = pila_auxiliar_operadores.listIterator(pila_auxiliar_operadores.size());
            
            while(iterador_pila.hasPrevious())
            {
                lista_polish.add(iterador_pila.previous());
            }
            
            for(int i = 0; i < pila_auxiliar_operadores.size(); i++)
            {
                switch ( pila_auxiliar_operadores.get(i) )
                {

                    case 110: lista_polish_buffer.add(">"); break;
                    case 107: lista_polish_buffer.add("!="); break;
                    case 109: lista_polish_buffer.add(">="); break;
                    case 111: lista_polish_buffer.add("<="); break;
                    case 112: lista_polish_buffer.add("<"); break;
                    case 113: lista_polish_buffer.add("="); break; 
                    case 106: lista_polish_buffer.add("*"); break;
                    case 108: lista_polish_buffer.add("/"); break;
                    case 104: lista_polish_buffer.add("+"); break;
                    case 105: lista_polish_buffer.add("-"); break;
                    case 116: lista_polish_buffer.add(":="); break; 
                }
            }
            
            lista_polish_buffer.add("BRF-"+ (A == 0 ? "A" : "A"+A));
            
            lista_polish.stream().forEach(item -> System.out.println(item));
            System.out.println("");
            
            
            comprobar_tipos();
            vaciar_pilas();
            vaciar_lista_polish();
            
            puntero++;
            lista_sentencias();
            
            if(lista_tokens.get(puntero).token == 220) // else
            {
                puntero++;
                lista_sentencias();
            }
            
         
            if(lista_tokens.get(puntero).token == 203) // end
            {
                System.out.println(puntero);
                System.out.println("qlol");
                puntero++;
                if(lista_tokens.get(puntero).token == 211) // if
                {
                    System.out.println("qdadas");
                    puntero++;
                    System.out.println("adsadsas");
                    if(lista_tokens.get(puntero).token == 115) // punto y coma
                    {
                        puntero++;

                        lista_polish_buffer.add("BRI-" + (B == 0 ? "B" : "B"+B));
                        lista_polish_buffer.add((A == 0 ? "A" : "A"+A));
                        lista_polish_buffer.add((B == 0 ? "B" : "B"+B));
                        
                        A++;
                        B++;
                        System.out.println(lista_tokens.get(puntero).token);
                        if(lista_tokens.get(puntero).token != 203) lista_sentencias();
                    }
                }
            }
        }
        if(lista_tokens.get(puntero).token == 204 || lista_tokens.get(puntero).token == 205) // and |  or  |
        {
            agregar_operador(lista_tokens.get(puntero).token);
            puntero++;
            expresiones_relacionales();
            sentencia_if();
        }
        /*if(lista_tokens.get(puntero).token == 218) // then
        {
            ListIterator<Integer> iterador_pila = pila_auxiliar_operadores.listIterator(pila_auxiliar_operadores.size());
            while(iterador_pila.hasPrevious())
            {
                lista_polish.add(iterador_pila.previous());
                //iterador_pila.remove();
            }    
            lista_polish.stream().forEach(item -> System.out.println(item));
            comprobar_tipos();
            vaciar_pilas();
            vaciar_lista_polish();
            puntero++;
            lista_sentencias();
            if(lista_tokens.get(puntero).token == 203) // end
            {
                puntero++;
                if(lista_tokens.get(puntero).token == 213) // if
                {
                    puntero++;
                    if(lista_tokens.get(puntero).token == 115)
                    {
                        puntero++;
                        if(lista_tokens.get(puntero).token != 203) lista_sentencias();
                    }
                }
            }
        }*/
    }
    
    public void sentencia_print()
    {
        puntero++;
        if(lista_tokens.get(puntero).token == 118) // (
        {
            puntero++;
            expresiones_simples_print();
            if(lista_tokens.get(puntero).token == 119) // )
            {
                lista_polish_buffer.add("print");
                puntero++;
                if(lista_tokens.get(puntero).token == 115) // punto y coma
                {
                    puntero++;
                    lista_sentencias();
                }
            }
        }
       /*     if(lista_tokens.get(puntero).token == 120) // pregunta si es string 
            {
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                lista_polish_buffer.add("print");
                
                puntero++;
                if(lista_tokens.get(puntero).token == 119) // )
                {
                    puntero++;
                    if(lista_tokens.get(puntero).token == 115) // punto y coma
                    {
                        puntero++;
                        lista_sentencias();
                    }
                }
            }
        }*/
    }
    
    public void sentencia_loop()
    {
        E++;
        puntero++;
        lista_sentencias();
        if(lista_tokens.get(puntero).token == 221) // exit
        {
            puntero++;
            if(lista_tokens.get(puntero).token == 222) // when
            {
                puntero++;
                expresiones_relacionales();
                if(lista_tokens.get(puntero).token == 115) // punto y coma
                {
                    puntero++;
                    if(lista_tokens.get(puntero).token == 203) // end
                    {
                        puntero++;
                        if(lista_tokens.get(puntero).token == 214) // loop
                        {
                            puntero++;
                            if(lista_tokens.get(puntero).token == 115) // punto y coma
                            {
                                ListIterator<Integer> iterador_pila = pila_auxiliar_operadores.listIterator(pila_auxiliar_operadores.size());
                                while(iterador_pila.hasPrevious())
                                {
                                    lista_polish.add(iterador_pila.previous());
                                    //iterador_pila.remove();
                                }
                                for(int i = pila_auxiliar_operadores.size()-1; i >= 0; i--)
                                {
                                    switch ( pila_auxiliar_operadores.get(i) )
                                    {
                                        case 110: lista_polish_buffer.add(">"); break;
                                        case 107: lista_polish_buffer.add("!="); break;
                                        case 109: lista_polish_buffer.add(">="); break;
                                        case 111: lista_polish_buffer.add("<="); break;
                                        case 112: lista_polish_buffer.add("<"); break;
                                        case 113: lista_polish_buffer.add("="); break; 
                                        case 106: lista_polish_buffer.add("*"); break;
                                        case 108: lista_polish_buffer.add("/"); break;
                                        case 104: lista_polish_buffer.add("+"); break;
                                        case 105: lista_polish_buffer.add("-"); break;
                                        case 116: lista_polish_buffer.add(":="); break; 
                                    }
                                }
                                lista_polish_buffer.add("BRF-"+ (E == 1 ? "E" : "E"+E));

                                comprobar_tipos();
                                vaciar_pilas();
                                vaciar_lista_polish();
                                puntero++;
                                E--;
                                if(lista_tokens.get(puntero).token != 203) lista_sentencias();
                            } 
                        }
                    }
                }
            }
        }
    }
    
    public void expresiones_simples_print()
    {
        switch ( lista_tokens.get(puntero).token )
        {
            case 100:
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                puntero++;
                break;
            case 120:
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                puntero++;
                break;
            
            default:
                agregar_error_semantico(512, lista_tokens.get(puntero).renglon);
                break;
                
        }
    }
    
    public void expresiones_simples()
    {
        switch ( lista_tokens.get(puntero).token )
        {
            case 105: // Operador unario (-)
            {
                puntero++;
                expresiones_simples();
                break;
            }
            case 118: // Inicio parentesis
            {
                agregar_operador(lista_tokens.get(puntero).token);
               // agregar_operador(118);
                puntero++;
                expresiones_simples();
                if(lista_tokens.get(puntero).token == 119)  // Fin del parentesis
                {
                 //   agregar_operador(119);
                    agregar_operador(lista_tokens.get(puntero).token);
                    puntero++;
                    operadores_aritmeticos();
                }
                else
                {
                    agregar_error_sintactico(513, lista_tokens.get(puntero).renglon);
                }
                break;               
            }
            case 100: // ID
                
                boolean variable_declarada = lista_variables.stream().anyMatch(var -> var.nombre_var.equals(lista_tokens.get(puntero).lexema));
                if(!variable_declarada)  agregar_error_semantico(521, lista_tokens.get(puntero).renglon);
                
                int temp_tipo = obtener_tipo_variable(lista_tokens.get(puntero).lexema);
                
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                
                agregar_operando(temp_tipo);

                puntero++;
                operadores_aritmeticos();
                break;
                
            case 101: // Numero Entero
                agregar_operando(lista_tokens.get(puntero).token);
                
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                
                puntero++;
                operadores_aritmeticos();
                break;
            case 102: // Numero Decimal
                agregar_operando(lista_tokens.get(puntero).token);
                puntero++;
                operadores_aritmeticos();
                break;    
            case 120: // String
                agregar_operando(lista_tokens.get(puntero).token);
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                puntero++;
                operadores_aritmeticos();
                break;
        }
    }
    
    public void expresiones_relacionales()
    {
        switch ( lista_tokens.get(puntero).token )
        {
            case 100: // id
                
                boolean variable_declarada = lista_variables.stream().anyMatch(var -> var.nombre_var.equals(lista_tokens.get(puntero).lexema));
                if(!variable_declarada)  agregar_error_semantico(521, lista_tokens.get(puntero).renglon);
                
                int temp_tipo = obtener_tipo_variable(lista_tokens.get(puntero).lexema);
                agregar_operando(temp_tipo);
                
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                
                puntero++;
                operadores_aritmeticos();
                operadores_relacionales();
            break;
            case 101: // numero entero
                agregar_operando(lista_tokens.get(puntero).token);
                
                lista_polish_buffer.add(lista_tokens.get(puntero).lexema);
                
                puntero++;
                operadores_aritmeticos();
                operadores_relacionales();
            break;
            case 102: // numero_decimal
                puntero++;
                operadores_aritmeticos();
                operadores_relacionales();
            break;
            
            case 120: // string
                agregar_operando(lista_tokens.get(puntero).token);
                puntero++;
                operadores_aritmeticos();
                operadores_relacionales();
            break;
            
           default:
                agregar_error_sintactico(512, lista_tokens.get(puntero).renglon);
            break;  
        }
    }
    
    public void operadores_relacionales()
    {
        switch ( lista_tokens.get(puntero).token )
        {
            case 107: //            diferente de 
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_relacionales();
            break;   
                
            case 109: //            mayor o igual que
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_relacionales();
            break;
                
            case 110: //            mayor que
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_relacionales();
            break;
                
            case 111: //            menor o igual que
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_relacionales();
            break;
                
            case 112: //            menor que
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;

                expresiones_relacionales();
            break;
                
            case 113: //            igual a 
                
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_relacionales();
            break;
        }
    }
    
    public void operadores_aritmeticos()
    {
        switch ( lista_tokens.get(puntero).token )
        {
            case 104: // suma
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_simples();
            break;
                
            case 105: // resta
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_simples();
            break;
                
            case 106: // mult
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_simples();
            break;
                
            case 108: // divi
                agregar_operador(lista_tokens.get(puntero).token);
                puntero++;
                expresiones_simples();
            break;     
        }
    }
    
    public void comprobar_tipos()
    {
       lista_polish.stream().filter((tipo) -> (tipo == 101 || tipo == 102 || tipo == 120) //  numero entero || numero decimal || string
        ).map((tipo) -> {
            pila_auxiliar_operandos.add(tipo);
            return tipo;
        }).forEach((tipo) -> {
            lista_polish.remove(tipo);
        });
        
        for(Integer operador : lista_polish)
        {
            if(pila_auxiliar_operandos.size() >= 2)
            {
                int resultado = 0;

                int operando2 = pila_auxiliar_operandos.peek();
                int idx_operando1 = pila_auxiliar_operandos.size()-2;
                int operando1 = pila_auxiliar_operandos.get(idx_operando1);

                pila_auxiliar_operandos.pop();
                pila_auxiliar_operandos.pop();
                
                if(operador == 110 || operador == 107 || operador == 109 || operador == 111 || operador == 112 || operador == 113) 
                // operadores relacionales
                {
                    if(operando1 == 101 && operando2 == 101)
                    {
                        resultado = 103;
                        //pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 101 && operando2 == 102)
                    {
                        resultado = 103;
                        //pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 102 && operando2 == 102)
                    {
                        resultado = 103;
                        //pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 102 && operando2 == 101)
                    {
                        resultado = 103;
                        //pila_auxiliar_operandos.add(resultado);
                    }
                    if(resultado == 0)
                    {
                        agregar_error_semantico(522, lista_tokens.get(puntero).renglon);
                        return;
                    }
                }
                
                /*if(operador == 204) // and 
                {
                    System.out.println("xdd");
                    if(operando1 == 103 && operando2 == 103)
                    {
                        resultado = 103;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(resultado == 0)
                    {
                        agregar_error_semantico(522, lista_tokens.get(puntero).renglon);
                        return;
                    }
                }*/
                    
                if(operador == 104) // suma
                {
                    if(operando1 == 101 && operando2 == 101) // se suma un numero entero con un numero entero
                    {
                        resultado = 101;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 101 && operando2 == 102) // se suma un numero entero con un numero decimal
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    } 
                    if(operando1 == 102 && operando2 == 102) // se suma un numero decimal con un numero decimal
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 102 && operando2 == 101) // se suma un numero decimal (102) con un numero entero (101)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(resultado == 0)
                    {
                        agregar_error_semantico(522, lista_tokens.get(puntero).renglon);
                        return;
                    }
                }
                
                if(operador == 105) // resta
                {
                    if(operando1 == 101 && operando2 == 101) // se suma un numero entero con un numero entero
                    {
                        resultado = 101;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 101 && operando2 == 102) // se suma un numero entero con un numero decimal
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    } 
                    if(operando1 == 102 && operador == 102) // se suma un numero decimal con un numero decimal
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 102 && operador == 101) // se suma un numero decimal (102) con un numero entero (101)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    
                    if(resultado == 0)
                    {
                        agregar_error_semantico(522, lista_tokens.get(puntero).renglon);
                        return;
                    }
                }
                
                if(operador == 106) // multiplicacion
                {
                    if(operando1 == 101 && operando2 == 101)
                    {
                        resultado = 101;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    
                    if(operando1 == 101 && operando2 == 102)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 102 && operando2 == 101)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);  
                    }
                    if(operando1 == 102 && operando2 == 102)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    
                    if(resultado == 0)
                    {
                        agregar_error_semantico(522, lista_tokens.get(puntero).renglon);
                        return;
                    }
                }
                
                if(operador == 108) // division
                {
                    if(operando1 == 101 && operando2 == 101)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 101 && operando2 == 102)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    
                    if(operando1 == 102 && operando2 == 101)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    if(operando1 == 102 && operando2 == 102)
                    {
                        resultado = 102;
                        pila_auxiliar_operandos.add(resultado);
                    }
                    
                    if(resultado == 0)
                    {
                        agregar_error_semantico(522, lista_tokens.get(puntero).renglon);
                        return;
                    }
                }  
                if(operador == 116) // asignacion
                {
                    if(operando1 == 120 && operando2 == 120) resultado = 120;
                    if(operando1 == 101 && operando2 == 101) resultado = 101;
                    if(operando1 == 102 && operando2 == 102) resultado = 102;                 
                    if(resultado == 0)
                    {
                        agregar_error_semantico(522, lista_tokens.get(puntero).renglon);
                        return;
                    }
                }
            }
        } 
    }
    
    public void agregar_operando(int operando)
    {
        lista_polish.add(operando);
    }
    
    public void agregar_operador(int operador)
    {
        if(pila_auxiliar_operadores.isEmpty())
        {
            pila_auxiliar_operadores.add(operador);
        }
        else
        {
            if(operador == 119) // final parentesis 
            {
                for(int i = pila_auxiliar_operadores.size()-1; i >= 0 ; i--)
                {
                    int op = pila_auxiliar_operadores.get(i);
                    if(op == 118)
                    {
                        pila_auxiliar_operadores.pop();
                        break;
                    }
                    else
                    {
                        lista_polish.add(op);
                        pila_auxiliar_operadores.pop();
                    }
                }
                
                return;
            }
            if(operador == 118) // apertura de parentesis
            {
                pila_auxiliar_operadores.add(operador);
                return;
            }
            
            for(int i = pila_auxiliar_operadores.size()-1; i >= 0 ; i--)
            {
                int op = pila_auxiliar_operadores.get(i);    
                if(comprobar_prioridad(operador) > comprobar_prioridad(op))
                {
                    pila_auxiliar_operadores.add(operador);
                    break;
                }            
                if(comprobar_prioridad(operador) <= comprobar_prioridad(op))
                {
                    if(i == pila_auxiliar_operadores.size()-1)
                    {
                        pila_auxiliar_operadores.pop();
                        pila_auxiliar_operadores.add(operador);
                        lista_polish.add(op);
                        break;
                    }
                    pila_auxiliar_operadores.pop();              
                    lista_polish.add(op);
                    switch (op)
                    {
                        case 110: lista_polish_buffer.add(">"); break;
                        case 107: lista_polish_buffer.add("!="); break;
                        case 109: lista_polish_buffer.add(">="); break;
                        case 111: lista_polish_buffer.add("<="); break;
                        case 112: lista_polish_buffer.add("<"); break;
                        case 113: lista_polish_buffer.add("="); break; 
                        case 106: lista_polish_buffer.add("*"); break;
                        case 108: lista_polish_buffer.add("/"); break;
                        case 104: lista_polish_buffer.add("+"); break;
                        case 105: lista_polish_buffer.add("-"); break;
                        case 116: lista_polish_buffer.add(":="); break; 
                    }
                }
            }
        }
        
    }
    public int comprobar_prioridad(int operador)
    {
        if(operador == 106 || operador == 108) // * | /
        {
            return 6;
        }        
        if(operador == 104 || operador == 105) // + | -
        {
            return 5;
        }
        if(operador == 110 || operador == 107 || operador == 109 || operador == 111 || operador == 112 || operador == 113) // op relacionales
        {
            return 4;
        }
        if(operador == 204 || operador == 205 || operador == 207) // and | or | not
        {
            return 3;
        }
        if(operador == 116) // :=
        {
            return 2;
        }
        if(operador == 118) // parentesis
        {
            return 1;
        }
        return 0;
    }
    
    public void vaciar_pilas()
    {
        pila_auxiliar_operandos.clear();
        pila_auxiliar_operadores.clear();
    }
    public void vaciar_lista_polish()
    {
        lista_polish.clear();
    }
    
    public void ensamblador() throws IOException
    {
        Stack <String> pila_aux_operandos = new Stack <>();
        Stack <String> pila_aux_operadores = new Stack <>();
        
        String CadenaVar="";
        String CadenaEnsamblador;
        String Operando2;
        String cadena_asm="";
        int Tempcont = 0;
        int Tempcont2 = 0;
        
        CadenaVar += "INCLUDE Macros.MAC\n" +
                           "DOSSEG\n" +
                           ".MODEL SMALL \n" +
                           ".STACK 100h\n" +
                           ".DATA \n";
        
        for (nodo_variable lista_variable : lista_variables) 
        {
            switch ( lista_variable.tipo_var )
            {
                case 101: // Entero
                {
                    CadenaVar += "\t\t" + lista_variable.nombre_var + " dw ? \n";
                    break;
                }
                case 102: // Numero Decimal
                {
                    CadenaVar += "\t\t" + lista_variable.nombre_var + " db 0 \n";
                    break;
                }
                case 120: // Cadena
                {
                    CadenaVar += "\t\t" + lista_variable.nombre_var + " db 254 dup ('$') \n";
                    break;
                }
            }
        }
        
        cadena_asm += "\t\tTempINT dw ?\n" +
                           "\t\tTempCOMPARACION dw ?\n";

            cadena_asm += ".CODE\n" +
                           ".386\n" +
                           "BEGIN:\n" +
                           "\t\tMOV AX, @DATA\n" +
                           "\t\tMOV DS, AX\n" +
                           "CALL COMPI\n" +
                           "\t\tMOV AX, 4C00H\n" +
                           "\t\tINT 21H\n\n" +
                           "COMPI PROC\n";
            
        for(String polish: lista_polish_buffer)
        {
        //lista_polish_buffer.forEach((polish) -> {
            if(polish.equals("+") || polish.equals("-") || polish.equals("*") || polish.equals(":=") || polish.equals("print"))
            {
                pila_aux_operadores.add(polish);
            }
            else
            {
                pila_aux_operandos.add(polish);
                boolean variable_declarada = lista_variables.stream().anyMatch(var -> var.nombre_var.equals(polish));
                if(!variable_declarada)  
                {
                    if(isInteger(polish))
                    {
                        lista_variables.add(new nodo_variable(polish, 101));
                    }
                    else if(isDouble(polish))
                    {
                        lista_variables.add(new nodo_variable(polish, 102));
                    }
                    else
                    {
                        CadenaVar += "\t\tCadenaTemp" + Tempcont + " db " + polish.substring(0, polish.length() - 1) + "$\"\n";
                        lista_variables.add(new nodo_variable(polish, 120));
                        Tempcont++;
                    }
                }
            }
        }
        //}); 

        
        System.out.println("");
        for(int i = pila_aux_operandos.size()-1; i >= 0 ; i--)
        {
            System.out.println(pila_aux_operandos.get(i));
        }
        System.out.println("");
        for(int i = pila_aux_operadores.size()-1; i >= 0 ; i--)
        {
            System.out.println(pila_aux_operadores.get(i));
        }
        System.out.println("");
        
        for (String operador : pila_aux_operadores) 
        {
            if(operador.equals("+"))
            {
                Operando2 = pila_aux_operandos.pop();
                cadena_asm += "\t\tSUMAR " + pila_aux_operandos.pop() + ", " + Operando2 + ", TempINT\n";
                pila_aux_operandos.push("TempINT");
                lista_variables.add(new nodo_variable("TempINT", 101));
            }
            if(operador.equals(":="))
            {
                Operando2 = pila_aux_operandos.pop();
                if(obtener_tipo_variable(pila_aux_operandos.peek()) == 101) // Entero
                {
                    cadena_asm += "\t\tI_ASIGNAR " + pila_aux_operandos.pop() + ", " + Operando2 + "\n";
                }
                if (obtener_tipo_variable(pila_aux_operandos.peek()) == 120) //Cadena
                {
                    //cadena_asm += "\t\tS_ASIGNAR " + pila_aux_operandos.pop() + ", " + Operando2 + "\n";
                    /*cadena_asm += "\t\tS_ASIGNAR " + pila_aux_operandos.pop() + ", CadenaTemp" + Tempcont2 + "\n";
                    Tempcont2++;
                    if(Tempcont2 == Tempcont)
                    {
                        pila_aux_operandos.add(operador)
                    }*/
                    //encontrado = false;
                    System.out.println(Tempcont2);
                    if(Tempcont2 == (Tempcont-1))
                    {
                        String tmp = pila_aux_operandos.pop();
                        System.out.println(tmp);
                        System.out.println("xdd");
                        cadena_asm += "\t\tS_ASIGNAR " + tmp + ", CadenaTemp" + Tempcont2 + "\n";
                        //pila_aux_operandos.pop();
                        pila_aux_operandos.add(tmp);
                    }
                    else
                    {
                        cadena_asm += "\t\tS_ASIGNAR " + pila_aux_operandos.pop() + ", CadenaTemp" + Tempcont2 + "\n";
                        Tempcont2++;
                    }
                }
            }
            if(operador.equals("print"))
            {
                if (obtener_tipo_variable(pila_aux_operandos.peek()) == 101)
                {
                    cadena_asm += "\t\tWRITENUM " + pila_aux_operandos.pop() + "\n\t\tWRITELN\n";
                }
                if (obtener_tipo_variable(pila_aux_operandos.peek()) == 120)
                {
                    cadena_asm += "\t\tWRITE " + pila_aux_operandos.pop() + "\n\t\tWRITELN\n";
                }
            }
        }
        cadena_asm += "\t\tret \nCOMPI ENDP \nEND BEGIN";
        CadenaEnsamblador = CadenaVar + cadena_asm;
        
        try (FileWriter myWriter = new FileWriter("C:/tasm/spark.asm")) {
            myWriter.write(CadenaEnsamblador);
        }
    }
    
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    public static boolean isDouble(String s) {
        try { 
            Double.parseDouble(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
}



