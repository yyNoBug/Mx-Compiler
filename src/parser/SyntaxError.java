package parser;

import ast.Location;

public class SyntaxError extends RuntimeException {
    private Location loc;
    private String message;

    public SyntaxError(String message) {
        this.message = message;
    }

    public SyntaxError(Location loc, String message) {
        this.loc = loc;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Error at Line: "+ loc + " " + message;
    }
}