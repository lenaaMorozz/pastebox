package com.mer.pastebox.service;

import com.mer.pastebox.api.request.PasteBoxRequest;
import com.mer.pastebox.api.response.PasteBoxResponse;
import com.mer.pastebox.api.response.PasteBoxUrlResponse;

import java.util.List;

public interface PasteBoxService {
    PasteBoxResponse getByHash(String hash);
    List<PasteBoxResponse> getFirstPublicPasteBoxes();
    PasteBoxUrlResponse create(PasteBoxRequest request);
}
