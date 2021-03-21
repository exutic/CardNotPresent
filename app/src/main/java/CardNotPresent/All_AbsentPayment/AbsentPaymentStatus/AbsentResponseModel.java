package CardNotPresent.All_AbsentPayment.AbsentPaymentStatus;

import com.google.gson.annotations.SerializedName;

public class AbsentResponseModel {

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
    @SerializedName("Description")
    private String Description;
    @SerializedName("PaymentLink")
    private String PaymentLink;
    @SerializedName("merchantId")
    private String merchantId;
    @SerializedName("terminalId")
    private String terminalId;
    @SerializedName("ipgResponseCode")
    private String ipgResponseCode;
    @SerializedName("transactionDateTime")
    private String InsertDateTime;
    @SerializedName("PspType")
    private String PspType;
    @SerializedName("resNum")
    private String resNum;
    @SerializedName("refNum")
    private String refNum;
    @SerializedName("transactionStatus")
    private String transactionStatus;
    @SerializedName("UserId")
    private String UserId;

    AbsentResponseModel() {

    }

    public AbsentResponseModel(String userId) {
        this.UserId = userId;
    }

    public AbsentResponseModel(String id, String firstName, String lastName, String amount, String nationalCode, String mobileNumber, String insertDateTime, String userId, String description, String pspType, String paymentLink) {
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPaymentLink() {
        return PaymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        PaymentLink = paymentLink;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getIpgResponseCode() {
        return ipgResponseCode;
    }

    public void setIpgResponseCode(String ipgResponseCode) {
        this.ipgResponseCode = ipgResponseCode;
    }

    public String getInsertDateTime() {
        return InsertDateTime;
    }

    public void setInsertDateTime(String insertDateTime) {
        InsertDateTime = insertDateTime;
    }

    public String getPspType() {
        return PspType;
    }

    public void setPspType(String pspType) {
        PspType = pspType;
    }

    public String getResNum() {
        return resNum;
    }

    public void setResNum(String resNum) {
        this.resNum = resNum;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
