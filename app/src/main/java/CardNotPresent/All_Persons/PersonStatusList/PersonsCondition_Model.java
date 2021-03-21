package CardNotPresent.All_Persons.PersonStatusList;

import com.google.gson.annotations.SerializedName;

public class PersonsCondition_Model {
    //takmil shode
    @SerializedName("Id") // using for deleting the user from the list
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
    @SerializedName("Description")
    private String Description;
    @SerializedName("PaymentLink")
    private String PaymentLink;
    @SerializedName("MerchantId")
    private String MerchantId;
    @SerializedName("TerminalId")
    private String TerminalId;
    @SerializedName("IpgResponseCode")
    private String IpgResponseCode;
    @SerializedName("TransactionDateTime")
    private String TransactionDateTime;
    @SerializedName("PspType")
    private String PspType;
    @SerializedName("RefNum")
    private String RefNum;
    @SerializedName("ResNum")
    private String ResNum;
    // if true the transaction has been complete and if not its false
    @SerializedName("TransactionStatus")
    private String TransactionStatus;
    @SerializedName("UserId")
    private String UserId;

    public PersonsCondition_Model(String id, String firstName, String lastName, String amount, String nationalCode, String mobileNumber, String userId, String description, String pspType, String paymentLink, String merchantId, String terminalId, String ipgResponseCode, String transactionDateTime, String transactionStatus, String refNum, String resNum) {
        this.Id = id;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Amount = amount;
        this.NationalCode = nationalCode;
        this.MobileNumber = mobileNumber;
        this.UserId = userId;
        this.Description = description;
        this.PspType = pspType;
        this.PaymentLink = paymentLink;
        this.MerchantId = merchantId;
        this.TerminalId = terminalId;
        this.IpgResponseCode = ipgResponseCode;
        this.TransactionDateTime = transactionDateTime;
        this.TransactionStatus = transactionStatus;
        this.RefNum = refNum;
        this.ResNum = resNum;
    }

    public PersonsCondition_Model() {
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

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public String getIpgResponseCode() {
        return IpgResponseCode;
    }

    public void setIpgResponseCode(String ipgResponseCode) {
        IpgResponseCode = ipgResponseCode;
    }

    public String getTransactionDateTime() {
        return TransactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        TransactionDateTime = transactionDateTime;
    }

    public String getRefNum() {
        return RefNum;
    }

    public void setRefNum(String refNum) {
        RefNum = refNum;
    }

    public String getResNum() {
        return ResNum;
    }

    public void setResNum(String resNum) {
        ResNum = resNum;
    }

    public String getTransactionStatus() {
        return TransactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        TransactionStatus = transactionStatus;
    }
}

