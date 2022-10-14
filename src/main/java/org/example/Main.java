package org.example;

import org.example.pojo.FileObj;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {


    /**
     *
     * @param rootDir => root dir of the project
     * @return all files that project contain
     */
    public static List<Path> getAllFiles(Path rootDir) {
        try (Stream<Path> walk = Files.walk(rootDir)) {
            return walk
                    .filter(Files::isRegularFile)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param allFiles => List of all files that project contain
     * @return List of FileObj
     */
    public static List<FileObj> createListOfFileObjects(List<Path> allFiles) {
        return allFiles.stream()
                .map(FileObj::createFileObj)
                .toList();
    }

    /**
     *
     * @param filesData => List of FileObj(output from createListOfFileObjects method)
     * @return List of java files => ends with .java extension
     */
    public static List<Path> getOnlyJavaFiles(List<FileObj> filesData) {
        return filesData
                .stream()
                .map(FileObj::getFileName)
                .filter(path -> path.toString().endsWith(".java"))
                .map(Path::getFileName)
                .toList();
    }

    /**
     *
     * @param filesData - List of FileObj(output from createListOfFileObjects method)
     * @return The largest file in the project
     */
    public static Optional<FileObj> getTheLargestFile(List<FileObj> filesData) {
        return filesData
                .stream()
                .max(Comparator.comparing(FileObj::getFileSize));
    }

//    public static OptionalLong getTheLargestFile(List<FileObj> filesData) {
//        return filesData
//                .stream()
//                .mapToLong(FileObj::getFileSize)
//                .min();
//    }

    /**
     *
     * @param filesData - List of FileObj(output from createListOfFileObjects method)
     * @return The smallest file in the project
     */
    public static Optional<FileObj> getTheSmallestFile(List<FileObj> filesData) {
        return filesData
                .stream()
                .min(Comparator.comparing(FileObj::getFileSize));
    }

//    public static OptionalLong getTheSmallestFile(List<FileObj> filesData) {
//        return filesData
//                .stream()
//                .mapToLong(FileObj::getFileSize)
//                .min();
//    }

    /**
     *
     * @param filesData - List of FileObj(output from createListOfFileObjects method)
     * @return Total size of the project in bytes
     */
    public static long getProjectTotalSize(List<FileObj> filesData) {
        return filesData
                .stream()
                .mapToLong(FileObj::getFileSize)
                .sum();
    }

    public static void main(String[] args) {
        Path path = Path.of(".");
        List<Path> allFiles = getAllFiles(path);
        List<FileObj> fileObjDataList = createListOfFileObjects(allFiles);
        System.out.println("Task #1");
        System.out.println("All .java files: " + getOnlyJavaFiles(fileObjDataList) + ".");
        System.out.println("---");
        System.out.println("Task #2");
        System.out.println("Largest file: " + getTheLargestFile(fileObjDataList));
        System.out.println("---");
        System.out.println("Task #3");
        System.out.println("Smallest file: " + getTheSmallestFile(fileObjDataList));
        System.out.println("---");
        System.out.println("Task #4");
        System.out.println("Total size of project: " + getProjectTotalSize(fileObjDataList) + " bytes.");
    }
}