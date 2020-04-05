package scope;

import ast.Location;

public class SemanticException extends RuntimeException {
    private Location loc;
    private String message;

    public SemanticException(String message) {
        this.message = message;
    }

    public SemanticException(Location loc, String message) {
        this.loc = loc;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Error at Line: "+ loc + " " + message;
    }
}
