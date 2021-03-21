package CardNotPresent.Home.ui.Profile;

import com.google.gson.annotations.SerializedName;

public class ProfileModel {

    @SerializedName("CustomerTypeID")
    private String customerTypeId;

    @SerializedName("CustomerName")
    private String fullName;

    @SerializedName("NationalCode")
    private String nationalID;

    @SerializedName("Mobile")
    private String Mobile;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("Email")
    private String Email;

    @SerializedName("HomeAddress")
    private String homeAddress;

    @SerializedName("CompanyCode")
    private String CompanyCode;



    public ProfileModel(String customerTypeId, String fullName, String nationalID, String mobile, String phoneNumber, String email, String homeAddress, String companyCode) {
        this.customerTypeId = customerTypeId;
        this.fullName = fullName;
        this.nationalID = nationalID;
        Mobile = mobile;
        this.phoneNumber = phoneNumber;
        Email = email;
        this.homeAddress = homeAddress;
        CompanyCode = companyCode;
    }


    public String getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(String customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyName(String companyCode) {
        CompanyCode = companyCode;
    }
}
