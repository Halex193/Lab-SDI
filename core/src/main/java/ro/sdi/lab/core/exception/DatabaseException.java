package ro.sdi.lab.core.exception;

public class DatabaseException extends ProgramException
{
    public DatabaseException(String message)
    {
        super(message);
    }

    public DatabaseException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DatabaseException(Throwable cause)
    {
        super(cause);
    }
}
