package bench.artshop.order.exception;

public class NotEnoughProductQuantityException extends RuntimeException{
    public NotEnoughProductQuantityException(String message) {
        super(message);
    }
}
