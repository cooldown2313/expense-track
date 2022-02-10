package cool.test.expense.exception;

public class AlreadyExistsException extends Exception{

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String msg) {
        super(msg);
    }

}
