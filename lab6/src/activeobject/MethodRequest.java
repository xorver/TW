package activeobject;

public abstract class MethodRequest {
    protected Servant servant;
    protected Future future;
    protected RequestType requestType;
    protected boolean done=false;

    protected MethodRequest(Servant servant, Future future, RequestType requestType) {
        this.servant = servant;
        this.future = future;
        this.requestType = requestType;
    }

    public abstract boolean guard();
    public abstract void call();
    public boolean isDone(){
        return done;
    }
    public RequestType getRequestType() {
        return requestType;
    }
    public enum RequestType{
        unlock,lockProduction,lockConsumption;
    }
}
