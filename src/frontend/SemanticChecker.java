package frontend;

import ast.*;
import scope.*;
import type.*;

public class SemanticChecker implements ASTVisitor {
    private TopLevelScope globalScope;
    // private SemanticErrorListener errorListener;
    private Type curFunctionReturnType = null;

    public SemanticChecker(TopLevelScope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        node.getSection_list().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(VarDeclSingleNode node) {
        if (node.getExpr() != null) {
            node.getExpr().accept(this);
            boolean invalidInitialType;
            if (node.getType().getType() instanceof VoidType || node.getExpr().getType() instanceof VoidType)
                invalidInitialType = true;
            else if (node.getType().getType().equals(node.getExpr().getType()))
                invalidInitialType = false;
            else if (node.getExpr().getType() instanceof NullType)
                invalidInitialType = !(node.getType().getType() instanceof ClassType
                        || node.getType().getType() instanceof ArrayType);
            else
                invalidInitialType = true;
            if (invalidInitialType)
                throw new SemanticException(node.getLocation(), "Invalid initialization value");
        }
    }

    @Override
    public void visit(FunDeclNode node) {
        curFunctionReturnType = node.getType().getType();
        visit(node.getBody());
        curFunctionReturnType = null;
    }

    @Override
    public void visit(ClassConstructorNode node) {
        visit(node.getBody());
    }

    @Override
    public void visit(ClassDeclNode node) {
        node.getMemberFuns().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(StatementBlockNode node) {
        node.getStmList().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(ExprStatementNode node) {
        node.getExpression().accept(this);
        // TODO: What if the expression is a class ?
    }

    @Override
    public void visit(IfStatementNode node) {
        node.getCondition().accept(this);
        if (!(node.getCondition().getType() instanceof BoolType))
            throw new SemanticException(node.getCondition().getLocation(),"Not a bool condition.");
        node.getThenStatement().accept(this);
        if (node.getElseStatement() != null) node.getElseStatement().accept(this);
    }

    @Override
    public void visit(WhileStatementNode node) {
        node.getCondition().accept(this);
        if (!(node.getCondition().getType() instanceof BoolType))
            throw new SemanticException(node.getCondition().getLocation(), "Not a bool condition.");
        node.getBody().accept(this);
    }

    @Override
    public void visit(ForStatementNode node) {
        if (node.getInit() != null) node.getInit().accept(this);
        if (node.getCond() != null) {
            node.getCond().accept(this);
            if (!(node.getCond().getType() instanceof BoolType)) {
                throw new SemanticException(node.getCond().getLocation(), "Not a bool condition.");
            }
        }
        if (node.getStep() != null) node.getStep().accept(this);
        node.getStatement().accept(this);
    }

    @Override
    public void visit(ContinueStatementNode node) {

    }

    @Override
    public void visit(BreakStatementNode node) {

    }

    @Override
    public void visit(ReturnStatementNode node) {
        if (curFunctionReturnType == null)
            throw new SemanticException("Return statement not in function.");
        if (node.getExpr() != null) {
            if (curFunctionReturnType instanceof VoidType)
                throw new SemanticException("Invalid return expression.");
            node.getExpr().accept(this);
            if (!curFunctionReturnType.equals(node.getExpr().getType()))
                throw new SemanticException("Return type not match.");
        } else {
            if (curFunctionReturnType instanceof VoidType)
                throw new SemanticException("Invalid return expression.");
        }
    }

    @Override
    public void visit(SuffixExprNode node) {
        ExprNode expr = node.getExpr();
        expr.accept(this);
        if (!(expr.getType() instanceof IntType))
            throw new SemanticException(expr.getLocation(), "Not an int type.");
        node.setType(new IntType());
        node.setLeftValue(true);
    }

    @Override
    public void visit(FunCallExprNode node) {
        ExprNode function = node.getFuncExpr();
        function.accept(this);
        if (!(function.getType() instanceof FuncType))
            throw new SemanticException(function.getLocation(), "Not a function.");
        node.getParameterList().forEach(x -> x.accept(this));
        if (node.getParameterList().size() == function.getFuncEntity().getParameters().size()) {

        }
    }

    @Override
    public void visit(ArrayExprNode node) {
        ExprNode array = node.getArray();
        ExprNode index = node.getIndex();
        array.accept(this);
        index.accept(this);
        if (!(array.getType() instanceof ArrayType))
            throw new SemanticException(array.getLocation(), "Not an array type.");
        if (!(index.getType() instanceof IntType))
            throw new SemanticException(array.getLocation(), "Not an int type.");
        node.setLeftValue(true);
        Type baseType = ((ArrayType) array.getType()).getBaseType();
        int numDims = ((ArrayType) array.getType()).getNumDims();
        if (numDims == 1) node.setType(baseType);
        else node.setType(new ArrayType(baseType, numDims - 1));
    }

    @Override
    public void visit(MemberExprNode node) {
        node.getExpr().accept(this);
        if (node.getExpr().getType() instanceof ClassType || node.getExpr().getType() instanceof StringType) {
            DefinedClass classEntity = node.getExpr().getType().getEntity();
            Entity memberEntity = classEntity.resolveMember(node.getMember());
            if (memberEntity instanceof DefinedVariable) {
                node.setLeftValue(true);
                node.setType(memberEntity.getType());
            } else {
                node.setLeftValue(false);
                node.setType(memberEntity.getType());
                node.setFuncEntity(((DefinedFunction) memberEntity));
            }
        } else if (node.getExpr().getType() instanceof ArrayType) {

        }

    }

    @Override
    public void visit(PrefixExprNode node) {
        ExprNode expr = node.getExpr();
        expr.accept(this);
        switch(node.getOp()) {
            case NEG:
            case POS:
            case BITWISE_NOT:
                if (!expr.isLeftValue())
                    throw new SemanticException(expr.getLocation(), "Not a left value.");
                if (!(expr.getType() instanceof IntType))
                    throw new SemanticException(expr.getLocation(), "Not an int type.");
                node.setType(new IntType());
                node.setLeftValue(false);
                break;
            case PREFIX_DEC:
            case PREFIX_INC:
                if (!(expr.getType() instanceof IntType))
                    throw new SemanticException(expr.getLocation(), "Not an int type.");
                node.setType(new IntType());
                node.setLeftValue(true);
                break;
            case LOGIC_NOT:
                if (!(expr.getType() instanceof BoolType))
                    throw new SemanticException(expr.getLocation(), "Not a bool type.");
                node.setType(new BoolType());
                node.setLeftValue(false);
                break;
        }
    }

    @Override
    public void visit(NewExprNode node) {
        node.getDims().forEach(x -> {
            x.accept(this);
            if (!(x.getType() instanceof IntType))
                throw new SemanticException(x.getLocation(), "Array subscript not an integer type.");
        });
        node.getNewType().accept(this);
        Type type = node.getNewType().getType();
        node.setLeftValue(false);
        if (node.getNumDims() == 0) {
            node.setType(type);
            // node.setFunctionSymbol(((ClassSymbol) type).getConstructor());
            // What does HBH mean ?
        } else {
            node.setType(new ArrayType(type, node.getNumDims()));
        }
        // TODO: What about new statement with constructor function ?
    }

    @Override
    public void visit(BinaryExprNode node) {
        ExprNode lhs = node.getLhs();
        ExprNode rhs = node.getRhs();
        lhs.accept(this);
        rhs.accept(this);
        switch (node.getOp()) {
            case MUL:
            case DIV:
            case MOD:
            case SUB:
            case SLA:
            case SRA:
            case BITWISE_AND:
            case BITWISE_XOR:
            case BITWISE_OR:
                if (!(lhs.getType() instanceof IntType))
                    throw new SemanticException(node.getLhs().getLocation(),"Not a integer type.");
                if (!(rhs.getType() instanceof IntType))
                    throw new SemanticException(node.getRhs().getLocation(),"Not a integer type.");
                node.setLeftValue(false);
                node.setType(new IntType());
                break;
            case ADD:
            case GTH:
            case GEQ:
            case LTH:
            case LEQ:
                if (lhs.getType() instanceof StringType && rhs.getType() instanceof StringType) {
                    node.setType(new StringType());
                    node.setLeftValue(false);
                } else if (lhs.getType() instanceof IntType && rhs.getType() instanceof IntType) {
                    node.setType(new IntType());
                    node.setLeftValue(false);
                } else throw new SemanticException(node.getLocation(), "Invalid expression type.");
                break;
            case NEQ:
            case EQ:
                if (lhs.getType().equals(rhs.getType())) {
                    node.setType(new BoolType());
                    node.setLeftValue(false);
                } else throw new SemanticException(node.getLocation(), "Not the same type.");
                break;
            case LOGIC_AND:
            case LOGIC_OR:
                if (!(lhs.getType() instanceof BoolType))
                    throw new SemanticException(node.getLhs().getLocation(),"Not a bool type.");
                if (!(rhs.getType() instanceof BoolType))
                    throw new SemanticException(node.getRhs().getLocation(),"Not a bool type.");
                node.setType(new BoolType());
                node.setLeftValue(false);
                break;
        }
    }

    @Override
    public void visit(AssignExprNode node) {
        ExprNode lhs = node.getLhs();
        if (lhs.isLeftValue())
            throw new SemanticException(lhs.getLocation(), "Invalid assignment to a left value.");
        lhs.accept(this);
        ExprNode rhs = node.getRhs();
        rhs.accept(this);
        if (!lhs.getType().equals(rhs.getType()))
            throw new SemanticException(node.getLocation(), "Not the same type.");
    }

    @Override
    public void visit(IdExprNode node) {
        Entity entity = node.getEntity();
        if (entity instanceof DefinedVariable) {
            node.setLeftValue(true);
            node.setType(entity.getType());
        } else if (entity instanceof DefinedFunction) {
            node.setLeftValue(false);
            node.setType(new FuncType());
            node.setEntity(entity);
        } else if (entity instanceof DefinedClass) {
            node.setLeftValue(false);
            node.setType(globalScope.getClass(entity.getName()).getType());
            // TODO: This may cause huge problem, since I cannot tell whether it is a class or a variable.
        }

    }

    @Override
    public void visit(ThisExprNode node) {
        node.setLeftValue(false);
        node.setType(node.getClassEntity().getType());
    }

    @Override
    public void visit(NumConstNode node) {
        node.setLeftValue(false);
        node.setType(new IntType());
    }

    @Override
    public void visit(StringConstNode node) {
        node.setLeftValue(false);
        node.setType(new StringType());
    }

    @Override
    public void visit(BoolConstNode node) {
        node.setLeftValue(false);
        node.setType(new BoolType());
    }

    @Override
    public void visit(NullConstNode node) {
        node.setLeftValue(false);
        node.setType(new NullType());
    }

    @Override
    public void visit(TypeNode node) {

    }

    @Override
    public void visit(ArrayTypeNode node) {

    }
}
