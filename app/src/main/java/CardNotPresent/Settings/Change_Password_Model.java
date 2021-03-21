package CardNotPresent.Settings;

import com.google.gson.annotations.SerializedName;

public class Change_Password_Model
{
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("CurrentPassword")
    private String CurrentPassword;
    @SerializedName("NewPassword")
    private String NewPassword;
    @SerializedName("ConfirmPassword")
    private String ConfirmPassword;
    @SerializedName("ResultStatus")
    private String ResultStatus;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Exception")
    private String Exception;

    public Change_Password_Model() {
    }

    public Change_Password_Model(String userId, String currentPassword, String newPassword, String confirmPassword) {
        UserId = userId;
        CurrentPassword = currentPassword;
        NewPassword = newPassword;
        ConfirmPassword = confirmPassword;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCurrentPassword() {
        return CurrentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        CurrentPassword = currentPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }

    public String getResultStatus() {
        return ResultStatus;
    }

    public void setResultStatus(String resultStatus) {
        ResultStatus = resultStatus;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getException() {
        return Exception;
    }

    public void setException(String exception) {
        Exception = exception;
    }
}
