package ml.workanet.app.pharmacie.utils;


import java.util.Base64;

public class ImageUtil {
    public String getImgData(byte[] image){
        return Base64.getMimeEncoder().encodeToString(image);
    }
}
