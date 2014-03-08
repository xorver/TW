package activeobject.requests;

import activeobject.Future;
import activeobject.MethodRequest;
import activeobject.Servant;

import java.util.List;

public class ProductionMethod extends MethodRequest {
    private List<Integer> argument;

    public ProductionMethod(Servant servant, Future future, List<Integer> argument) {
        super(servant, future,RequestType.lockProduction);
        this.argument = argument;
    }

    @Override
    public boolean guard() {
        return  argument.size() <= servant.getNumberOfFreeElements();
    }

    @Override
    public void call() {
        servant.produce(argument);
        future.setResult(null);
        done=true;
    }
}
