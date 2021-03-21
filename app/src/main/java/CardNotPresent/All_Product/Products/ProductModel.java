package CardNotPresent.All_Product.Products;

import com.google.gson.annotations.SerializedName;
public class ProductModel
{
    @SerializedName("Id")
    private String Id;
    @SerializedName("ProductName")
    private String ProductName;
    @SerializedName("ProductCode")
    private String ProductCode;
    @SerializedName("ProductPrice")
    private String ProductPrice;
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

    public ProductModel(String id, String productName, String productCode, String productPrice, String insertDateTime, String userId, String description, String pspType, String paymentLink) {
        Id = id;
        ProductName = productName;
        ProductCode = productCode;
        ProductPrice = productPrice;
        InsertDateTime = insertDateTime;
        UserId = userId;
        Description = description;
        PspType = pspType;
        PaymentLink = paymentLink;
    }

    public ProductModel() {
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
}
