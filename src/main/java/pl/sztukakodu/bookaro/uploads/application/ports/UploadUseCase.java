package pl.sztukakodu.bookaro.uploads.application.ports;

import lombok.Value;
import pl.sztukakodu.bookaro.uploads.domain.Upload;

public interface UploadUseCase {

    Upload save(SaveUploadCommand command);

    @Value
    class SaveUploadCommand {
        String fileName;
        byte[] file;
        String contentType;

    }
}
