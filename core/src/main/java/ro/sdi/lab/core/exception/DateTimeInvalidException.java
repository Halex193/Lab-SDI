package ro.sdi.lab.core.exception;

public class DateTimeInvalidException extends ProgramException
{
    public DateTimeInvalidException(String message)
    {
        super(message);
    }

    public DateTimeInvalidException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DateTimeInvalidException(Throwable cause)
    {
        super(cause);
    }
}
