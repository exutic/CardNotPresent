package CardNotPresent.All_Persons.PersonStatusList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_ResultStatus_Per_Conditions {
    @SerializedName("ResultStatus")
    private String usernameValidationPersonCondition;
    @SerializedName("PersonalTransactionLogStatusList")
    private List<PersonsCondition_Model> perConditionModelList;
    @SerializedName("UserId")
    private String UserIdPersonCondition;

    public Model_ResultStatus_Per_Conditions(String userIdPersonCondition) {
        UserIdPersonCondition = userIdPersonCondition;
    }

    public Model_ResultStatus_Per_Conditions(String usernameValidationPersonCondition, List<PersonsCondition_Model> perConditionModelList) {
        this.usernameValidationPersonCondition = usernameValidationPersonCondition;
        this.perConditionModelList = perConditionModelList;
    }

    public String getUsernameValidationPersonCondition() {
        return usernameValidationPersonCondition;
    }

    public void setUsernameValidationPersonCondition(String usernameValidationPersonCondition) {
        this.usernameValidationPersonCondition = usernameValidationPersonCondition;
    }

    public List<PersonsCondition_Model> getPerConditionModelList() {
        return perConditionModelList;
    }

    public void setPerConditionModelList(List<PersonsCondition_Model> perConditionModelList) {
        this.perConditionModelList = perConditionModelList;
    }

    public String getUserIdPersonCondition() {
        return UserIdPersonCondition;
    }

    public void setUserIdPersonCondition(String userIdPersonCondition) {
        UserIdPersonCondition = userIdPersonCondition;
    }
}
