package Helpers;

public class MyStringPair {
    private String username;
    private String id;

    public MyStringPair(String username, String id)
    {
        this.username   = username;
        this.id   = id;
    }

    public String getUsername()   { return username; }
    public String getId()   { return id; }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setId(String id){
        this.id = id;
    }
}
