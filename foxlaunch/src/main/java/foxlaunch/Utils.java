package foxlaunch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Utils {

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static String getFileSHA256(File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            final byte[] buffer = new byte[4096];
            int read = in.read(buffer, 0, 4096);

            while (read > -1) {
                md.update(buffer, 0, read);
                read = in.read(buffer, 0, 4096);
            }

            byte[] digest = md.digest();
            return String.format("%0" + (digest.length << 1) + "x", new BigInteger(1, digest)).toUpperCase();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static String getMissingSHA256(String filename) {
        switch (filename) {
            case "minecraft_server.1.19.2.jar":
                return "B26727069EF5F61C704ADD9A378AC90E3D271FD7876C0BD3DCFBE9FD0BEC4D96";
            case "server-1.19.2-mappings.txt":
                return "E3E93DAC4D886924FF3B81CE5D210925B9E4112776FDEF4D0E6D5FEFB062A879";
            case "mcp_config-1.19.2-20220805.130853.zip": // 重要: Update it when updating MCP version
                return "FD4CF084B96492DE87499B49D1AE5896EF14A3F85B5462D2842D7B193FBA9135";
            case "authlib-3.11.49.jar":
                return "866D5B4C844CE698552EC4EB04C47CC7760EF71E3FA2BE5F9F32DDAC677D6E1A";
            case "brigadier-1.0.18.jar":
                return "EDC4926AA4B49010F6E7AC46EFD623FB38F9517344D26F6251D79A26A9738C0B";
            case "commons-io-2.11.0.jar":
                return "961B2F6D87DBACC5D54ABF45AB7A6E2495F89B75598962D8C723CEA9BC210908";
            case "datafixerupper-5.0.28.jar":
                return "D1990B4801EE970F211DD507D0F7F299E86AF36F6330DA83CFBA87A8DDF4076D";
            case "failureaccess-1.0.1.jar":
                return "A171EE4C734DD2DA837E4B16BE9DF4661AFAB72A41ADAF31EB84DFDAF936CA26";
            case "fastutil-8.5.6.jar":
                return "5021369BDC72E2CA24222D036C9B35C9733DC9C082A0891C4DBA5A583663F30B";
            case "gson-2.8.9.jar":
                return "D3999291855DE495C94C743761B8AB5176CFEABE281A5AB0D8E8D45326FD703E";
            case "guava-31.0.1-jre.jar":
                return "D5BE94D65E87BD219FB3193AD1517BAA55A3B88FC91D21CF735826AB5AF087B9";
            case "javabridge-1.2.24.jar":
                return "B5F8871A1799B36E27A5F2AD8A4B47DB39210031C967B794707B92E9E3F8598E";
            case "jna-5.10.0.jar":
                return "E335C10679F743207D822C5F7948E930319835492575A9DBA6B94F8A3B96FCC8";
            case "jna-platform-5.10.0.jar":
                return "1F71AFD977051BF0109EF5E3767D4E2AFD777BE894D89788CC0F38AD68F6A16F";
            case "jopt-simple-5.0.4.jar":
                return "DF26CC58F235F477DB07F753BA5A3AB243EBE5789D9F89ECF68DD62EA9A66C28";
            case "log4j-api-2.17.0.jar":
                return "AB9CADC80E234580E3F3C8C18644314FCCD4B3CD3F7085D4E934866CB561B95D";
            case "log4j-core-2.17.0.jar":
                return "65C33DC9B24A5E5F6CACAE62680641582894749C7BF16C951032EF92F3E12A60";
            case "log4j-slf4j18-impl-2.17.0.jar":
                return "40BB460CE600AABF832BE01141D07ED439FE288140AAFAE4FDC391D682E9CF43";
            case "logging-1.0.0.jar":
                return "B2F5D129244F7A90973623CB683BECFCCD0E23B2AC3ADEC028F259F27B0A5400";
            case "netty-buffer-4.1.77.Final.jar":
                return "41B7DDC4DD124C7E75AF33A13A426FDA4E1EC87C387CD234971E7DF4C0B51C26";
            case "netty-codec-4.1.77.Final.jar":
                return "84E4E01DD5B345311E971289B5BC08C0DFD6054A28D16853F0416943C9A3E458";
            case "netty-common-4.1.77.Final.jar":
                return "40DD9B5EF14878F050A1F7F4D5647D53473F134E349665B47243BDE56DE7A51F";
            case "netty-handler-4.1.77.Final.jar":
                return "7911BECD4850FF3FC3D93B4BE7C468A2F6444FB48C17EEC03C807856FAF11E0A";
            case "netty-resolver-4.1.77.Final.jar":
                return "0161CFE9544B3656ED0DE67D8937828101859E94BCD0CAAF58D21AC7011EABD4";
            case "netty-transport-4.1.77.Final.jar":
                return "034CDF7D81FEAAD9977C3D8B4FC05611952BC9861DFB9085B8962E2C1DE582AA";
            case "netty-transport-classes-epoll-4.1.77.Final.jar":
                return "0593FB942D7D57AADCC22360A238DCFAC9F29013A20201EF49CC392B0DD2AAF9";
            case "netty-transport-native-epoll-4.1.77.Final-linux-x86_64.jar":
                return "5965ED3DD2558BFC017CCD765698FB2801D0C0D54900779D033D23959171A435";
            case "netty-transport-native-epoll-4.1.77.Final-linux-aarch_64.jar":
                return "118AD3C04F7169C7893A4D909558C37FFAED15E4B549F2C25B864E2F966ECCE8";
            case "netty-transport-native-unix-common-4.1.77.Final.jar":
                return "108D27F325FA9CE9A914FA53638FBEEB1A384148FB3A13389BD09A5E38E4AC1E";
            case "oshi-core-5.8.5.jar":
                return "FE16BD8836EECF3D152585C2151322273B68237D13F223E662E0DB959DD13680";
            case "slf4j-api-1.8.0-beta4.jar":
                return "602B712329C84B4A83C40464F4FDFD0FE4238C53EF397139A867064739DBF4E0";
            case "bootstraplauncher-1.1.2.jar":
                return "853B61FD165CAF4103B5B969AFBC5B2B037ABC310ED644518CD967B50D5189A4";
            default:
                return null;
        }
    }

    public static URL pathToURL(String path) throws Exception {
        return new File(path).toURI().toURL();
    }

    public static void relaunch(String mainClass, URL[] classPath, String[] args, boolean closeClassLoader) throws Exception {
        URLClassLoader ucl = new URLClassLoader(classPath, null);
        Class.forName(mainClass, true, ucl).getMethod("main", String[].class).invoke(null, new Object[] { args });
        if (closeClassLoader) {
            ucl.close();
        }
    }

    public static boolean isJarCorrupted(File jarFile) {
        try {
            if (jarFile.exists()) {
                new JarFile(jarFile).close();
            }
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static File unpackSingleFileZip(File file) throws IOException {
        try (ZipFile zipFile = new ZipFile(file)) {
            if (zipFile.size() > 1) {
                throw new IOException("Not single file zip!");
            }

            Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
            if (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = enumeration.nextElement();
                File outFile = new File(file.getParentFile(), zipEntry.getName());
                try (FileOutputStream out = new FileOutputStream(outFile)) {
                    try (InputStream in = zipFile.getInputStream(zipEntry)) {
                        byte[] bytes = new byte[4096];
                        int readSize;
                        while ((readSize = in.read(bytes)) > 0) {
                            out.write(bytes, 0, readSize);
                        }
                    }
                    out.flush();
                    return outFile;
                }
            } else {
                throw new IOException("Empty zip!");
            }
        } finally {
            try { file.delete(); } catch (Exception ignored) {}
        }
    }

    public static File unpackZipEntry(File file, File target, String entry) throws IOException {
        try (ZipFile zipFile = new ZipFile(file)) {
            ZipEntry zipEntry = zipFile.getEntry(entry);
            if (zipEntry != null) {
                try (FileOutputStream out = new FileOutputStream(target)) {
                    try (InputStream in = zipFile.getInputStream(zipEntry)) {
                        byte[] bytes = new byte[4096];
                        int readSize;
                        while ((readSize = in.read(bytes)) > 0) {
                            out.write(bytes, 0, readSize);
                        }
                    }
                    out.flush();
                    return target;
                }
            } else {
                throw new IOException("Zip entry not found: " + entry);
            }
        }
    }

    public static File findServerJar() throws IOException {
        try {
            URL jarUrl = Utils.class.getProtectionDomain().getCodeSource().getLocation();
            File jarFile = new File(URLDecoder.decode(jarUrl.getPath(), "UTF-8"));
            if (jarFile.isFile()) {
                return jarFile;
            } else {
                throw new IOException(jarFile.getName() + " is not a file!");
            }
        } catch (IOException e) {
            String s = System.getProperty("java.class.path");
            if (s != null) {
                if (s.replace(":", ";").split(";").length == 1) {
                    return new File(s);
                }
            }
            throw e;
        }
    }
}
