package org.example.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Getter
@Setter
@Builder
@ToString
public class FileObj {
    private Path fileName;
    private long fileSize;


    /**
     *
     * @param file => file from the project
     * @return FileObj => fileName, fileSize(In bytes)
     */

    // Should be here? Cuz Main::createFileObj vs FileObj::createFileObj in createListOfFileObjects
    // Ask how to handle exception correctly
    public static FileObj createFileObj(Path file) {
        try {
            return FileObj.builder()
                    .fileName(file.getFileName())
                    .fileSize(Files.size(file))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
