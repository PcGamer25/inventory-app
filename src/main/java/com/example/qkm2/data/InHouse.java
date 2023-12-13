package com.example.qkm2.data;

/**
 * @author Yibo Hwang
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor for new in-house part
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * This method sets the machine ID
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This method gets the machine ID
     *
     * @return
     */
    public int getMachineId() {
        return this.machineId;
    }

}
