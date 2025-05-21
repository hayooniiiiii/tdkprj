package com.example.tdkprj1.service;

import com.example.tdkprj1.entity.TbDocument;
import com.example.tdkprj1.entity.TbLeave;
import com.example.tdkprj1.entity.TbUser;
import com.example.tdkprj1.entity.TbVacation;
import com.example.tdkprj1.model.DocumentDto;
import com.example.tdkprj1.model.LeaveDto;
import com.example.tdkprj1.model.SaveRequestDto;
import com.example.tdkprj1.model.VacationDto;
import com.example.tdkprj1.repository.DocumentRepository;
import com.example.tdkprj1.repository.LeaveRepository;
import com.example.tdkprj1.repository.VacationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final VacationRepository vacationRepository;
    private final LeaveRepository leaveRepository;

    @Transactional
    public void saveDocument(SaveRequestDto requestDto, TbUser tbUser) {
        // 1. 문서 저장
        DocumentDto docDto = requestDto.getInformation();
        TbDocument document = new TbDocument();
        document.setDocmentDate(docDto.getDocumentDate());
        document.setDocumentTitle(docDto.getDocumentTitle());
        document.setDocumentOpen("공개".equals(docDto.getDocumentOpen()) ? 1L : 0L);
        document.setUser(tbUser);

        TbDocument savedDoc = documentRepository.save(document);

        // 2. 휴가(vacationDto) 저장
        List<VacationDto> vacations = requestDto.getVacationDto();  // ✅ 이건 프론트에서 바로 배열로 옴
        if (vacations != null) {
            for (VacationDto v : vacations) {
                TbVacation vacation = new TbVacation();
                vacation.setDocument(savedDoc);
                vacation.setVacationStartday(v.getVacationStartday());
                vacation.setVacationEndday(v.getVacationEndday());
                vacation.setVacationStarttime(v.getVacationStarttime());
                vacation.setVacationEndtime(v.getVacationEndtime());
                vacation.setVacationUse(v.getVacationUse());
                vacation.setVacationContent(v.getVacationContent());
                vacation.setVacationType(v.getVacationType());
                vacationRepository.save(vacation);
            }
        }

        // 3. 연차(LeaveDto) update 방식으로 처리
        LeaveDto leaveDto = requestDto.getUseYear();
        if (leaveDto != null) {
            Optional<TbLeave> leaveOpt = leaveRepository.findByUserId(tbUser.getId());
            if (leaveOpt.isPresent()) {
                TbLeave leave = leaveOpt.get();
                leave.setDocument(savedDoc);  // ✅ 연동
                leaveRepository.save(leave);
            }
        }
    }

    @Transactional
    public List<DocumentDto> getMyDocuments(TbUser tbUser) {
        List<TbDocument> documents = documentRepository.findByUserId(tbUser.getId());

        return documents.stream().map(doc -> {
            DocumentDto dto = new DocumentDto();
            dto.setId(doc.getId());
            dto.setDocumentDate(doc.getDocmentDate());
            dto.setDocumentTitle(doc.getDocumentTitle());
            dto.setDocumentOpen(doc.getDocumentOpen() == 1L ? "1" : "0"); // 프론트에선 "1" 또는 "0" 문자열로 처리
            return dto;
        }).collect(Collectors.toList());
    }

    public Map<String, Object> getDocumentFullData(Long documentId) {
        TbDocument document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("문서를 찾을 수 없습니다."));

        List<TbVacation> vacations = vacationRepository.findByDocumentId(documentId);
        Optional<TbLeave> leave = leaveRepository.findByDocumentId(documentId);

        // 👉 DTO 변환
        DocumentDto documentDto = new DocumentDto(
                document.getId(),
                document.getDocmentDate(),
                document.getDocumentTitle(),
                document.getDocumentOpen() == 1L ? "1" : "0"
        );

        List<VacationDto> vacationDtos = vacations.stream()
                .map(v -> new VacationDto(
                        v.getId(),
                        document.getId(),
                        v.getVacationStartday(),
                        v.getVacationEndday(),
                        v.getVacationStarttime(),
                        v.getVacationEndtime(),
                        v.getVacationUse(),
                        v.getVacationContent(),
                        v.getVacationType()
                )).collect(Collectors.toList());

        LeaveDto leaveDto = leave.map(l -> new LeaveDto(
                l.getId(),
                l.getLeaveIwol(),
                l.getLeaveUsemonth(),
                l.getLeaveMonth(),
                l.getLeaveYear(),
                l.getLeaveUseday(),
                l.getUser().getId(),
                l.getDocument().getId().intValue() // 형 변환 필요시
        )).orElse(null);

        Map<String, Object> response = new HashMap<>();
        response.put("document", documentDto);
        response.put("vacations", vacationDtos);
        response.put("leave", leaveDto);

        return response;
    }


}
