package CardNotPresent.All_AbsentPayment.AbsentPaymentTransaction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Link_ResultStatus_Model
{
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("PersonalTransactionLogList")
    private List<LinkModel> linkModelList;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("PaymentLink")
    private String PaymentLink;

    public Link_ResultStatus_Model(String userId) {
        this.UserId = userId;
    }

    public Link_ResultStatus_Model(String usernameValidation, List<LinkModel> linkModelList) {
        this.usernameValidation = usernameValidation;
        this.linkModelList = linkModelList;
    }


    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public List<LinkModel> getLinkModelList() {
        return linkModelList;
    }

    public void setLinkModelList(List<LinkModel> linkModelList) {
        this.linkModelList = linkModelList;
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

}
