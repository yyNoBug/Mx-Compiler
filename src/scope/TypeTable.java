package scope;

import type.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TypeTable {
    private Map<String, DefinedClass> typeTable = new LinkedHashMap<>();
    TopLevelScope globalScope;

    public TypeTable(TopLevelScope globalScope) {
        this.globalScope = globalScope;
        typeTable.put("int", new DefinedClass("int", new IntType(), new LocalScope(globalScope)));
        typeTable.put("void", new DefinedClass("void", new VoidType(), new LocalScope(globalScope)));
        typeTable.put("bool", new DefinedClass("bool", new BoolType(), new LocalScope(globalScope)));
        typeTable.put("null", new DefinedClass("null", new NullType(), new LocalScope(globalScope)));

        DefinedClass stringEntity = new DefinedClass("string", new IntType(), new LocalScope(globalScope));
        stringEntity.defineMemberFunction(new DefinedFunction("length", new IntType(),
                new ArrayList<>(), true));
        List<DefinedVariable> parameterList_substring = new ArrayList<>();
        parameterList_substring.add(new DefinedVariable("left", new IntType()));
        parameterList_substring.add(new DefinedVariable("right", new IntType()));
        stringEntity.defineMemberFunction(new DefinedFunction("substring", new StringType(),
                parameterList_substring, true));
        stringEntity.defineMemberFunction(new DefinedFunction("parseInt", new IntType(),
                new ArrayList<>(), true));
        List<DefinedVariable> parameterList_ord = new ArrayList<>();
        parameterList_ord.add(new DefinedVariable("pos", new IntType()));
        stringEntity.defineMemberFunction(new DefinedFunction("ord", new IntType(),
                parameterList_ord, true));
        typeTable.put("string", stringEntity);
        StringType.setEntity(stringEntity);

        DefinedClass arrayEntity = new DefinedClass("$$array_type_entity$$", new ArrayType(null, -1),
                new LocalScope(globalScope));
        arrayEntity.defineMemberFunction(new DefinedFunction("size", new IntType(), new ArrayList<>(), true));
        typeTable.put("$$array_type_entity$$", arrayEntity);
        ArrayType.setEntity(arrayEntity);
    }

    public void defineClass(String className) throws SemanticException {
        if (typeTable.containsKey(className)) throw new SemanticException("Class " + className + " has been defined.");
        DefinedClass entity = new DefinedClass(className, new ClassType(className), new LocalScope(globalScope));
        ((ClassType) entity.getType()).setEntity(entity);
        typeTable.put(className, entity);
    }

    public DefinedClass getClass(String name) {
        if (!typeTable.containsKey(name))
            throw new SemanticException("Class not defined!");
        return typeTable.get(name);
    }

    public boolean hasType(String name){
        return typeTable.containsKey(name);
    }
}
