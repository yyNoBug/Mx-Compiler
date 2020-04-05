package frontend;

import ast.Location;

public class CompilerException extends RuntimeException {
    private Location location;

    public CompilerException(Location location, String message) {
        super(message);
        this.location = location;
    }

    @Override
    public String getMessage() {
        return "Error at Line: "+ location + " " + super.getMessage();
    }
}
