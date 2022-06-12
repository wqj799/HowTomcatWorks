package connector.http;

public class Constants {
    public static final String WEB_ROOT = ClassLoader.getSystemResource("").getPath();
    public static final String Module = "how-tomcat-works-ex03";
    public static final String Package = "ex03.pyrmont.connector.http";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;
    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;
}
