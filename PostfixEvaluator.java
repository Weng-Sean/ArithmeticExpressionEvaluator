import java.util.Stack;
public class PostfixEvaluator implements Evaluator{

    public double evaluate(String expressionString) {
        ToPostfixConverter p = new ToPostfixConverter();
        Stack<String> stack = new Stack<>();
        int start = 0;
        while (start < expressionString.length()){


            String token = p.nextToken(expressionString, start);
            if (p.isOperand(token))
                stack.push(token);
            else if (Operator.isOperator(token)){
                double operand2 = Double.parseDouble(stack.pop());
                double operand1 = Double.parseDouble(stack.pop());
                Operator operator = Operator.of(token);
                double result = 0;

                switch(operator){
                    case ADDITION:
                        result = operand1 + operand2;
                        break;
                    case SUBTRACTION:
                        result = operand1 - operand2;
                        break;
                    case MULTIPLICATION:
                        result = operand1 * operand2;
                        break;
                    case DIVISION:
                        result = operand1 / operand2;
                }
                stack.push(""+result);

            }

            start += token.length();
            while (start < expressionString.length() && expressionString.charAt(start) == ' '){
                start++;
            }
        }
        return Math.round(Double.parseDouble(stack.pop())*100.0)/100.0;
    }
    public static void main(String... args){
        PostfixEvaluator p1 = new PostfixEvaluator();
        System.out.println(p1.evaluate("3 4 * 2 5 * +"));

        ToPostfixConverter p2 = new ToPostfixConverter();
        ArithmeticExpression a = new ArithmeticExpression("5 * ((2+3) * 4)");
        System.out.println(p1.evaluate(p2.convert(a)));


    }

}
