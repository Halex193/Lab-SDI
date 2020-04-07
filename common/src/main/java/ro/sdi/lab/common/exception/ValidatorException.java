package ro.sdi.lab.common.exception;

/**
 * @author radu.
 */

public class ValidatorException extends ProgramException
{
    public ValidatorException(String message)
    {
        super(message);
    }

    public ValidatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ValidatorException(Throwable cause)
    {
        super(cause);
    }
}
