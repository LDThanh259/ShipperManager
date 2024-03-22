
package model;

public class Shipper {
    private int Shipper_Id;
    private String Name;
    private String BirthDay;
    private String Gender;
    private String StartWork;
    private String Phone;
    private String Email;
    private String Address;
    private String Description;

    public Shipper() {
    }

    public Shipper(int Shipper_Id, String Name, String BirthDay, String Gender, String StartWork, String Phone, String Email, String Address, String Description) {
        this.Shipper_Id = Shipper_Id;
        this.Name = Name;
        this.BirthDay = BirthDay;
        this.Gender = Gender;
        this.StartWork = StartWork;
        this.Phone = Phone;
        this.Email = Email;
        this.Address = Address;
        this.Description = Description;
    }

    public int getShipper_Id() {
        return Shipper_Id;
    }

    public void setShipper_Id(int Shipper_Id) {
        this.Shipper_Id = Shipper_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String BirthDay) {
        this.BirthDay = BirthDay;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getStartWork() {
        return StartWork;
    }

    public void setStartWork(String StartWork) {
        this.StartWork = StartWork;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "Shipper{" + "Shipper_Id=" + Shipper_Id + ", Name=" + Name + ", BirthDay=" + BirthDay + ", Gender=" + Gender + ", StartWork=" + StartWork + ", Phone=" + Phone + ", Email=" + Email + ", Address=" + Address + ", Description=" + Description + '}';
    }
    
    
}
