package com.luvina.la.config.constants;

import java.util.Arrays;
import java.util.List;

public class MessageConstants {
    public static final String CODE_200 = "200";
    public static final String CODE_500 = "500";

    // Error codes
    public static final String ER001 = "ER001";
    public static final String ER002 = "ER002";
    public static final String ER003 = "ER003";
    public static final String ER004 = "ER004";
    public static final String ER005 = "ER005";
    public static final String ER006 = "ER006";
    public static final String ER007 = "ER007";
    public static final String ER008 = "ER008";
    public static final String ER009 = "ER009";
    public static final String ER010 = "ER010";
    public static final String ER011 = "ER011";
    public static final String ER012 = "ER012";
    public static final String ER013 = "ER013";
    public static final String ER014 = "ER014";
    public static final String ER015 = "ER015";
    public static final String ER018 = "ER018";
    public static final String ER019 = "ER019";
    public static final String ER021 = "ER021";
    public static final String ER023 = "ER023";

    // Error messages
    public static final String ACCOUNT_NAME = "アカウント名";
    public static final String FULL_NAME = "氏名";
    public static final String KANA_NAME = "カタカナ氏名";
    public static final String BIRTH_DATE = "生年月日";
    public static final String EMAIL = "メールアドレス";
    public static final String PHONE = "電話番号";
    public static final String PASSWORD = "パスワード";
    public static final String GROUP = "グループ";
    public static final String CERTIFICATION = "資格";
    public static final String CERTIFICATION_START_DATE = "資格交付日";
    public static final String CERTIFICATION_END_DATE = "失効日";
    public static final String SCORE = "点数";
    public static final String DATE_FORMAT_MESSAGE = "yyyy/MM/dd";

    // Success messages
    // Thêm nhân viên thành công
    public static final String MSG001 = "MSG001";
    // Update nhân viên thành công
    public static final String MSG002 = "MSG002";
    // Xóa nhân viên thành công
    public static final String MSG003 = "MSG003";

    // Parameter names
    public static final String OFFSET = "オフセット";
    public static final String LIMIT = "リミット";
    public static final String ID_PARAMETER = "ID";

    public static List<String> getParams(String... params) {
        return Arrays.asList(params);
    }
}
