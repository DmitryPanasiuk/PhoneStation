package by.gstu.phonestation.service;

/**
 * Service types enumeration
 *
 * @author Dmitry Panasiuk
 * @version 1.0
 */
public enum ServiceType {
    CLIP("clip", 5), VIP_NUMBER("vip_number", 10),
    VIRTUAL_NUMBER("virtual_number", 7);

    private String name;
    private int cost;

    ServiceType(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    //Service type definition by string value
    public static ServiceType defineType(String type) {
        switch (type) {
            case "clip":
                return ServiceType.CLIP;
            /* falls through */
            case "vip_number":
                return ServiceType.VIP_NUMBER;
            /* falls through */
            case "virtual_number":
                return ServiceType.VIRTUAL_NUMBER;
            /* falls through */
            default:
                throw new EnumConstantNotPresentException(ServiceType.class, type);
        }
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
