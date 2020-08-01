package com.example.productsale.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    FileInfo store(MultipartFile file, String relativePath);

    FileInfo store(MultipartFile file, String relativePath, String prefix);

    Stream<Path> loadAll(String relativePath);

    Path load(String fileName);

    Path load(String fileName, String relativePath);

    Path load(String fileName, String relativePath, boolean ignoreNotExists);

    Resource loadAsResource(String fileName, String relativePath);

    void deleteAll(String relativePath);
}