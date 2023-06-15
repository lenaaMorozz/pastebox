package com.mer.pastebox;

import com.mer.pastebox.api.response.PasteBoxResponse;
import com.mer.pastebox.exception.NotFoundEntityException;
import com.mer.pastebox.repository.PasteBoxEntity;
import com.mer.pastebox.repository.PasteBoxRepository;
import com.mer.pastebox.service.PasteBoxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteBoxServiceTest {

    @Autowired
    private PasteBoxService pasteBoxService;
    @MockBean
    private PasteBoxRepository pasteBoxRepository;

    @Test
    public void notExistHash() {
        when(pasteBoxRepository.getByHash(anyString())).thenThrow(NotFoundEntityException.class);
        assertThrows(NotFoundEntityException.class, () ->
                pasteBoxService.getByHash("eellaaallskdkd"));
    }

    @Test
    public void getExistHash() {
        PasteBoxEntity entity = new PasteBoxEntity();
        entity.setHash("1");
        entity.setData("data");
        entity.setPublic(true);
        when(pasteBoxRepository.getByHash("1")).thenReturn(entity);

        PasteBoxResponse expected = new PasteBoxResponse("data", true);
        PasteBoxResponse actual = pasteBoxService.getByHash("1");

        assertEquals(expected, actual);
    }
}
