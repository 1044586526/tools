package cn.ljh.upload.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @description: 文件断点续传工具类
 * @author ljh
 * @date 2024/1/23 16:31
 * @version 1.0
 */
public class UploadUtils {

    /**
     * 获取文件文件指纹
     * @param filePath 文件路径
     */
    public static String getFileSha256(Path filePath) throws Exception {
        try {
            // 创建SHA-256 MessageDigest实例
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 读取文件内容并计算哈希值
            digest.update(Files.readAllBytes(filePath));
            byte[] hashBytes = digest.digest();

            // 将哈希值转换为十六进制字符串
            return getString(hashBytes);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new Exception("Error generating SHA-256 hash: " + e.getMessage());
        }
    }

    /**
     * 获取指定范围文件指纹
     * @param filePath 文件路径
     * @param startPos 文件的起始位置
     * @param endPos 文件的结束位置
     * @throws Exception
     */
    public static String calculateChecksum(String filePath, long startPos, long endPos) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(filePath);

        byte[] buffer = new byte[8192];
        int bytesRead;
        long currentPosition = 0;

        // 定位到起始位置
        fis.skip(startPos);

        // 读取指定范围内的字节并更新哈希值
        while (currentPosition <= endPos && (bytesRead = fis.read(buffer)) != -1) {
            long remainingBytes = endPos - currentPosition + 1;
            int bytesToProcess = (int) Math.min(bytesRead, remainingBytes);

            // 更新哈希值
            digest.update(buffer, 0, bytesToProcess);
            currentPosition += bytesToProcess;
        }

        // 计算最终的哈希值
        byte[] checksum = digest.digest();

        // 关闭输入流
        fis.close();

        return getString(checksum);


    }

    /**
     * 字节转换
     * @param bytes 字节
     */
    private static String getString(byte[] bytes){
        // 将哈希值转换为十六进制字符串
        return IntStream.range(0, bytes.length)
                .mapToObj(i -> String.format("%02x", bytes[i] & 0xFF))
                .collect(Collectors.joining());
    }

    /**
     * 向文件指定位置插入内容
     *
     * @param filePath     源文件路径
     * @param pos          插入文件指定位置
     * @param writeContent 写入内容
     */
    public static void readFileThenWrite(Path filePath, long pos, byte[] writeContent) throws IOException {
        File tempFile = File.createTempFile("tmp", null);
        tempFile.deleteOnExit();

        try (RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "rw");
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             FileInputStream fileInputStream = new FileInputStream(tempFile)) {

            // 把文件记录指针定位到pos位置
            raf.seek(pos);

            //------------将插入点后的内容读入临时文件中保存------------
            byte[] bytes = new byte[1024];
            int hasRead;
            while ((hasRead = raf.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, hasRead);
            }

            //------------用于插入内容------------
            // 把文件记录指针重新定位到pos位置
            raf.seek(pos);
            raf.write(writeContent);

            // 追加临时文件中的内容
            while ((hasRead = fileInputStream.read(bytes)) != -1) {
                raf.write(bytes, 0, hasRead);
            }
        }
    }

    public static void main(String[] args) {
        try {
            String filePath = "D:\\company\\workspae\\liangyun\\ly-tools\\test_file\\test.txt";
            System.out.println(Files.size(Paths.get(filePath)));
            System.out.println(getFileSha256(Paths.get(filePath)));

            Path path = Paths.get(filePath);


//            Path tempPath = Paths.get("D:\\company\\workspae\\liangyun\\ly-tools\\test_file\\temp\\15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225$9.tmp");
//
//            readFileThenWrite(tempPath,Files.size(tempPath),(new String("ceshi")).getBytes());
//            System.out.println(calculateChecksum(filePath, 0, 37));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
