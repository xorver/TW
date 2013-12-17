package activeobject.requests;

import activeobject.Future;
import activeobject.MethodRequest;
import activeobject.Servant;

import java.util.List;

public class UnlockConsumptionMethod extends MethodRequest {
    private List<Integer> argument;

    public UnlockConsumptionMethod(Servant servant, Future future, List<Integer> argument) {
        super(servant, future,RequestType.unlock);
        this.argument = argument;
    }

    @Override
    public boolean guard() {
        return true;
    }

    @Override
    public void call() {
        servant.unlockConsumption(argument);
        future.setResult(null);
        done=true;
    }
}
