package ir;

import scope.DefinedFunction;
import scope.TopLevelScope;

public class BuiltinFunction extends Function {
    public BuiltinFunction(String name) {
        super(name);
    }

    public final static BuiltinFunction mallocArray = new BuiltinFunction("__mallocArray__");
    public final static BuiltinFunction mallocObject = new BuiltinFunction("__mallocObject__");
    public final static BuiltinFunction getInt = new BuiltinFunction("__getInt__");
    public final static BuiltinFunction getString = new BuiltinFunction("__getString__");
    public final static BuiltinFunction print = new BuiltinFunction("__print__");
    public final static BuiltinFunction println = new BuiltinFunction("__println__");
    public final static BuiltinFunction printInt = new BuiltinFunction("__printInt__");
    public final static BuiltinFunction printlnInt = new BuiltinFunction("__printlnInt__");
    public final static BuiltinFunction toString = new BuiltinFunction("__toString__");
    public final static BuiltinFunction stringParseInt = new BuiltinFunction("__stringParseInt__");
    public final static BuiltinFunction stringLength = new BuiltinFunction("__stringLength__");
    public final static BuiltinFunction stringSubstring = new BuiltinFunction("__stringSubstring__");
    public final static BuiltinFunction stringOrder = new BuiltinFunction("__stringOrder__");
    public final static BuiltinFunction arraySize = new BuiltinFunction("__arraySize__");
    public final static BuiltinFunction stringConcatenate = new BuiltinFunction("__stringConcatenate__");
    public final static BuiltinFunction stringEqual = new BuiltinFunction("__stringEqual__");
    public final static BuiltinFunction stringNeq = new BuiltinFunction("__stringNeq__");
    public final static BuiltinFunction stringLess = new BuiltinFunction("__stringLess__");
    public final static BuiltinFunction stringLeq = new BuiltinFunction("__stringLeq__");
    public final static BuiltinFunction stringGreater = new BuiltinFunction("__stringGreater__");
    public final static BuiltinFunction stringGeq = new BuiltinFunction("__stringGeq__");

    public static void setFunctionMap(FunctionMap functionMap, TopLevelScope globalScope) {
        functionMap.put(((DefinedFunction) globalScope.get("print")), print);
        functionMap.put(((DefinedFunction) globalScope.get("println")), println);
        functionMap.put(((DefinedFunction) globalScope.get("printInt")), printInt);
        functionMap.put(((DefinedFunction) globalScope.get("printlnInt")), printlnInt);
        functionMap.put(((DefinedFunction) globalScope.get("getString")), getString);
        functionMap.put(((DefinedFunction) globalScope.get("getInt")), getInt);
        functionMap.put(((DefinedFunction) globalScope.get("toString")), toString);
        var str = globalScope.getClass("string");
        functionMap.put(((DefinedFunction) str.resolveMember("length")), stringLength);
        functionMap.put(((DefinedFunction) str.resolveMember("substring")), stringSubstring);
        functionMap.put(((DefinedFunction) str.resolveMember("parseInt")), stringParseInt);
        functionMap.put(((DefinedFunction) str.resolveMember("ord")), stringOrder);
        var arr = globalScope.getClass("$$array_type_entity$$");
        functionMap.put(((DefinedFunction) arr.resolveMember("size")), arraySize);
    }
}
