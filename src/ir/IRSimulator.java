package ir;

import java.util.ArrayList;

public class IRSimulator {
    private ArrayList<Integer> locals;
    private ArrayList<Integer> globals;
/*
    private Integer VarOfRegister(Register r) {
        int ind = r.getNum();
        if (r instanceof Local) return locals.get(ind);
        else if (r instanceof Global) return globals.get(ind);
    }

    public void visit(DeclaredFunction function, List<Register> args) {
        ArrayList<Register> argList = function.getArgs();
        for (int i = 0; i < argList.size(); ++i) {
            VarOfRegister(argList.get(i)) = VarOfRegister(args.get(i));
        }
        for (Block block : function.getBlockList()) {
            for (Statement statement : block.getStmtList()) {
                statement.run(this);
            }
        }
    }*/
}
