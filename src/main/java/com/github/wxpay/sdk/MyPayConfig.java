package  com.github.wxpay.sdk;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyPayConfig extends WXPayConfig {
    private  byte[] certData;
    private static MyPayConfig INSTANCE;
    /**
     * 获取 App ID
     *
     * @return App ID
     */
    public String getAppID(){
        return "wx509992ee3584678d";
    }
    private MyPayConfig(){
        try {
            Resource resource = new ClassPathResource("cert/apiclient_cert.p12");
            File sourceFile = resource.getFile();
            InputStream certStream = new FileInputStream(sourceFile);
            this.certData = new byte[(int) sourceFile.length()];
            certStream.read(this.certData);
            certStream.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static MyPayConfig getInstance(){
        if (INSTANCE == null) {
            synchronized (MyPayConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MyPayConfig();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    public String getMchID(){
        return "1543333401";
    }

    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    public String getKey(){
        return "cb27496879517bd0e42b4d4f20bc8efc";
    }

    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    @Override
    protected IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;

    }

}
