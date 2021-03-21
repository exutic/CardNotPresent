package CardNotPresent.webservice.generate_links_inner_models;

import com.google.gson.annotations.SerializedName;

public class ModelSendIntegerTerminal {
    @SerializedName("UserId")
    private int userId;
    @SerializedName("Id")
    private int id;
    @SerializedName("PspType")
    private int pspType;
    @SerializedName("UserMerchantInfoId")
    private int userMerchantInfoId;

    public ModelSendIntegerTerminal(int userId, int id, int pspType, int userMerchantInfoId) {
        this.userId = userId;
        this.id = id;
        this.pspType = pspType;
        this.userMerchantInfoId = userMerchantInfoId;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public int getPspType() {
        return pspType;
    }

    public int getUserMerchantInfoId() {
        return userMerchantInfoId;
    }
}
