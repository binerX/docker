package com.bin.yang.rest.api;

import com.oracle.jrockit.jfr.management.FlightRecordingMBean;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.*;
import java.util.Scanner;

/**
 * @ClassName: com.bin.yang.rest.api.FilePathRead
 * @Author: bin.yang
 * @Date: 2020/12/11 10:29
 * @Description: TODO
 */
public class FilePathRead {

//    public static void main(String[] args) throws IOException {
//        FileInputStream fileInputStream = null;
//        Scanner scanner = null;
//        String path = "/Users/bin.yang/Desktop/导入模板/codetext.txt";
//
//        try {
//            fileInputStream  = new FileInputStream(path);
//            scanner = new Scanner(fileInputStream,"GB2312");
//
//            while (scanner.hasNextLine()){
//
//                String s = scanner.nextLine();
//
//                System.out.println(s);
//
//            }
//
//            if(scanner.ioException() != null){
//                System.out.println("....读取异常");
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//
//            if (fileInputStream != null) {
//                fileInputStream.close();
//            }
//            if (scanner != null) {
//                scanner.close();
//            }
//
//        }
//
//    }

    public static void main(String[] args) throws IOException {

        LineIterator lineIterator = null;
        File file = new File("/Users/bin.yang/Desktop/导入模板/test.txt");
        if(file.exists()){
            file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        try {
            lineIterator = FileUtils.lineIterator(new File("/Users/bin.yang/Desktop/导入模板/codetext.txt"));
            while (lineIterator.hasNext()){

                String s = lineIterator.nextLine();
                System.out.println(s);
                bufferedWriter.write(s.toCharArray());
                bufferedWriter.newLine();

            }
            bufferedWriter.flush();
            lineIterator = FileUtils.lineIterator(new File("/Users/bin.yang/Desktop/导入模板/codetext.txt"));
            while (lineIterator.hasNext()){

                String s = lineIterator.nextLine();
                System.out.println(s);
                bufferedWriter.write(s.toCharArray());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            lineIterator = FileUtils.lineIterator(new File("/Users/bin.yang/Desktop/导入模板/codetext.txt"));
            while (lineIterator.hasNext()){

                String s = lineIterator.nextLine();
                System.out.println(s);
                bufferedWriter.write(s.toCharArray());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            lineIterator = FileUtils.lineIterator(new File("/Users/bin.yang/Desktop/导入模板/codetext.txt"));
            while (lineIterator.hasNext()){

                String s = lineIterator.nextLine();
                System.out.println(s);
                bufferedWriter.write(s.toCharArray());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            lineIterator = FileUtils.lineIterator(new File("/Users/bin.yang/Desktop/导入模板/codetext.txt"));
            while (lineIterator.hasNext()){

                String s = lineIterator.nextLine();
                System.out.println(s);
                bufferedWriter.write(s.toCharArray());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            lineIterator.close();
        }




    }


}
