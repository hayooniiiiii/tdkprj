package com.example.tdkprj1.model;

import com.example.tdkprj1.entity.TbDocument;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link TbDocument}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentDto  {
    Long id;
    LocalDate documentDate;
    String documentTitle;
    String documentOpen;
}