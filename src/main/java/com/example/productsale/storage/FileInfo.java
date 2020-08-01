package com.example.productsale.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    private String fileName;
    private String relativePath;
    private String absolutePath;
}