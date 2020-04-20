package ro.sdi.lab.core.exception;

public class ProgramException extends RuntimeException
{
    public ProgramException(String message)
    {
        super(message);
    }

    public ProgramException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ProgramException(Throwable cause)
    {
        super(cause);
    }
}
