package com.mer.pastebox.repository;

import com.mer.pastebox.exception.NotFoundEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class PasteBoxRepositoryMap implements PasteBoxRepository {

    private final Map<String, PasteBoxEntity> vault = new ConcurrentHashMap<>();

    @Override
    public PasteBoxEntity getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = vault.get(hash);

        if (pasteBoxEntity == null) {
            log.error("Pastebox not found with hash {} ", hash);
            throw new NotFoundEntityException("Pastebox not found with hash " + hash);
        }
        return vault.get(hash);
    }

    @Override
    public List<PasteBoxEntity> getListOfPublicAndALive(int amount) {
        LocalDateTime now = LocalDateTime.now();
        return vault.values().stream()
                .filter(PasteBoxEntity::isPublic)
                .filter(pasteBoxEntity -> pasteBoxEntity.getLifetime().isAfter(now))
                .sorted(Comparator.comparing(PasteBoxEntity::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());
    }

    @Override
    public void add(PasteBoxEntity pasteBoxEntity) {
        vault.put(pasteBoxEntity.getHash(), pasteBoxEntity);
    }
}
