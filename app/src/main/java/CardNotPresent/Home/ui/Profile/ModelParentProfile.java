package CardNotPresent.Home.ui.Profile;

import com.google.gson.annotations.SerializedName;

public class ModelParentProfile {
    @SerializedName("Customer")
    private ProfileModel profileModel;

    @SerializedName("ResultStatus")
    private String resultStatus;

    @SerializedName("Description")
    private String description;

    @SerializedName("Exception")
    private String exception;

    @SerializedName("UserId")
    private String UserId;

    public ModelParentProfile(String userId) {
        UserId = userId;
    }

    public ModelParentProfile(ProfileModel profileModel, String resultStatus, String description, String exception) {
        this.profileModel = profileModel;
        this.resultStatus = resultStatus;
        this.description = description;
        this.exception = exception;
    }

    public ProfileModel getProfileModel() {
        return profileModel;
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
}
