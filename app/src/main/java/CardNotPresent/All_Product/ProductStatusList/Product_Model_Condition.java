package CardNotPresent.All_Product.ProductStatusList;

import com.google.gson.annotations.SerializedName;

public class Product_Model_Condition
{

    @SerializedName("Id")
    private String Id;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("ProductCode")
    private String ProductCode;
    @SerializedName("ProductPrice")
    private String ProductPrice;
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
    @SerializedName("CustomerName")
    private String CustomerName;
    @SerializedName("CustomerMobileNumber")
    private String CustomerMobileNumber;
    @SerializedName("CustomerAddress")
    private String CustomerAddress;
    @SerializedName("CustomerPostalCode")
    private String CustomerPostalCode;
    @SerializedName("PspType")
    private String PspType;
    @SerializedName("RefNum")
    private String RefNum;
    @SerializedName("ResNum")
    private String ResNum;
    @SerializedName("TransactionStatus")
    private String TransactionStatus;

    public Product_Model_Condition(String id, String productName, String productCode, String productPrice, String description, String paymentLink, String merchantId, String terminalId, String ipgResponseCode, String transactionDateTime, String customerName, String customerMobileNumber, String customerAddress, String customerPostalCode, String pspType, String refNum, String resNum, String transactionStatus) {
        Id = id;
        ProductName = productName;
        ProductCode = productCode;
        ProductPrice = productPrice;
        Description = description;
        PaymentLink = paymentLink;
        MerchantId = merchantId;
        TerminalId = terminalId;
        IpgResponseCode = ipgResponseCode;
        TransactionDateTime = transactionDateTime;
        CustomerName = customerName;
        CustomerMobileNumber = customerMobileNumber;
        CustomerAddress = customerAddress;
        CustomerPostalCode = customerPostalCode;
        PspType = pspType;
        RefNum = refNum;
        ResNum = resNum;
        TransactionStatus = transactionStatus;
    }

    public Product_Model_Condition() {
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

    public String getPspType() {
        return PspType;
    }

    public void setPspType(String pspType) {
        PspType = pspType;
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
