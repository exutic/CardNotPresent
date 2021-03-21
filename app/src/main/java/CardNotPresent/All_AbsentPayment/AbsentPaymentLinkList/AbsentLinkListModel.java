package CardNotPresent.All_AbsentPayment.AbsentPaymentLinkList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbsentLinkListModel
{
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("PaymentLink")
    private String PaymentLink;
    @SerializedName("MerchantPaymentInformationList")
    private List<MerchantModel> merchantModels;

    public AbsentLinkListModel(String userId) {
        this.UserId = userId;
    }

    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPaymentLink() {
        return PaymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        PaymentLink = paymentLink;
    }

    public List<MerchantModel> getMerchantModels() {
        return merchantModels;
    }

    public void setMerchantModels(List<MerchantModel> merchantModels) {
        this.merchantModels = merchantModels;
    }
}
