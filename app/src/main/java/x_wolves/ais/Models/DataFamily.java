package x_wolves.ais.Models;

/**
 * Created by macbook on 25/12/2016.
 */

public class DataFamily {

    String Name;
    String Email;
    String Contact;
    String Address;
    String CNIC;

    public DataFamily(String name, String email, String contact, String address, String CNIC) {
        Name = name;
        Email = email;
        Contact = contact;
        Address = address;
        this.CNIC = CNIC;
    }
    public String getName() { return Name; }

    public void setName(String name) { Name = name; }

    public String getEmail() { return Email; }

    public void setEmail(String email) { Email = email; }

    public String getContact() { return Contact; }

    public void setContact(String contact) { Contact = contact; }

    public String getAddress() { return Address; }

    public void setAddress(String address) { Address = address; }

    public String getCNIC() { return CNIC; }

    public void setCNIC(String CNIC) { this.CNIC = CNIC;}
}
