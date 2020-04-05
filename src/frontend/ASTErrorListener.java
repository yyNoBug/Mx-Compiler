package frontend;

import ast.Location;

public class ASTErrorListener {
    private boolean hasErrorOccurred = false;

    public void record (Location location, String message) {
        System.err.println(location.toString() + message);
        hasErrorOccurred = true;
    }

    public void report() throws ASTException {
        if (hasErrorOccurred) throw new ASTException();
    }
}
