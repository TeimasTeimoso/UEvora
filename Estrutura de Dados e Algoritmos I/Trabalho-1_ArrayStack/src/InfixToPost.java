import java.util.Scanner;
import java.util.StringTokenizer;

class InfixToPost{
    
    String output, token, input;
    boolean numeric, verbose;
    ArrayStack stack;
    Scanner scanner;
    StringTokenizer sub_strings;

    public InfixToPost(){
        sub_strings = new StringTokenizer("2 + 3 * ( 10 + 2 )");
        stack = new ArrayStack(sub_strings.countTokens());   
        verbose = false;
        System.out.println(Conversion(sub_strings, stack, verbose));
    }

    public InfixToPost(boolean verbose){
        scanner = new Scanner(System.in);
        input = scanner.nextLine();
        sub_strings = new StringTokenizer(input);
        stack = new ArrayStack(sub_strings.countTokens());
        System.out.println(Conversion(sub_strings, stack, verbose));
    }

    //Sets the operators prioritys
    //The higher the number the most importance it has
    public int Priority(String i){
        switch(i){
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "(":
            case ")":
                return 3;
        }
        return 0;
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

    @SuppressWarnings("unchecked")
    public String Conversion(StringTokenizer sub_strings, ArrayStack stack, boolean verbose){
        output = "";

        while(sub_strings.hasMoreTokens()){
            if(Numeric()){
                output += token +  " ";
            }else{
                if(token.equals(")")){                                      //If token is a closed brace
                    while(!String.valueOf(stack.top()).equals("(")){        //Stack will pop out till it finds
                        output += String.valueOf(stack.pop()) + " ";        //A open brace
                        if(verbose){
                            System.out.println("stack.pop");
                        }
                    }
                    stack.pop();                                            //which then pops out
                }else{
                    if(Priority(token) > Priority(String.valueOf(stack.top()))){
                        stack.push(token);                                  //if the token is higher than the top of the stack it will push it to stack
                        if(verbose){
                            System.out.println("stack.push");
                        }
                    }else{
                        //While the next token is less important than the top of the stack, and the top is not "(" or ")" the stack will pop and than push the token
                        while(Priority(String.valueOf(stack.top()))!= 3 && Priority(String.valueOf(stack.top()))>=Priority(token)){ 
                            output += String.valueOf(stack.pop()) + " ";
                            if(verbose){
                                System.out.println("stack.pop");
                            }
                        }
                        stack.push(token);
                        if(verbose){
                            System.out.println("stack.push");
                        }
                    }
                }
            }
        }
        while(!stack.empty()){                              //while the stack is not empty it will keep popping out
            output += String.valueOf(stack.pop()) + " ";
            if(verbose){
                System.out.println("stack.pop");
            }
        }
        return "O resultado final é: " + output;
    }
}