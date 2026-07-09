package api.poja.app.file.s3;

import java.io.File;
import java.io.IOException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class S3Service {

  private final S3Client s3Client;
  private final String bucketName;

  public S3Service(S3Client s3Client, @Value("${aws.s3.bucket}") String bucketName) {
    this.s3Client = s3Client;
    this.bucketName = bucketName;
  }

  @SneakyThrows
  public File download(String key) {
    var tempFile = java.nio.file.Files.createTempFile("s3-", "-" + key.replaceAll("/", "_"));
    s3Client.getObject(req -> req.bucket(bucketName).key(key), tempFile);
    return tempFile.toFile();
  }
}
