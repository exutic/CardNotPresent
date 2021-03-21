package CardNotPresent.All_AbsentPayment.AbsentPaymentStatus;

import CardNotPresent.All_Persons.Persons.PersonsModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AbsentStatusModel
{
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("PersonalTransactionLogStatusList")
    private List<PersonsModel> personsModelList;
    @SerializedName("UserId")
    private String UserId;
    @SerializedName("Description")
    private String Description;

    public AbsentStatusModel(String userId) {
        this.UserId = userId;
    }

    public AbsentStatusModel(String usernameValidation, List<PersonsModel> personsModelList) {
        this.usernameValidation = usernameValidation;
        this.personsModelList = personsModelList;
    }

    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public List<PersonsModel> getPersonsModelList() {
        return personsModelList;
    }

    public void setPersonsModelList(List<PersonsModel> personsModelList) {
        this.personsModelList = personsModelList;
    }

    public String getUserId() {
        return UserId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
