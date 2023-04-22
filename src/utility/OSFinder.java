package utility;
public  class OSFinder {


    private static OS os;
    public static OS getOS() {

        if (os == null) {

            String operSys = System.getProperty("os.name".toLowerCase());
            System.out.println(operSys);

            if (operSys.contains("win") || operSys.contains("Win")) {
                os = OS.WINDOWS;
            }
            if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
                os = OS.LINUX;
            }
            if (operSys.contains("mac")) {
                os = OS.MAC;
            }
            if (operSys.contains("sunos")) {
                os = OS.SOLARIS;
            }

        }

        return os;

    }
}
