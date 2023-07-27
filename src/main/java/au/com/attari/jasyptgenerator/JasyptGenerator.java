package au.com.attari.jasyptgenerator;

import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptGenerator {

    private String action;
    private String encryptor;
    private char[] password;
    private String text;

    public static void main(String[] args) {

        if(args.length != 4) {
            System.out.println("usage: java JasyptGenerator ENC|DEC AES|Text <encryption password> <text>");
            System.exit(-1);
        }

        JasyptGenerator jG = new JasyptGenerator();

        if(!args[0].equalsIgnoreCase("ENC") && !args[0].equalsIgnoreCase("DEC")) {
            System.out.println("First argument is ENC|DEC");
            System.exit(-1);
        }
        jG.setAction(args[0]);

        if(!args[1].equalsIgnoreCase("AES") &&
                !args[1].equalsIgnoreCase("TEXT") &&
                !args[1].equalsIgnoreCase("PBE")) {
            System.out.println("Second argument is SHA|Text|PBE");
            System.exit(-1);
        }
        jG.setEncryptor(args[1]);

        jG.setPassword(args[2].toCharArray());

        jG.setText(args[3]);

        jG.encryptOrDecrypt();

        System.exit(0);
    }

    private void encryptOrDecrypt() {
        if(getAction().equalsIgnoreCase("ENC")) {
            if (getEncryptor().equalsIgnoreCase("AES")) {
                String encrypted = shaEncrypt(text);
                System.out.println(String.format("%s encrypts to %s", getText(), encrypted));
            }
            else if (getEncryptor().equalsIgnoreCase("TEXT")) {
                String encrypted = encrypt(text);
                System.out.println(String.format("%s encrypts to %s", getText(), encrypted));
            }
        }
        else if(getAction().equalsIgnoreCase("DEC")) {
            if (getEncryptor().equalsIgnoreCase("AES")) {
                String encrypted = shaDecrypt(text);
                System.out.println(String.format("%s decrypts to %s", getText(), encrypted));
            }
            else if (getEncryptor().equalsIgnoreCase("TEXT")) {
                String encrypted = shaDecrypt(text);
                System.out.println(String.format("%s decrypts to %s", getText(), encrypted));
            }
        }

    }

    private String encrypt(String text) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(getPassword());
        String encryptedText = textEncryptor.encrypt(text);

        return encryptedText;
    }

    private String decrypt(String text) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray(getPassword());
        String decryptedText = textEncryptor.decrypt(text);

        return decryptedText;
    }

    // Since 3.0.5

    private String shaEncrypt(String text) {
       // StringEncryptor textEncryptor = stringEncryptor();
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPasswordCharArray(getPassword());
        String encryptedText = textEncryptor.encrypt(text);
        return encryptedText;
    }

    private String shaDecrypt(String text) {
        // StringEncryptor textEncryptor = stringEncryptor();
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPasswordCharArray(getPassword());
        String decryptedText = textEncryptor.decrypt(text);
        return decryptedText;
    }

    /*
    public static StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password.toString());
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setPoolSize("1");
        config.setKeyObtentionIterations("1000");

        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
     */

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEncryptor() {
        return encryptor;
    }

    public void setEncryptor(String encryptor) {
        this.encryptor = encryptor;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}