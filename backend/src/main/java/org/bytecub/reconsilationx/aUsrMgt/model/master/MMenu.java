package org.bytecub.reconsilationx.aUsrMgt.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.reconsilationx.aUsrMgt.config.audit.AuditModel;

import java.util.List;

import static org.bytecub.reconsilationx.aUsrMgt.constants.TableNames.MENU_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = MENU_TABLE, indexes = {
        @Index(name = "idx_menu_code", columnList = "CODE"),
})
public class MMenu extends AuditModel {

    @Id
    @SequenceGenerator(name = MENU_TABLE, allocationSize = 1, sequenceName = MENU_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = MENU_TABLE)
    @Column(name = "MENU_ID", nullable = false)
    private Long menuId;

    @Column(name = "CODE", length = 50, nullable = false, unique = true)
    private String code;
    @Column(name = "ICON", length = 100)
    private String icon;
    @Column(name = "PATH", length = 255)
    private String path;

     // One menu can have multiple labels (different languages)
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MMenuLabel> labels;

    // Self-referencing relationship for children menus
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MMenu parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MMenu> children;

}
