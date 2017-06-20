package scis.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void init();

    String store(MultipartFile file) throws StorageException;

    void delete();

    void deleteAll();
}