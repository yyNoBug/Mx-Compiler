package ast;

public interface ASTVisitor {
    void visit(ProgramNode node);
    // void visit(VarDeclListNode node); // Not on tree.
    void visit(VarDeclSingleNode node);
    void visit(FunDeclNode node);
    void visit(ClassDeclNode node);
    void visit(ClassConstructorNode node);
    void visit(StatementBlockNode node);
    void visit(ExprStatementNode node);
    void visit(IfStatementNode node);
    void visit(WhileStatementNode node);
    void visit(ForStatementNode node);
    void visit(ContinueStatementNode node);
    void visit(BreakStatementNode node);
    void visit(ReturnStatementNode node);
    void visit(SuffixExprNode node);
    void visit(FunCallExprNode node);
    void visit(ArrayExprNode node);
    void visit(MemberExprNode node);
    void visit(PrefixExprNode node);
    void visit(NewExprNode node);
    void visit(BinaryExprNode node);
    void visit(AssignExprNode node);
    void visit(IdExprNode node);
    void visit(ThisExprNode node);
    void visit(NumConstNode node);
    void visit(StringConstNode node);
    void visit(BoolConstNode node);
    void visit(NullConstNode node);
    void visit(TypeNode node);
    void visit(ArrayTypeNode node);
    // There may be something missing.
}
