package com.example.tdkprj1.controller;


import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.model.DocumentDto;
import com.example.tdkprj1.model.SaveRequestDto;
import com.example.tdkprj1.service.DocumentService;
import com.example.tdkprj1.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final JwtService jwtService;


    @PostMapping("document/save")
    public ResponseEntity<?> saveDocument(@RequestBody SaveRequestDto requestDto,HttpServletRequest request) {
        // ✅ JWT 쿠키에서 사용자 정보 추출
        TbUser tbUser = jwtService.getUserFromJwt(request)
                .orElseThrow(() -> new IllegalArgumentException("Unauthorized"));


        System.out.println("🔥 document/save 요청 성공! dto: "+ requestDto);
        documentService.saveDocument(requestDto, tbUser);
        return ResponseEntity.ok().build();
    }

    // ✅ 사용자의 문서 목록 조회
    @GetMapping("document/my")
    public ResponseEntity<List<DocumentDto>> getMyDocuments(HttpServletRequest request) {
        TbUser tbUser = jwtService.getUserFromJwt(request)
                .orElseThrow(() -> new IllegalArgumentException("Unauthorized"));

        List<DocumentDto> myDocuments = documentService.getMyDocuments(tbUser);
        return ResponseEntity.ok(myDocuments);
    }
    @GetMapping("/document/{id}")
    public ResponseEntity<Map<String, Object>> getDocumentData(@PathVariable Long id) {
        Map<String, Object> result = documentService.getDocumentFullData(id);
        return ResponseEntity.ok(result);
    }
}
