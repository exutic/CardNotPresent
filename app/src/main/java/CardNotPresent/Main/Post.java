package CardNotPresent.Main;

import com.google.gson.annotations.SerializedName;

//TODO Post Class mainly for retrieving access token and login into portal
public class Post {

    @SerializedName("UserName")
    private String username;

    @SerializedName("Password")
    private String password;

    @SerializedName("UserId")
    private String userID;

    @SerializedName("Name")
    private String fullName;

    @SerializedName("AccessToken")
    private String AccessToken;

    @SerializedName("ResultStatus")
    private String usernameValidation = "";


    public Post() {

    }

    public Post(String userID) {
        this.userID = userID;
    }

    public Post(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public String getUsernameValidation() {
        return usernameValidation;
    }

    public void setUsernameValidation(String usernameValidation) {
        this.usernameValidation = usernameValidation;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
