package pl.sztukakodu.bookaro.uploads.application.ports;

import lombok.Value;
import pl.sztukakodu.bookaro.uploads.domain.Upload;

import java.util.Optional;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);
    Optional<Upload> getById(String id);

    void removeById(String id);

    @Value
    class SaveUploadCommand {
        String fileName;
        byte[] file;
        String contentType;

    }
}
