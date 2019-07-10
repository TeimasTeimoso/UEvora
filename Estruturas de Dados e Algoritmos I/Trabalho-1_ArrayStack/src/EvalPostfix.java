import java.util.Scanner;
import java.util.StringTokenizer;

class EvalPostfix{

    double op2, op1;
    boolean numeric, verbose;
    ArrayStack stack;
    Scanner scanner;
    String input, token;
    StringTokenizer sub_strings;
    
    public EvalPostfix(){
        sub_strings = new StringTokenizer("2 3 + 5 /");
        stack = new ArrayStack(sub_strings.countTokens());
        System.out.println(Operation(sub_strings, stack, verbose));
        verbose = false;
    }

    public EvalPostfix(boolean verbose){
        scanner = new Scanner(System.in);
        input = scanner.nextLine(); 
        sub_strings = new StringTokenizer(input);
        ArrayStack stack = new ArrayStack(sub_strings.countTokens());
        System.out.println(Operation(sub_strings, stack, verbose));

    }

    //Mehtod that verifies if the token is a number
    //if its a number return true, else false
    public boolean Numeric(){
        boolean numeric = true;
        token = sub_strings.nextToken();
        try {
            Double.parseDouble(token);
        } catch (NumberFormatException e) {
            numeric = false;
        }
        return numeric;
    }


    //Method that makes the operations with op from the stack
    @SuppressWarnings("unchecked")
    public String Operation(StringTokenizer sub_strings, ArrayStack stack, boolean verbose){  
        while(sub_strings.hasMoreTokens()){
            if(Numeric()){
                stack.push(Double.parseDouble(token));
                if(verbose){
                    System.out.println(token + " is on stack");
                }
            }else{
                op2 = Double.parseDouble(String.valueOf(stack.pop())); 
                op1 = Double.parseDouble(String.valueOf(stack.pop())); 
                
                if(verbose){
                  System.out.println(op2 + " is out of stack");
                  System.out.println(op1 + " is out of stack");
                }
                
                switch(token){
                    case "+": 
                    stack.push(op1+op2); 
                    if(verbose){
                        System.out.println(op1+"+"+op2+"="+(op1+op2));
                    }
                    break; 

                    case "-": 
                    stack.push(op1-op2); 
                    if(verbose){
                        System.out.println(op1+"-"+op2+"="+(op1-op2));
                    }
                    break;

                    case "*": 
                    stack.push(op1*op2); 
                    if(verbose){
                        System.out.println(op1+"*"+op2+"="+(op1*op2));
                    }
                    break;

                    case "/": 
                    stack.push(op1/op2);
                    if(verbose){
                        System.out.println(op1+"/"+op2+"="+(op1/op2));
                    } 
                    break;
                }
            
            }
        }
        if(verbose){
            System.out.println(stack.toString());
        }
        return "O resultado final é: " + String.valueOf(stack.top());
    }
}