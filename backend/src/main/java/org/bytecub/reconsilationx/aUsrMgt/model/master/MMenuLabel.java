package org.bytecub.reconsilationx.aUsrMgt.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.MENU_LABEL_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = MENU_LABEL_TABLE, indexes = {
        @Index(name = "idx_menu_label_menu_id", columnList = "MENU_ID"),
})
public class MMenuLabel extends AuditModel {

    @Id
    @SequenceGenerator(name = MENU_LABEL_TABLE, allocationSize = 1, sequenceName = MENU_LABEL_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = MENU_LABEL_TABLE)
    @Column(name = "MENU_LABEL_ID", nullable = false)
    private Long menuLabelId;

    // Language code like "zh_CN" or "en_US"
    @Column(name = "LANG_CODE", length = 10, nullable = false)
    private String langCode;

    @Column(name = "LABEL", length = 100, nullable = false)
    private String label;

    // Reference to the parent menu
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MENU_ID", referencedColumnName = "MENU_ID", nullable = false)
    private MMenu menu;
}
