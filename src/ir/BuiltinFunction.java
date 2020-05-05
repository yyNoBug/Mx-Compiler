package ir;

import scope.DefinedFunction;
import scope.TopLevelScope;

public class BuiltinFunction extends Function {
    public final static BuiltinFunction mallocArray = new BuiltinFunction();
    public final static BuiltinFunction mallocObject = new BuiltinFunction();
    public final static BuiltinFunction getInt = new BuiltinFunction();
    public final static BuiltinFunction getString = new BuiltinFunction();
    public final static BuiltinFunction print = new BuiltinFunction();
    public final static BuiltinFunction println = new BuiltinFunction();
    public final static BuiltinFunction printInt = new BuiltinFunction();
    public final static BuiltinFunction printlnInt = new BuiltinFunction();
    public final static BuiltinFunction toString = new BuiltinFunction();
    public final static BuiltinFunction stringParseInt = new BuiltinFunction();
    public final static BuiltinFunction stringLength = new BuiltinFunction();
    public final static BuiltinFunction stringSubstring = new BuiltinFunction();
    public final static BuiltinFunction stringOrder = new BuiltinFunction();
    public final static BuiltinFunction arraySize = new BuiltinFunction();
    public final static BuiltinFunction stringConcatenate = new BuiltinFunction();
    public final static BuiltinFunction stringEqual = new BuiltinFunction();
    public final static BuiltinFunction stringNeq = new BuiltinFunction();
    public final static BuiltinFunction stringLess = new BuiltinFunction();
    public final static BuiltinFunction stringLeq = new BuiltinFunction();
    public final static BuiltinFunction stringGreater = new BuiltinFunction();
    public final static BuiltinFunction stringGeq = new BuiltinFunction();

    public static void setFunctionMap(FunctionMap functionMap, TopLevelScope globalScope) {
        functionMap.put(((DefinedFunction) globalScope.get("print")), print);
        functionMap.put(((DefinedFunction) globalScope.get("println")), println);
        functionMap.put(((DefinedFunction) globalScope.get("printInt")), printInt);
        functionMap.put(((DefinedFunction) globalScope.get("printlnInt")), printlnInt);
        functionMap.put(((DefinedFunction) globalScope.get("getString")), getString);
        functionMap.put(((DefinedFunction) globalScope.get("getInt")), getInt);
        functionMap.put(((DefinedFunction) globalScope.get("toString")), toString);
        var str = globalScope.getClass("String");
        functionMap.put(((DefinedFunction) str.resolveMember("length")), stringLength);
        functionMap.put(((DefinedFunction) str.resolveMember("substring")), stringSubstring);
        functionMap.put(((DefinedFunction) str.resolveMember("parseInt")), stringParseInt);
        functionMap.put(((DefinedFunction) str.resolveMember("ord")), stringOrder);
        var arr = globalScope.getClass("$$array_type_entity$$");
        functionMap.put(((DefinedFunction) arr.resolveMember("size")), arraySize);
    }
}
