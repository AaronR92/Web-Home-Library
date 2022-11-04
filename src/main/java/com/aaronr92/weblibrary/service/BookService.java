package com.aaronr92.weblibrary.service;

import com.aaronr92.weblibrary.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public void saveFile(MultipartFile file) {
        File dir = new File("A:\\Files");
        String filename = (dir.listFiles().length + 1) + "." +
                (file.getOriginalFilename().split("\\.")[1]);
        Path path = Paths.get(dir + "\\" + filename);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
