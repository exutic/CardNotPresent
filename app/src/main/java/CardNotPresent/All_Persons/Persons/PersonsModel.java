package CardNotPresent.All_Persons.Persons;

import com.google.gson.annotations.SerializedName;

public class PersonsModel {
    //takmil shode
    @SerializedName("Id")
    private String Id;
    @SerializedName("FirstName")
    private String FirstName;
    @SerializedName("LastName")
    private String LastName;
    @SerializedName("Amount")
    private String Amount;
    @SerializedName("NationalCode")
    private String NationalCode;
    @SerializedName("MobileNumber")
    private String MobileNumber;
    @SerializedName("InsertDateTime")
    private String InsertDateTime;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("Description")
    private String Description;
    @SerializedName("PspType")
    private String PspType;
    @SerializedName("PaymentLink")
    private String PaymentLink;
    @SerializedName("ResultStatus")
    private String ResultStatus;
    @SerializedName("Exception")
    private String Exception;

    @SerializedName("IpgResponseCode")
    private String IpgResponseCode;
    @SerializedName("TransactionStatus")
    private String TransactionStatus;


    //added for absentPayment only
    @SerializedName("TransactionDateTime")
    private String TransactionDateTime;

    PersonsModel() {

    }


    public PersonsModel(String userId) {
        this.UserId = userId;
    }

    public PersonsModel(String id, String firstName, String lastName, String amount, String nationalCode, String mobileNumber, String insertDateTime, String userId, String description, String pspType, String paymentLink) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Amount = amount;
        NationalCode = nationalCode;
        MobileNumber = mobileNumber;
        InsertDateTime = insertDateTime;
        UserId = userId;
        Description = description;
        PspType = pspType;
        PaymentLink = paymentLink;
    }

    public PersonsModel(String id, String firstName, String lastName, String amount, String nationalCode, String mobileNumber, String insertDateTime, String userId, String description, String pspType, String paymentLink,
        String transactionStatus ,String ipgResponseCode   ) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Amount = amount;
        NationalCode = nationalCode;
        MobileNumber = mobileNumber;
        InsertDateTime = insertDateTime;
        UserId = userId;
        Description = description;
        PspType = pspType;
        PaymentLink = paymentLink;
        TransactionStatus = transactionStatus;
        IpgResponseCode=ipgResponseCode;

    }

    public PersonsModel(String id, String firstName, String lastName, String amount, String nationalCode, String mobileNumber, String transactionDateTime, String userId, String description, String paymentLink,
                        String transactionStatus ,String ipgResponseCode   ) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Amount = amount;
        NationalCode = nationalCode;
        MobileNumber = mobileNumber;
        TransactionDateTime = transactionDateTime;
        UserId = userId;
        Description = description;
        PaymentLink = paymentLink;
        TransactionStatus = transactionStatus;
        IpgResponseCode=ipgResponseCode;

    }

    public String getTransactionDateTime() {
        return TransactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        TransactionDateTime = transactionDateTime;
    }

    public String getResultStatus() {
        return ResultStatus;
    }

    public void setResultStatus(String resultStatus) {
        ResultStatus = resultStatus;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getNationalCode() {
        return NationalCode;
    }

    public void setNationalCode(String nationalCode) {
        NationalCode = nationalCode;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getInsertDateTime() {
        return InsertDateTime;
    }

    public void setInsertDateTime(String insertDateTime) {
        InsertDateTime = insertDateTime;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPspType() {
        return PspType;
    }

    public void setPspType(String pspType) {
        PspType = pspType;
    }

    public String getPaymentLink() {
        return PaymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        PaymentLink = paymentLink;
    }

    public String getIpgResponseCode() {
        return IpgResponseCode;
    }

    public void setIpgResponseCode(String ipgResponseCode) {
        IpgResponseCode = ipgResponseCode;
    }

    public String getTransactionStatus() {
        return TransactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        TransactionStatus = transactionStatus;
    }
}
