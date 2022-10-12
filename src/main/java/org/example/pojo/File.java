package org.example.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Path;

@Getter
@Setter
@Builder
@ToString
public class File {
    private Path fileName;
    private int fileSize;
}
