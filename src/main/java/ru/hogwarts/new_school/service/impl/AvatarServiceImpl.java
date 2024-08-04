package ru.hogwarts.new_school.service.impl;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.new_school.model.Avatar;
import ru.hogwarts.new_school.model.Student;
import ru.hogwarts.new_school.repository.AvatarRepository;
import ru.hogwarts.new_school.service.AvatarService;
import ru.hogwarts.new_school.service.StudentService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("avatars")
    private String avatarsDir;

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile file)  {
        logger.info("Uploading avatar for student with id {} (filename: {})", studentId, file.getOriginalFilename());

        Student student = studentService.findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        try {
            Files.createDirectories(filePath.getParent());
        } catch (IOException exception) {
            logger.error("Unable to create directory for avatar file {}", file.getOriginalFilename());
            throw new RuntimeException(exception);
        }
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException exception) {
            logger.error("Unable to delete file {}", file.getOriginalFilename());
            throw new RuntimeException(exception);
        }

        try (InputStream is = file.getInputStream();
            OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
            BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
            BufferedInputStream bis = new BufferedInputStream(is, 1024)
        ) {
            bis.transferTo(bos);
        } catch (IOException exception) {
            logger.error("Error while uploading avatar");
            throw new RuntimeException(exception);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateAvatarDataForDB(filePath));

        logger.info("Avatar uploaded successfully");

        avatarRepository.save(avatar);
    }

    private byte[] generateAvatarDataForDB(Path filePath) {
        logger.info("Generating avatar data for file {}", filePath.getFileName().toString());

        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            BufferedImage image = ImageIO.read(bis);
            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics2D = preview.createGraphics();
            graphics2D.drawImage(image, 0, 0, 100, height, null);
            graphics2D.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);

            logger.info("Generated avatar data for file {}", filePath.getFileName().toString());
            return baos.toByteArray();

        } catch (IOException exception) {
            logger.error("Error while generating avatar");
            throw new RuntimeException(exception);
        }
    }

    private String getExtension(String filename) {
        logger.info("Getting extension for file {}", filename);

        return filename.substring(filename.lastIndexOf(".") + 1);
    }



    public Avatar findAvatar(Long studentId) {
        logger.info("Finding avatar for student with id {}", studentId);
        logger.warn("Avatar for student with id {} may not be found", studentId);

        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public List<Avatar> getAvatarsWithPagination(int page, int size) {
        logger.info("Getting avatars for page {} and size {}", page, size);

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}
