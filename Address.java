package org.example.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Address {

    @ManyToOne
    @JoinColumn(name="vendor_id")
    @JsonIgnore
    private Vendor vendor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetname;
    private String state;
    private int pincode;

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetname;
    }

    public void setStreetName(String streetname) {
        this.streetname = streetname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPinCode(){
        return pincode;
    }

    public void setPinCode(int pincode){
        this.pincode = pincode;
    }
}
