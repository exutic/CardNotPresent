package CardNotPresent.All_Product.ProductTransaction;

import com.google.gson.annotations.SerializedName;

public class Product_Transaction_List_Model
{
    @SerializedName("Id")
    private String Id;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("ProductCode")
    private String ProductCode;
    @SerializedName("ProductPrice")
    private String ProductPrice;
    @SerializedName("PaymentLink")
    private String PaymentLink;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("MerchantId")
    private String MerchantId;
    @SerializedName("TerminalId")
    private String TerminalId;
    @SerializedName("PspType")
    private String PspType;
    @SerializedName("IpgResponseCode")
    private String IpgResponseCode;
    @SerializedName("TransactionDateTime")
    private String TransactionDateTime;
    @SerializedName("Description")
    private String Description;
    @SerializedName("CustomerName")
    private String CustomerName;
    @SerializedName("CustomerMobileNumber")
    private String CustomerMobileNumber;
    @SerializedName("CustomerAddress")
    private String CustomerAddress;
    @SerializedName("CustomerPostalCode")
    private String CustomerPostalCode;
    @SerializedName("RefNum")
    private String RefNum;
    @SerializedName("ResNum")
    private String ResNum;

    public Product_Transaction_List_Model(String id, String productName, String productCode, String productPrice, String paymentLink, String userId, String merchantId, String terminalId, String pspType, String ipgResponseCode, String transactionDateTime, String description, String customerName, String customerMobileNumber, String customerAddress, String customerPostalCode, String refNum, String resNum) {
        Id = id;
        ProductName = productName;
        ProductCode = productCode;
        ProductPrice = productPrice;
        PaymentLink = paymentLink;
        UserId = userId;
        MerchantId = merchantId;
        TerminalId = terminalId;
        PspType = pspType;
        IpgResponseCode = ipgResponseCode;
        TransactionDateTime = transactionDateTime;
        Description = description;
        CustomerName = customerName;
        CustomerMobileNumber = customerMobileNumber;
        CustomerAddress = customerAddress;
        CustomerPostalCode = customerPostalCode;
        RefNum = refNum;
        ResNum = resNum;
    }

    public Product_Transaction_List_Model() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductCode() {
        return ProductCode;
    }

    public void setProductCode(String productCode) {
        ProductCode = productCode;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getPaymentLink() {
        return PaymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        PaymentLink = paymentLink;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
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

    public String getPspType() {
        return PspType;
    }

    public void setPspType(String pspType) {
        PspType = pspType;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerMobileNumber() {
        return CustomerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        CustomerMobileNumber = customerMobileNumber;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
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
