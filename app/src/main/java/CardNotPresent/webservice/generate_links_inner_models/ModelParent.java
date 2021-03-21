package CardNotPresent.webservice.generate_links_inner_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//TODO This model contains response including Model_PSP_List & Model_Terminal_List
public class ModelParent
{
    @SerializedName("UserId")
    private String UserId;

    @SerializedName("ResultStatus")
    private String resultStatus;
    @SerializedName("Description")
    private String description;
    @SerializedName("Exception")
    private String exception;
    @SerializedName("Id")
    private String id;
    @SerializedName("PspType")
    private String pspType;

    @SerializedName("PspList")
    private List<Model_PSP_List> modelPspLists_list;

    @SerializedName("UserMerchantInfoId")
    private String userMerchantInfoId;

    @SerializedName("TerminalList")
    private List<Model_Terminal_List> modelTerminalLists_list;


    public ModelParent(String userId, String id) {
        this.UserId = userId;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<Model_PSP_List> getModelPspLists_list() {
        return modelPspLists_list;
    }

    public List<Model_Terminal_List> getModelTerminalLists_list() {
        return modelTerminalLists_list;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getException() {
        return exception;
    }

    public String getPspType() {
        return pspType;
    }

    public String getUserMerchantInfoId() {
        return userMerchantInfoId;
    }
}
