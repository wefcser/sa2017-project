package scis.storage;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemStorageService implements StorageService {
    private Path rootLocation = null;

    @Override
    public String store(MultipartFile file) throws StorageException {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename() + ".");
            }
            if(rootLocation == null)this.init();
            Files.deleteIfExists(rootLocation.resolve("students.xlsx"));
            Files.copy(file.getInputStream(),
                    rootLocation.resolve("students.xlsx"));
            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename() + ".", e);
        }
    }

    @Override
    public void init() {
        try {
            System.out.println("-------------------------here");
            rootLocation = Paths.get("Storage");
            if(Files.notExists(rootLocation)) {
                Files.createDirectory(rootLocation);
            }
        } catch (IOException e) {
            throw new StorageException("Could not initialize Storage.", e);
        }
    }

    public void delete() {
        File directory = rootLocation.toFile();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (!file.delete()) {
                System.out.println("Failed to delete " + file);
            }
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}