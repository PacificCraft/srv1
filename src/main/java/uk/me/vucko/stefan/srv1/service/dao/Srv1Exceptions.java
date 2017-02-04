package uk.me.vucko.stefan.srv1.service.dao;

public class Srv1Exceptions extends Exception {

    private static final long serialVersionUID = 1362108306141821576L;

    public Srv1Exceptions(String message)
    {
        super(message);
    }

    public Srv1Exceptions(Throwable cause)
    {
        super(cause);
    }

    public Srv1Exceptions(String message, Throwable cause)
    {
        super(message, cause);
    }

    public static class AuthenticationException extends Srv1Exceptions
    {
        private static final long serialVersionUID = 1362108306141821577L;
        
        public AuthenticationException(String message)
        {
            super(message);
        }

        public AuthenticationException(Throwable cause)
        {
            super(cause);
        }

        public AuthenticationException(String message, Throwable cause)
        {
            super(message, cause);
        }
    }

}