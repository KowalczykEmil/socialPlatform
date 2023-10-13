package com.example.socialplatform.service;

import com.example.socialplatform.datatransferobject.TagDataTransferObject;
import com.example.socialplatform.exception.SocialPlatformException;
import com.example.socialplatform.mapper.TagMapper;
import com.example.socialplatform.repository.TagRepository;
import com.example.socialplatform.schema.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Transactional
    public TagDataTransferObject save(TagDataTransferObject tagDataTransferObject) {
        Tag save = tagRepository.save(tagMapper.mapDtoToTag(tagDataTransferObject));
        tagDataTransferObject.setId(save.getId());
        return tagDataTransferObject;
    }

    @Transactional(readOnly = true)
    public List<TagDataTransferObject> getAll() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::mapTagToDto)
                .collect(toList());
    }

    public TagDataTransferObject getTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new SocialPlatformException("Nie znaleziono takiego tag'u: " + id));
        return tagMapper.mapTagToDto(tag);
    }
}
