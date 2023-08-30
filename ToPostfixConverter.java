import java.util.Stack;
public class ToPostfixConverter implements Converter{

    private void lowerPrecedence(Stack<String> stack, String token, StringBuilder output){
        output.append(stack.pop());
        output.append(" ");
        if (stack.empty())
            stack.push(token);
        else if(Operator.of(token.charAt(0)).getRank() > Operator.of(stack.peek().charAt(0)).getRank())
            lowerPrecedence(stack, token, output);
        else if (Operator.of(token.charAt(0)).getRank() == Operator.of(stack.peek().charAt(0)).getRank())
            equalPrecedence(stack, token, output);
        else
            stack.push(token);
    }
    private void equalPrecedence(Stack<String> stack, String token, StringBuilder output){
        String element = stack.pop();
        output.append(element);
        output.append(" ");
        stack.push(token);
    }



    public String convert(ArithmeticExpression expression) {
        Stack<String> stack = new Stack<>();
        String str = expression.getExpression();
        int start = 0;
        StringBuilder output = new StringBuilder();
        String element = "";

        while (start < str.length()){

            String token = nextToken(str, start);
            if (token.equals(""))
                break;
            char token_char = token.charAt(0);
            // if token is an operator
            if (token.length() == 1 && !isOperand(token)){
                Operator operator = Operator.of(token_char);
                switch (operator){
                    case LEFT_PARENTHESIS:
                        stack.push(""+operator.getSymbol());
                        break;
                    case RIGHT_PARENTHESIS:
                        element = stack.pop();
                        while(!Operator.of(element.charAt(0)).equals(Operator.LEFT_PARENTHESIS)){
                            output.append(element);
                            output.append(" ");
                            element = stack.pop();
                        }
                        break;
                    default:
                        if(stack.empty() || (stack.peek()).equals(""+Operator.LEFT_PARENTHESIS.getSymbol())){
                            stack.push(token);
                        }
                        else if(Operator.of(token_char).getRank() < Operator.of(stack.peek().charAt(0)).getRank()){
                            stack.push(token);
                        }
                        else if(Operator.of(token_char).getRank() == Operator.of(stack.peek().charAt(0)).getRank()){
                            equalPrecedence(stack, token, output);
                        }
                        else if(Operator.of(token_char).getRank() > Operator.of(stack.peek().charAt(0)).getRank()){
                            lowerPrecedence(stack, token, output);
                        }
                }
            }
            // if token is an operand
            else{
                output.append(token);
                output.append(" ");
            }


            start += token.length();
            while (start < str.length() && str.charAt(start) == ' '){
                start++;
            }

        }
        while (!stack.empty()){
            output.append(stack.pop());
            output.append(" ");
        }


        return output.toString();
    }

    public String nextToken(String s, int start) {

        if (start >= s.length())
            throw new IndexOutOfBoundsException();

        char c = s.charAt(start);

        TokenBuilder t = new TokenBuilder();

        if (Operator.isOperator(c)){
            t.append(c);
        }
        else if (isOperand(""+c)){
            // end index equals to start
            int end = start;

            while (end < s.length() && isOperand(s.substring(end,end+1)))
                end++;

            for (int i = 0; i < end-start; i++)
                t.append(s.charAt(i+start));

        }
        else{
            if (Operator.of(c).equals(Operator.LEFT_PARENTHESIS) || Operator.of(c).equals(Operator.RIGHT_PARENTHESIS)){
                t.append(c);
            }

        }
        return t.build();
    }

    public boolean isOperand(String s) {
        for(char c: s.toCharArray()){
            if (!Character.isLetterOrDigit(c))
                return false;
        }
        return true;
    }
    static class TokenBuilder{
        private StringBuilder tokenBuilder = new StringBuilder();
        public void append(char c) {
            tokenBuilder.append(c);
        }
        public String build() {
            return tokenBuilder.toString();
        }
    }
//    public static void main(String... args){
//        ToPostfixConverter p = new ToPostfixConverter();
//        ArithmeticExpression a = new ArithmeticExpression("A + B + C + D");
//        System.out.println(p.convert(a));
//    }
}
