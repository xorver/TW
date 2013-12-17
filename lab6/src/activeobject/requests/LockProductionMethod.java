package activeobject.requests;

import activeobject.Future;
import activeobject.MethodRequest;
import activeobject.Servant;

public class LockProductionMethod extends MethodRequest {
    private int argument;

    public LockProductionMethod(Servant servant, Future future, int argument) {
        super(servant, future,RequestType.lockProduction);
        this.argument = argument;
    }

    @Override
    public boolean guard() {
        return  argument <= servant.getNumberOfFreeElements();
    }

    @Override
    public void call() {
        future.setResult( servant.lockProduction(argument) );
        done=true;
    }
}
