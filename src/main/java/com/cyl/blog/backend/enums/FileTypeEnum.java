package com.cyl.blog.backend.enums;

/**
 * Created by youliang.cheng on 2018/9/21.
 */
public enum FileTypeEnum {
        TXT("TXT","txt"),
        TEXT("TEXT", "txt"),
        SQL("SQL", "txt" ),
        PNG("PNG", "image"),
        JPG("JPG", "image"),
        JPEG("JPEG", "image"),
        GIF("GIF", "image"),
        BMP("BMP", "image"),
        DOC("DOC", "word"),
        DOCX("DOCX", "word"),
        XLS("XLS", "xls"),
        XLSX("XLSX", "xls"),
        RAR("RAR", "rar"),
        ZIP("ZIP", "zip"),
        QIZ("7Z", "7z"),
        TARGZ("GZ", "gz"),
        GZ("GZ", "gz");

    FileTypeEnum(String extName, String suffix) {
        this.extName = extName;
        this.suffix = suffix;
    }

    private String extName;
    private String suffix;

    public String getExtName() {
        return extName;
    }

    public String getSuffix() {
        return suffix;
    }

    public static String getSuffix(String extName) {

        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if(fileTypeEnum.getExtName().equalsIgnoreCase(extName)) {
                return fileTypeEnum.getSuffix();
            }
        }
        return "image";
    }

}
