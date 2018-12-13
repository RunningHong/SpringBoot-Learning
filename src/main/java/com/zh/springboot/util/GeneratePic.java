package com.zh.springboot.util;

import org.junit.Test;

import java.io.*;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 根据指路径生成复制文件
 */
public class GeneratePic {

    /**
     * 复制图片并压缩入口类
     * @author RunningHong at 2018/12/13 10:53
     * @param
     * @return
     */
    @Test
    public void generatePic() throws Exception {
        // 模板文件路径
        String tempPicPath = "G:\\copyPicture\\templatePic";

        // 文件保存的路径
        String saveFolderpath = "G:\\copyPicture\\116100502－第八期－38";

        // 生成图片数量
        Integer pictureNum = 38;

        // 生成文件
        distributionGenerate(tempPicPath, saveFolderpath, pictureNum);

        // 压缩文件为zip
        String zipPath = "G:\\copyPicture\\116100502.zip";
        OutputStream zipOS = new FileOutputStream(new File(zipPath));
        toZip(saveFolderpath, zipOS, true);
        System.out.println("压缩文件成功");
    }


    /**
     * 根据指定目录，读取目录下的所有文件，分配生成指定数量的文件
     * @author RunningHong at 2018/12/13 10:17
     * @param tempPicPath 模板文件的路径
     * @param saveFolderpath 文件保存路径
     * @param totalPicNum 生成总数
     * @return void
     */
    public void distributionGenerate(String tempPicPath, String saveFolderpath, Integer totalPicNum) throws Exception{
        File file = new File(tempPicPath);
        File[] fileList = file.listFiles();

        // 前面的取平均数
        Integer normalNum = totalPicNum/fileList.length;

        // 最后一个取总数-前面所有的和
        Integer lastNum = totalPicNum - (fileList.length-1)*normalNum;

        // 遍历文件平均生成文件
        for (int i = 0; i < fileList.length-1; i++) {
            copyPicture(fileList[i].getAbsolutePath(), saveFolderpath, normalNum);
            System.out.printf("复制%s文件个数%d \n", fileList[i].getAbsolutePath(), normalNum);
        }

        // 处理最后一个文件的复制
        copyPicture(fileList[fileList.length-1].getAbsolutePath(), saveFolderpath, lastNum);
        System.out.printf("复制%s文件个数%d \n", fileList[fileList.length-1].getAbsolutePath(), lastNum);


        System.out.println("***************** 所有文件生成成功 *******************");
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
            System.out.println("文件目录：：：：" + fileFolderPath + " :::: 创建成功");
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

    /**
     * 压缩成ZIP 方法1
     * @param srcDir 压缩文件夹路径
     * @param out    压缩文件输出流
     * @param KeepDirStructure  是否保留原来的目录结构
     *                          true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException{
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean KeepDirStructure) throws Exception{
        byte[] buf = new byte[1024];
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if(listFiles == null || listFiles.length == 0){
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if(KeepDirStructure){
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            }else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }

}
