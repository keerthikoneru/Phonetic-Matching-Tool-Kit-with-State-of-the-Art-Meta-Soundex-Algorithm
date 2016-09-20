package PhoneticMatching;

public class EncodeException extends Exception {

    private static final long serialVersionUID = 1L;

    public EncodeException() {
        super();
    }

    /**
     *
     * @param message
     *            a useful message relating to the encoder specific error.
     */
    public EncodeException(final String msg) {
        super(msg);
    }

    /**
     *
     * @param message
     *            The detail message which is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause
     */
    public EncodeException(final String msg, final Throwable c) {
        super(msg, c);
    }

    /**
     *
     * @param cause
     */
    public EncodeException(final Throwable c) {
        super(c);
    }
}
