package model;

/**
 * InHouse contains the Machine ID and extends the Part class
 */
public class InHouse extends Part{

    /**
     * the Machine ID of the InHouse Part
     */
    private int machineId;

    /**
     * the Constructor used to create a new InHouse Part
     * @param id the ID of the InHouse Part
     * @param name the name of the InHouse Part
     * @param price the price of the InHouse Part
     * @param stock the current inventory amount of the InHouse Part
     * @param min the minimum amount of the InHouse Part
     * @param max the maximum amount of the InHouse Part
     * @param machineId the machine ID of the InHouse Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);

        this.machineId = machineId;
    }

    /**
     * Get the machine ID of the InHouse Part
     * @return the InHouse machine ID
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Set the machine ID of the InHouse Part
     * @param machineId the InHouse machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
