package thinkmore.com.multichoiceadapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/3/24.
 */
public class ProductInfo implements Parcelable{

    private String imgPath;
    private String productName;
    private String productPrice;
    private boolean isChecked;

    // alt+insert

    /**
     * 无参构造器
     */
    public ProductInfo() {
    }


    protected ProductInfo(Parcel in) {
        imgPath = in.readString();
        productName = in.readString();
        productPrice = in.readString();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imgPath);
        dest.writeString(productName);
        dest.writeString(productPrice);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductInfo> CREATOR = new Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel in) {
            return new ProductInfo(in);
        }

        @Override
        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }
    };

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
