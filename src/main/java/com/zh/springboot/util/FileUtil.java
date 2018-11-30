package com.zh.springboot.util;

import org.junit.Test;

import java.io.*;
import java.util.Random;

public class FileUtil {

    @Test
    public void generatePic() throws Exception {
        // 图片的路径
        String picturePath = "G:\\6A19CA0B7DF1B2175748D8AC5B2F8495.jpg";

        // 文件保存的路径
        String fileFolderPath = "G:\\copyPicture\\116100502－第五期－38";

        // 生成图片数量
        Integer pictureNum = 38;

        copyPicture(picturePath, fileFolderPath, pictureNum);
    }


    /**
     *
     * @param picturePath  图片的路径
     * @param fileFolderPath  文件保存的路径
     * @param pictureNum 生成图片数量
     * @throws Exception
     */
    public void copyPicture(String picturePath, String fileFolderPath, Integer pictureNum) throws Exception {

        File sourceFile = new File(picturePath);

        File file = new File(fileFolderPath);
        if(!file.exists()) { // 文件夹不存在创建文件夹
            file.mkdirs();
            System.out.println("****************************************************");
            System.out.println(fileFolderPath + " :::: 创建成功");
            System.out.println("****************************************************");
        }

        // 生成图片的前缀名称
        String picBaseName = picturePath.substring(1, picturePath.length()-4);

        for (int i = 0; i < pictureNum; i++) {
            // 随机生成的名称
            String genegratedName = generatePicName(picBaseName);

            String absoluteNamePath = fileFolderPath + "\\" + genegratedName + ".jpg";

            File copyFile = new File(absoluteNamePath);

            copyFile(sourceFile, copyFile);
        }
        System.out.println("***************** 所有文件生成成功 *******************");
    }

    /**
     * 根据baseName生成图片名称
     * @param baseName
     */
    public String generatePicName(String baseName) {
        String fileName = getStringRandom(baseName.length());
        return fileName;
    }


    /**
     * 生成随机数字和字母,
     * @param length
     * @return
     */
    public String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }



    /**
     * 复制文件
     * 将source复制到dest
     */
    public void copyFile(File source, File dest) throws Exception{
        InputStream input = null;
        OutputStream output = null;
        input = new FileInputStream(source);
        output = new FileOutputStream(dest);
        byte[] buf = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buf)) > 0) {
            output.write(buf, 0, bytesRead);
        }
        input.close();
        output.close();
    }

}
