import java.util.*;

public class Flowduino {
    public static void main(String[] args) {
        List<Variable> variables = new ArrayList<Variable>() {{add(new Variable("i", "int", "0")); add(new Variable("g", "string", "hej")); add(new Variable("b", "float"));}};

        ForLoop forLoop = new ForLoop(variables.get(2), new Constant(4), new Constant(80), new Constant(.4));
        StatementComponent i2 = new StatementComponent("i *= 2");
        StatementComponent i3 = new StatementComponent("i *= 3");

        Node forLoopNode = new Node(forLoop);
        Node i2Node = new Node(i2);
        Node i3Node = new Node(i3);

        Case ifA = new Case(new Case(variables.get(0), new Constant(3), "=="), new Case(new Constant(4), variables.get(0), "=="), "||");
        Case ifB = new Case(variables.get(0), new Constant(4), "==");

        List<Node> nodes = new ArrayList<Node>() {{add(forLoopNode); add(i2Node); add(i3Node);}};
        List<ICase> cases = new ArrayList<ICase>() {{add(ifA); add(ifB);}};

        IfComponent ifcase = new IfComponent(nodes, cases);

        forLoop.setHeadOfContent(new Node(new StatementComponent("b *= 2")));

        StatementComponent staementComponetn = new StatementComponent("i = sqrt(b)\ni = pow(i, 3)");
        Node staementNode = new Node(staementComponetn);

        forLoopNode.setNext(staementNode);

        WhileLoop whileLoop = new WhileLoop(new Case(variables.get(0), new Constant(0), ">"));
        Node whileLoopNode = new Node(whileLoop);

        staementNode.setNext(whileLoopNode);

        DelayComponent delay = new DelayComponent();
        delay.setDelayMilliseconds(3);
        Node delayNode = new Node(delay);

        StatementComponent i1 = new StatementComponent("i -= 1");
        Node i1Node = new Node(i1, delayNode);

        whileLoop.setHeadOfContent(i1Node); 

        Node head = new Node(ifcase);




        String code = "";
        // init
        for (Variable current : variables) {
            code += current.getInitCode() + "\n";
        }
        code += "\n\n\n\n";
        code += head.getProgramCode(0);
        System.out.println(code);
        // rest
    }

    /*
    int i = 0;
    string g = "hej";
    float b;

    if (((i) == (3)) || (4) == (i)) {
      for (b = 4; b < 80; b += .4) {
        b *= 2;
      }
      i = sqrt(b);
      i = pow(i, 3);
      while ((i) > (0)) {
        i -= 1;
        delay(3);
      }
    } else if ((i) == (4)) {
      i *= 2;  
    } else {
      i *= 3;
    }
    */
}