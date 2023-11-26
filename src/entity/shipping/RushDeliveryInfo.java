package entity.shipping;

public class RushDeliveryInfo {

	private String deliveryInstruction;

	private String shipmentDetail;

	private String deliveryTime;

	//constructor operation
	public RushDeliveryInfo(String deliveryInstruction, String shipmentDetail, String deliveryTime) {
		super();
		this.deliveryInstruction = deliveryInstruction;
		this.shipmentDetail = shipmentDetail;
		this.deliveryTime = deliveryTime;
	}

	public RushDeliveryInfo() {

	}


	/** 
	 * @return String
	 */
	//getter setter method
	public String getDeliveryInstruction() {
		return this.deliveryInstruction;
	}

    public void validateDeliveryInfo(){
        // TODO: implement later on
    }

    
    /** 
     * @return Shipment
     */
    public RushDeliveryInfo createNewShipment(){
        // TODO: implement later on
        return new RushDeliveryInfo();
    }
}
