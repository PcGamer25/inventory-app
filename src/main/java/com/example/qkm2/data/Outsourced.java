package com.example.qkm2.data;

/**
 * @author Yibo Hwang
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * Constructor for new outsourced part
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * This method sets the company name
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method gets the company name
     *
     * @return
     */
    public String getCompanyName() {
        return this.companyName;
    }

}
