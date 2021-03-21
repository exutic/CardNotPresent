package CardNotPresent.Main;

import com.google.gson.annotations.SerializedName;

public class Forget_Password_Model
{
    @SerializedName("NationalCode")
    private String NationalCode;
    @SerializedName("Email")
    private String Email;
    @SerializedName("ResultStatus")
    private String ResultStatus;
    @SerializedName("Description")
    private String Description;
    @SerializedName("Exception")
    private String Exception;

    public Forget_Password_Model() {
    }

    public String getNationalCode() {
        return NationalCode;
    }

    public void setNationalCode(String nationalCode) {
        NationalCode = nationalCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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
