package ru.hogwarts.new_school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.new_school.model.Avatar;
import ru.hogwarts.new_school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long studentId,
                                               @RequestParam("file") MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big!");
        }

        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok("Avatar uploaded!");
    }

    @GetMapping("{id}/avatar/preview")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.ok().headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
        OutputStream os = response.getOutputStream()) {

            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());

            is.transferTo(os);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAvatarsWithPagination(
            @RequestParam Integer page,
            @RequestParam Integer size) throws IOException {

        List<Avatar> avatars = avatarService.getAvatarsWithPagination(page, size);

        if (avatars.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ResponseEntity<byte[]>> responses = new ArrayList<>();

        for (Avatar avatar : avatars) {
            Path path = Path.of(avatar.getFilePath());

            if (Files.exists(path)) {
                byte[] content = Files.readAllBytes(path);

                responses.add(ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(avatar.getMediaType()))
                        .contentLength(avatar.getFileSize())
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName().toString() + "\"")
                        .body(content));
            }

        }

        return ResponseEntity.ok(responses);
    }

}
