package org.example;

import org.example.pojo.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {

    // Getting all files that in project
    public static List<Path> getAllFiles(Path rootDir) {
        try (Stream<Path> walk = Files.walk(rootDir)) {
            return walk
                    .filter(Files::isRegularFile)
                    .toList();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // Creating List of objects(File) - fileName, fileSize
    public static List<File> createListOfFileObjects(List<Path> allFiles) {
        return allFiles.stream()
                .map(file -> {
                    try {
                        return File.builder()
                                .fileName(file.getFileName())
                                .fileSize((int) Files.size(file))
                                .build();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
    }

    // Getting all java files -> ends with .java extension
    // -> Argument all project files(As arg. using output from createListOfFileObjects method)
    public static List<Path> getOnlyJavaFiles(List<File> filesData) {
        return filesData
                .stream()
                .map(File::getFileName)
                .filter(path -> path.toString().endsWith(".java"))
                .map(Path::getFileName)
                .toList();
    }

    // Getting the largest files
    // -> Argument all project files(As arg. using output from createListOfFileObjects method)
    public static Optional<File> getTheLargestFile(List<File> filesData) {
        return filesData
                .stream()
                .max(Comparator.comparing(File::getFileSize));
    }

    // Getting the largest files
    // -> Argument all project files(As arg. using output from createListOfFileObjects method)
    public static Optional<File> getTheSmallestFile(List<File> filesData) {
        return filesData
                .stream()
                .min(Comparator.comparing(File::getFileSize));
    }

    // Getting the total size of all files in the project
    // -> Argument all project files(As arg. using output from createListOfFileObjects method)
    public static Optional<Integer> getProjectTotalSize(List<File> filesData) {
        return filesData
                .stream()
                .map(File::getFileSize)
                .reduce(Integer::sum);
    }

    public static void main(String[] args) {
        Path path = Path.of("/home/rennud/java/JavaStreamsTwo");
        List<Path> allFiles = getAllFiles(path);
        List<File> fileDataList = createListOfFileObjects(allFiles);

        System.out.println("Task #1");
        System.out.println("All .java files: " + getOnlyJavaFiles(fileDataList) + ".");
        System.out.println("---");
        System.out.println("Task #2");
        System.out.println("Largest file: " + getTheLargestFile(fileDataList));
        System.out.println("---");
        System.out.println("Task #3");
        System.out.println("Smallest file: " + getTheSmallestFile(fileDataList));
        System.out.println("---");
        System.out.println("Task #4");
        System.out.println("Total size of project: " + getProjectTotalSize(fileDataList) + " bytes.");
    }
}