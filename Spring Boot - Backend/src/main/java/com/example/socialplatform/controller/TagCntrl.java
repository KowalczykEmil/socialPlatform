package com.example.socialplatform.controller;

import com.example.socialplatform.datatransferobject.TagDataTransferObject;
import com.example.socialplatform.service.TagService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
@Slf4j
public class TagCntrl {

    private final TagService tagService;

    @PostMapping("/tagi")
    public ResponseEntity<TagDataTransferObject> createTag(@RequestBody TagDataTransferObject tagDataTransferObject) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.save(tagDataTransferObject));
    }

    @GetMapping("/tagi")
    public ResponseEntity<List<TagDataTransferObject>> getAllTags() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDataTransferObject> getTag(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tagService.getTag(id));
    }
}
