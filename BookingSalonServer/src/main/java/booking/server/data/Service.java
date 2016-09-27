package booking.server.data;


public class Service {
    private String serviceName;
    private String serviceDuration;
    private String serviceDescription;
    private String serviceCost;
    private int serviceID;

    public Service(int serviceID,String serviceName, String serviceDuration, String serviceDescription, String serviceCost) {
        this.serviceName = serviceName;
        this.serviceDuration = serviceDuration;
        this.serviceDescription = serviceDescription;
        this.serviceCost = serviceCost;
        this.serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDuration() {
        return serviceDuration;
    }

    public void setServiceDuration(String serviceDuration) {
        this.serviceDuration = serviceDuration;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }
}
