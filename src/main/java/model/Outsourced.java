package model;

/**
 * Outsourced contains the Company Name and extends the Part class
 */
public class Outsourced extends Part{

    /**
     * the company name of the Outsourced Part
     */
    private String companyName;

    /**
     * the Constructor used to create a new Outsourced Part
     * @param id the ID of the Outsourced Part
     * @param name the name of the Outsourced Part
     * @param price the price of the Outsourced Part
     * @param stock the current inventory of the Outsourced Part
     * @param min the minimum inventory of the Outsourced Part
     * @param max the maximum inventory of the Outsourced Part
     * @param companyName the company name of the Outsourced Part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);

        this.companyName = companyName;
    }

    /**
     * Get the company name of the Outsourced Part
     * @return the company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set the company name of the Outsourced Part
     * @param companyName the company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
