package tigercompiler;

import antlr.TigerBaseListener;
import antlr.TigerLexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import tiger.TokenTuple;

import java.util.LinkedList;

public class MyTigerListener extends TigerBaseListener {
    // data structure to store tuple of token type and token text

    // String builder to store the tree
    private static StringBuilder tree;
    private final LinkedList<TokenTuple> tokenSet;

    public MyTigerListener() {
        tree = new StringBuilder("digraph ParseTree {\n");
        tokenSet = new LinkedList<>();
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if (ctx.getParent() == null) {
            // root node
            String name = getNodeName(ctx);
            tree.append(ctx.hashCode()).append(" [label=\"").append(name).append("\"];\n");
        } else {
            // non-root node
            String parentName = getNodeName(ctx.getParent());
            String name = getNodeName(ctx);
            tree.append(ctx.hashCode()).append(" [label=\"").append(name).append("\"];\n");
            tree.append(ctx.getParent().hashCode()).append(" -> ").append(ctx.hashCode()).append(";\n");
        }
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        // get token name
        String name = TigerLexer.VOCABULARY.getSymbolicName(node.getSymbol().getType());
        String text = node.getText();
        if (!name.equals("EOF")) {
            TokenTuple token = new TokenTuple(name, text);
            tokenSet.add(token);
        }

        // add token to tree
        tree.append(node.hashCode()).append(" [label=\"").append(name).append(" : ").append(text).append("\"];\n");
        tree.append(node.getParent().hashCode()).append(" -> ").append(node.hashCode()).append(";\n");
    }

    private String getNodeName(ParseTree node) {
        return node.getClass().getSimpleName().replace("Context", "");
    }

    // return tree as string
    public static String getTree() {
        return tree.toString() + "}";
    }

    // return token set
    public LinkedList<TokenTuple> getTokenSet() {
        return tokenSet;
    }

}
