package cool.test.expense.exception;

public class NotFoundException extends Exception{

    public NotFoundException() {
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
