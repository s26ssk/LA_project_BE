/**
 * Copyright(C) 2024  Luvina
 * MessageConstants.java, 10/10/2024 KhanhNV
 */
package com.luvina.la.config.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Lớp chứa các hằng số mã lỗi, mã thành công và thông điệp liên quan đến ứng dụng.
 */
public class MessageConstants {
    public static final String CODE_200 = "200"; // Mã thành công
    public static final String CODE_500 = "500"; // Mã lỗi server

    // Mã lỗi
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

    // Thông điệp lỗi tương ứng
    public static final String ACCOUNT_NAME = "アカウント名"; // Tên tài khoản
    public static final String FULL_NAME = "氏名"; // Họ và tên
    public static final String KANA_NAME = "カタカナ氏名"; // Tên katakana
    public static final String BIRTH_DATE = "生年月日"; // Ngày sinh
    public static final String EMAIL = "メールアドレス"; // Địa chỉ email
    public static final String PHONE = "電話番号"; // Số điện thoại
    public static final String PASSWORD = "パスワード"; // Mật khẩu
    public static final String GROUP = "グループ"; // Nhóm
    public static final String CERTIFICATION = "資格"; // Chứng chỉ
    public static final String CERTIFICATION_START_DATE = "資格交付日"; // Ngày cấp chứng chỉ
    public static final String CERTIFICATION_END_DATE = "失効日"; // Ngày hết hạn
    public static final String SCORE = "点数"; // Điểm số
    public static final String DATE_FORMAT_MESSAGE = "yyyy/MM/dd"; // Định dạng ngày

    // Thông điệp thành công
    public static final String MSG001 = "MSG001"; // Thêm nhân viên thành công
    public static final String MSG002 = "MSG002"; // Cập nhật nhân viên thành công
    public static final String MSG003 = "MSG003"; // Xóa nhân viên thành công

    // Tên tham số
    public static final String OFFSET = "オフセット"; // Tham số offset
    public static final String LIMIT = "リミット"; // Tham số limit
    public static final String ID_PARAMETER = "ID"; // Tham số ID

    /**
     * Phương thức trả về danh sách các tham số dưới dạng List.
     *
     * @param params Tham số đầu vào
     * @return Danh sách tham số
     */
    public static List<String> getParams(String... params) {
        return Arrays.asList(params);
    }
}
