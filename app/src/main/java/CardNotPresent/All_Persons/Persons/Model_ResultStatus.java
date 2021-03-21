package CardNotPresent.All_Persons.Persons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_ResultStatus
{
    @SerializedName("ResultStatus")
    private String usernameValidation;
    @SerializedName("PersonalList")
    private List<PersonsModel> personsModelList;
    @SerializedName("UserId")
    private String UserId;

    public Model_ResultStatus(String userId) {
        this.UserId = userId;
    }

    public Model_ResultStatus(String usernameValidation, List<PersonsModel> personsModelList) {
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
}
