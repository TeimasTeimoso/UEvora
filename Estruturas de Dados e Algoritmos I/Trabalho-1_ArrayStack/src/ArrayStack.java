class ArrayStack<E> implements Stack<E>{
    
    int size, pos;
    E[] array_stack; 

    //class constructor    
    @SuppressWarnings("unchecked")
    public ArrayStack(int size){
        array_stack = (E[]) new Object[size];
        this.size = size;
        pos = -1;
    }

    /*method that adds
     *objects in the array while
     *there is still space available
     */
    @Override
    public void push(E o){
        if(pos<size-1){  //size-1 because when evaluating the if statement, pos starts at -1
            pos++;
            array_stack[pos] = o;
        }else{
            throw new StackOverflowError("stack overflow");
        }
    }

    //method that returns top of the stack
    @Override
    public E top(){
        if(empty()){
            return null;
        }else{
            return array_stack[pos];
        }
    }

    //It must remove the op of the stack and show which element it has removed
    @Override
    public E pop(){
        if(empty()){
            return null;
        }else{
            E x = array_stack[pos];
            array_stack[pos] = null; 
            pos--;
            return x;     //it still doesnt work
        }
    }

    //method that returns the size of the array
    @Override
    public int size(){
        return pos+1; //+1 because the first value of pos after a push will be 0
    }

    //method that tells if array is empty or not
    @Override
    public boolean empty(){
        if(pos == -1){
            return true;
        }else{
            return false;
        }
    }

    

    public String toString() {
		int i=0;
		String stack_print = " ";

		for(i=0;i<size;i++){
			stack_print = stack_print + array_stack[i] + " ";
		}
		return stack_print;
    }
}
