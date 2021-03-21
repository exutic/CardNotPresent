package CardNotPresent.All_Persons.PersonsTransaction;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_ResultStatus_Per_Trans {
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("PersonalTransactionLogList")
    private List<PersonsTransModel> perTransModelList;
    @SerializedName("UserId")
    private String UserId;

    public Model_ResultStatus_Per_Trans(String userId) {
        this.UserId = userId;
    }

    public Model_ResultStatus_Per_Trans(String usernameValidation, List<PersonsTransModel> personsModelList) {
        this.usernameValidation = usernameValidation;
        this.perTransModelList = personsModelList;
    }

    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public List<PersonsTransModel> getPerTransModelList() {
        return perTransModelList;
    }

    public void setPerTransModelList(List<PersonsTransModel> perTransModelList) {
        this.perTransModelList = perTransModelList;
    }

    public String getUserId() {
        return UserId;
    }
}
