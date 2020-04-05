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
        List<DefinedVariable> parameterList_print = new ArrayList<>();
        parameterList_print.add(new DefinedVariable("str", new StringType()));
        globalScope.defineFunction(new DefinedFunction("print", new VoidType(),
                parameterList_print));
        List<DefinedVariable> parameterList_println = new ArrayList<>();
        parameterList_println.add(new DefinedVariable("str", new StringType()));
        globalScope.defineFunction(new DefinedFunction("println", new VoidType(),
                parameterList_println));
        List<DefinedVariable> parameterList_printInt = new ArrayList<>();
        parameterList_printInt.add(new DefinedVariable("n", new IntType()));
        globalScope.defineFunction(new DefinedFunction("printInt", new VoidType(),
                parameterList_printInt));
        List<DefinedVariable> parameterList_printlnInt = new ArrayList<>();
        parameterList_printlnInt.add(new DefinedVariable("n", new IntType()));
        globalScope.defineFunction(new DefinedFunction("printlnInt", new VoidType(),
                parameterList_printlnInt));
        globalScope.defineFunction(new DefinedFunction("getString", new StringType(),
                new ArrayList<>()));
        globalScope.defineFunction(new DefinedFunction("getInt", new IntType(),
                new ArrayList<>()));
        List<DefinedVariable> parameterList_toString = new ArrayList<>();
        parameterList_toString.add(new DefinedVariable("i", new IntType()));
        globalScope.defineFunction(new DefinedFunction("toString", new StringType(),
                parameterList_toString));
    }

}
