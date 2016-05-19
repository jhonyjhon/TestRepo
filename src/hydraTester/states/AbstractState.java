package hydraTester.states;

public abstract class AbstractState implements State
{
    // Base impl of State pulling
    // Update ChainWraper wit new State obj
    // createNextState must be implemented
    // in any concrete State's impl.
    public State pull( final Chain chain )
    {
        chain.setState(createNextState());
        return this;
    }

    // Implement this for any new State
    // It will be a next link in chain
    protected State createNextState()
    {
        throw new UnsupportedOperationException("Not Implemented AbstractState : createNextState()");
    }
}
