package com.mer.pastebox.controller;

import com.mer.pastebox.api.request.PasteBoxRequest;
import com.mer.pastebox.api.response.PasteBoxResponse;
import com.mer.pastebox.api.response.PasteBoxUrlResponse;
import com.mer.pastebox.service.PasteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasteBoxController {

    private final PasteBoxService pasteBoxService;

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash) {
        return pasteBoxService.getByHash(hash);
    }

    @GetMapping("/")
    public List<PasteBoxResponse> getPublicPasteList() {
        return pasteBoxService.getFirstPublicPasteBoxes();
    }

    @PostMapping("/")
    public PasteBoxUrlResponse add(@RequestBody PasteBoxRequest request) {
        return pasteBoxService.create(request);
    }
}
