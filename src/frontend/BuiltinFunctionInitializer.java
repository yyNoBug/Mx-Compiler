package frontend;

import scope.DefinedFunction;
import scope.DefinedVariable;
import scope.TopLevelScope;
import type.IntType;
import type.StringType;
import type.VoidType;

import java.util.ArrayList;
import java.util.List;

public class BuiltinFunctionInitializer{
    private TopLevelScope globalScope;

    public BuiltinFunctionInitializer(TopLevelScope globalScope) {
        this.globalScope = globalScope;
    }

    public void initialize() {
        List<DefinedVariable> parameterList_print = new ArrayList<DefinedVariable>();
        parameterList_print.add(new DefinedVariable("str", new StringType()));
        globalScope.defineFunction(new DefinedFunction("print", new VoidType(),
                parameterList_print));
        List<DefinedVariable> parameterList_println = new ArrayList<DefinedVariable>();
        parameterList_println.add(new DefinedVariable("str", new StringType()));
        globalScope.defineFunction(new DefinedFunction("println", new VoidType(),
                parameterList_println));
        List<DefinedVariable> parameterList_printInt = new ArrayList<DefinedVariable>();
        parameterList_printInt.add(new DefinedVariable("n", new IntType()));
        globalScope.defineFunction(new DefinedFunction("printInt", new VoidType(),
                parameterList_printInt));
        List<DefinedVariable> parameterList_printIntLn = new ArrayList<DefinedVariable>();
        parameterList_printIntLn.add(new DefinedVariable("n", new IntType()));
        globalScope.defineFunction(new DefinedFunction("printIntLn", new VoidType(),
                parameterList_printIntLn));
        globalScope.defineFunction(new DefinedFunction("getString", new StringType(),
                new ArrayList<DefinedVariable>()));
        globalScope.defineFunction(new DefinedFunction("getInt", new IntType(),
                new ArrayList<DefinedVariable>()));
        List<DefinedVariable> parameterList_toString = new ArrayList<DefinedVariable>();
        parameterList_printIntLn.add(new DefinedVariable("i", new IntType()));
        globalScope.defineFunction(new DefinedFunction("toString", new StringType(),
                parameterList_printIntLn));

        // What does HBH mean by initHacker ?
    }

}
