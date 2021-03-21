package CardNotPresent.All_AbsentPayment.AbsentPaymentLinkList;

import com.google.gson.annotations.SerializedName;

public class MerchantModel
{
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("MerchantId")
    private String MerchantId;
    @SerializedName("TerminalId")
    private String TerminalId;
    @SerializedName("PspType")
    private String PspType;
    @SerializedName("PspName")
    private String PspName;
    @SerializedName("Id")
    private String id;
    @SerializedName("PaymentLink")
    private String PaymentLink;

    public MerchantModel(String userId)
    {
        this.UserId=userId;
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

    public String getPspName() {
        return PspName;
    }

    public void setPspName(String pspName) {
        PspName = pspName;
    }

    public String getPaymentLink() {
        return PaymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        PaymentLink = paymentLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
