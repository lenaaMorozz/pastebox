package com.mer.pastebox.service;

import com.mer.pastebox.api.request.PasteBoxRequest;
import com.mer.pastebox.api.request.PublicStatus;
import com.mer.pastebox.api.response.PasteBoxResponse;
import com.mer.pastebox.api.response.PasteBoxUrlResponse;
import com.mer.pastebox.repository.PasteBoxEntity;
import com.mer.pastebox.repository.PasteBoxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasteBoxServiceImpl implements PasteBoxService {

    private final String host = "http://pastebox.ru";
    private final PasteBoxRepository repository;
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBoxes() {
        int publicListSize = 10;
        List<PasteBoxEntity> list = repository.getListOfPublicAndALive(publicListSize);
        return list.stream().map(pasteBoxEntity ->
            new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBoxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteBoxEntity);
        log.info("Add paste {}", hash);
        return new PasteBoxUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
