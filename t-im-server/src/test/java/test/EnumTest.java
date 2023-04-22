package test;


/**
 * Created by DELL(mxd) on 2022/9/6 10:41
 */
public enum  EnumTest {

    AUTH(1,"222"),
    LOGIN_PATH(2,"111");


    private final int id;
    private final String path;

    EnumTest(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }
}
