package hydraTester.states;

public class Chain
{

    private State current;

    public void setState( final State state )
    {
        current = state;
    }

    public State pull()
    {
        return current.pull(this);
    }
}
