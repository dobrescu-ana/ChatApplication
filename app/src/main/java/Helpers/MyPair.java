package Helpers;

public class MyPair
{
    private String key;
    private int value;

    public MyPair(String aKey, int aValue)
    {
        key   = aKey;
        value = aValue;
    }

    public String getKey()   { return key; }
    public int getValue() { return value; }
    public void setValue(int nr) {
        this.value = nr;
    }
    public void setKey(String key){
        this.key = key;
    }
}