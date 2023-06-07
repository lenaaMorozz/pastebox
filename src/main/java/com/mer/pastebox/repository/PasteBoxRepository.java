package com.mer.pastebox.repository;

import java.util.List;

public interface PasteBoxRepository {
    PasteBoxEntity getByHash(String hash);
    List<PasteBoxEntity> getListOfPublicAndALive(int amount);
    void add(PasteBoxEntity pasteBoxEntity);
}
