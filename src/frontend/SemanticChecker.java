package frontend;

import ast.*;
import scope.*;
import type.*;

import java.util.Iterator;

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
                throw new SemanticException(node.getLocation(), "Invalid initialization value type.");
        } else {
            if (node.getType().getType() instanceof VoidType)
                throw new SemanticException(node.getLocation(), "Variables cannot be void type.");
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
        curFunctionReturnType = node.getType().getType();
        if (!(curFunctionReturnType instanceof VoidType))
            throw new SemanticException(node.getLocation(), "error!!");
        visit(node.getBody());
        curFunctionReturnType = null;
    }

    @Override
    public void visit(ClassDeclNode node) {
        if (node.getConstructor() != null)
            node.getConstructor().accept(this);
        node.getMemberFuns().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(StatementBlockNode node) {
        node.getStmList().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(ExprStatementNode node) {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(VarDeclStatementNode node) {
        node.getVariable().accept(this);
    }

    @Override
    public void visit(IfStatementNode node) {
        node.getCondition().accept(this);
        if (!(node.getCondition().getType() instanceof BoolType))
            throw new SemanticException(node.getCondition().getLocation(),"Not a bool condition.");
        if (node.getThenStatement() != null) node.getThenStatement().accept(this);
        if (node.getElseStatement() != null) node.getElseStatement().accept(this);
    }

    @Override
    public void visit(WhileStatementNode node) {
        node.getCondition().accept(this);
        if (!(node.getCondition().getType() instanceof BoolType))
            throw new SemanticException(node.getCondition().getLocation(), "Not a bool condition.");
        if (node.getBody() != null) node.getBody().accept(this);
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
        if (node.getStatement() != null) node.getStatement().accept(this);
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
            if (!curFunctionReturnType.compacts(node.getExpr().getType()))
                throw new SemanticException(node.getLocation(), "Return type not match.");
        } else {
            if (!(curFunctionReturnType instanceof VoidType))
                throw new SemanticException(node.getLocation(), "Invalid return expression.");
        }
    }

    @Override
    public void visit(SuffixExprNode node) {
        ExprNode expr = node.getExpr();
        expr.accept(this);
        if (!expr.isLeftValue())
            throw new SemanticException(expr.getLocation(), "Not a left value.");
        if (!(expr.getType() instanceof IntType))
            throw new SemanticException(expr.getLocation(), "Not an int type.");
        node.setType(new IntType());
        node.setLeftValue(false);
    }

    @Override
    public void visit(FunCallExprNode node) {
        ExprNode functionNameNode = node.getFuncExpr();
        functionNameNode.accept(this);
        //if (!(functionNameNode.getType() instanceof FuncType))
        DefinedFunction function = functionNameNode.getFuncEntity();
        if (function == null)
            throw new SemanticException(functionNameNode.getLocation(), "Not a function.");
        node.getParameterList().forEach(x -> x.accept(this));
        if (node.getParameterList().size() == functionNameNode.getFuncEntity().getParameters().size()) {
            Iterator<ExprNode> iterator = node.getParameterList().iterator();
            for (DefinedVariable parameter : function.getParameters()) {
                ExprNode expr = iterator.next();
                if (!parameter.getType().compacts(expr.getType()))
                    throw new SemanticException(node.getLocation(), "Parameter type not match.");
            }
            node.setLeftValue(false);
            node.setType(function.getReturnType());
        } else
            throw new SemanticException(node.getLocation(), "Parameter list length not match.");
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
        else node.setType(new ArrayType(baseType, (numDims - 1)));
    }

    @Override
    public void visit(MemberExprNode node) {
        node.getExpr().accept(this);
        if (node.getExpr().getType() instanceof ClassType || node.getExpr().getType() instanceof StringType
            || node.getExpr().getType() instanceof ArrayType) {
            if (node.getExpr().getType().getEntity() == null)
                node.getExpr().getType().resolve(globalScope);
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
        } else {
            throw new SemanticException(node.getLocation(), "Member access error.");
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
                if (!(expr.getType() instanceof IntType))
                    throw new SemanticException(expr.getLocation(), "Not an int type.");
                node.setType(new IntType());
                node.setLeftValue(false);
                break;
            case PREFIX_DEC:
            case PREFIX_INC:
                if (!expr.isLeftValue())
                    throw new SemanticException(expr.getLocation(), "Not a left value.");
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
            if (type instanceof ClassType || type instanceof StringType) {
                node.setType(type);
                if (type.getEntity().getConstructor() != null)
                    node.setFuncEntity(type.getEntity().getConstructor());
            } else {
                if (type instanceof VoidType)
                    throw new SemanticException(node.getLocation(), "New item cannot be of Void Type.");
                else node.setType(type);
            }
        } else {
            node.setType(new ArrayType(type, node.getNumDims()));
        }
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
                if (lhs.getType() instanceof StringType && rhs.getType() instanceof StringType) {
                    node.setType(new StringType());
                    node.setLeftValue(false);
                } else if (lhs.getType() instanceof IntType && rhs.getType() instanceof IntType) {
                    node.setType(new IntType());
                    node.setLeftValue(false);
                } else throw new SemanticException(node.getLocation(), "Invalid expression type.");
                break;
            case GTH:
            case GEQ:
            case LTH:
            case LEQ:
                if (lhs.getType() instanceof StringType && rhs.getType() instanceof StringType) {
                    node.setType(new BoolType());
                    node.setLeftValue(false);
                } else if (lhs.getType() instanceof IntType && rhs.getType() instanceof IntType) {
                    node.setType(new BoolType());
                    node.setLeftValue(false);
                } else throw new SemanticException(node.getLocation(), "Invalid expression type.");
                break;
            case NEQ:
            case EQ:
                if (lhs.getType().compacts(rhs.getType())) {
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
        lhs.accept(this);
        if (!lhs.isLeftValue())
            throw new SemanticException(lhs.getLocation(), "Invalid assignment to a left value.");
        ExprNode rhs = node.getRhs();
        rhs.accept(this);
        if (!lhs.getType().compacts(rhs.getType()))
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
            node.setFuncEntity((DefinedFunction) entity);
        } else if (entity instanceof DefinedClass) {
            throw new SemanticException ("error!");
            // This may cause huge problem, since I cannot tell whether it is a class or a variable.
            // But it seems that our program never reaches here.
        } else
            throw new SemanticException(node.getLocation(), "ID not resolved.");

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
