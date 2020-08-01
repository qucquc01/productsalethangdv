package com.example.productsale.storage;

import com.example.productsale.exception.StorageException;
import com.example.productsale.msg.Msg;
import com.example.productsale.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final static Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);

    private final Path rootLocation;

    public FileSystemStorageService(@Value("${app.storage.location:./files}") String rootLocationStr) {
        rootLocation = Paths.get(rootLocationStr);
    }

    @Override
    public FileInfo store(MultipartFile file, String relativePath) {
        return store(file, relativePath, null);
    }

    @Override
    public FileInfo store(MultipartFile file, String relativePath, String prefix) {
        if (file.isEmpty()) {
            throw new StorageException.InvalidMultipartFile(Msg.getMessage("multipart.file.null"));
        }

        String fileName = file.getOriginalFilename();

        logger.debug("Client upload file: " + fileName);

        if (fileName.contains("..")) {
            throw new StorageException.InvalidMultipartFile(Msg.getMessage("file.name.wrong", new Object[] {fileName}));
        }

        fileName = StringUtils.cleanPath(fileName);
        fileName = (prefix == null ? "" : prefix) + FileUtil.generateFileName(fileName);

        relativePath = (relativePath == null ? "" : relativePath + File.separator) + fileName;
        try {
            Path parentFolder = rootLocation.resolve(relativePath);

            if (!Files.exists(parentFolder)) {
                Files.createDirectories(parentFolder);
            }

            Path filePath = rootLocation.resolve(relativePath);

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                String absolutePath = filePath.toAbsolutePath().toString();

                logger.debug("Save file to: " + absolutePath);
                return new FileInfo(fileName, relativePath, absolutePath);
            }
        } catch (IOException e) {
            throw new StorageException.StoreFileException(Msg.getMessage("failed.to.store.file", new Object[] {fileName}), e);
        }
    }

    @Override
    public Stream<Path> loadAll(String relativePath) {
        Path location;
        if (relativePath == null || relativePath.isEmpty()) {
            location = rootLocation;
        } else {
            location = rootLocation.resolve(relativePath);
        }

        try {
            return Files.walk(location, 1)
                    .filter(path -> !path.equals(location))
                    .map(location::relativize);
        } catch (IOException e) {
            throw new StorageException.StoreFileException(Msg.getMessage("failed.to.read.file", new Object[] {relativePath}), e);
        }

    }

    @Override
    public Path load(String fileName) {
        return load(fileName, null);
    }

    @Override
    public Path load(String fileName, String relativePath) {
        return load(fileName, relativePath, false);
    }

    @Override
    public Path load(String fileName, String relativePath, boolean ignoreNotExists) {
        if (fileName == null) {
            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {null}));
        }

        Path location;
        if (relativePath == null || relativePath.isEmpty()) {
            location = rootLocation;
        } else {
            location = rootLocation.resolve(relativePath);
        }

        location = location.resolve(fileName);

        if (Files.notExists(location) && !ignoreNotExists) {
            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {fileName}));
        }

        return location;
    }

    @Override
    public Resource loadAsResource(String fileName, String relativePath) {
        try {
            Path file = load(fileName, relativePath);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {fileName}));
            }
        } catch (MalformedURLException e) {
            throw new StorageException.FileNotFound(Msg.getMessage("failed.to.read.file", new Object[] {fileName}), e);
        }
    }

    @Override
    public void deleteAll(String relativePath) {
        Path location;
        if (relativePath == null || relativePath.isEmpty()) {
            location = rootLocation;
        } else {
            location = rootLocation.resolve(relativePath);
        }

        FileSystemUtils.deleteRecursively(location.toFile());
    }
}