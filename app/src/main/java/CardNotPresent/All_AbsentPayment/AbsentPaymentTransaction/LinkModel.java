package CardNotPresent.All_AbsentPayment.AbsentPaymentTransaction;

import com.google.gson.annotations.SerializedName;

public class LinkModel
{
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
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("PspType")
    private String PspType;
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
    @SerializedName("Description")
    private String Description;
    @SerializedName("CustomerPostalCode")
    private String CustomerPostalCode;
    @SerializedName("RefNum")
    private String RefNum;
    @SerializedName("ResNum")
    private String ResNum;

    public LinkModel(String id, String firstName, String lastName, String amount, String nationalCode, String mobileNumber, String userId, String description, String pspType, String paymentLink, String merchantId, String terminalId, String ipgResponseCode, String transactionDateTime, String customerPostalCode, String refNum, String resNum) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Amount = amount;
        NationalCode = nationalCode;
        MobileNumber = mobileNumber;
        UserId = userId;
        Description = description;
        PspType = pspType;
        PaymentLink = paymentLink;
        MerchantId = merchantId;
        TerminalId = terminalId;
        IpgResponseCode = ipgResponseCode;
        TransactionDateTime = transactionDateTime;
        CustomerPostalCode = customerPostalCode;
        RefNum = refNum;
        ResNum = resNum;
    }

    public LinkModel() {
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

    public String getCustomerPostalCode() {
        return CustomerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        CustomerPostalCode = customerPostalCode;
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
}

