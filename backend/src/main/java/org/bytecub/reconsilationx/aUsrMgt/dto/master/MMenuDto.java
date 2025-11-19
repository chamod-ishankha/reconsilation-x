package org.bytecub.reconsilationx.aUsrMgt.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MMenu;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MMenuDto implements Serializable {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private Long menuId;
    private String code;
    private String icon;
    private String path;

    private List<MMenuLabelDto> labels;
    private MMenuDto parent;
    private List<MMenuDto> children;

    // --- Helper methods ---

    // Convert Entity to DTO
    public static MMenuDto fromEntity(MMenu entity) {
        if (entity == null) return null;

        MMenuDto dto = new MMenuDto();
        dto.setMenuId(entity.getMenuId());
        dto.setCode(entity.getCode());
        dto.setIcon(entity.getIcon());
        dto.setPath(entity.getPath());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());

        // Convert labels
        if (entity.getLabels() != null) {
            dto.setLabels(
                entity.getLabels().stream()
                      .map(MMenuLabelDto::fromEntity)
                      .collect(Collectors.toList())
            );
        }

        // Convert parent safely (avoid deep recursion)
        if (entity.getParent() != null) {
            MMenuDto parentDto = new MMenuDto();
            parentDto.setMenuId(entity.getParent().getMenuId());
            parentDto.setCode(entity.getParent().getCode());
            dto.setParent(parentDto);
        }

        // Convert children
        if (entity.getChildren() != null) {
            dto.setChildren(
                entity.getChildren().stream()
                      .map(MMenuDto::fromEntity) // safe because parent won't recurse deeply
                      .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // Convert DTO to Entity
    public MMenu toEntity() {
        MMenu entity = new MMenu();
        entity.setMenuId(this.menuId);
        entity.setCode(this.code);
        entity.setIcon(this.icon);
        entity.setPath(this.path);

        // Convert labels
        if (this.labels != null) {
            entity.setLabels(
                this.labels.stream()
                           .map(MMenuLabelDto::toEntity)
                           .collect(Collectors.toList())
            );
            // set back-reference
            entity.getLabels().forEach(label -> label.setMenu(entity));
        }

        // Convert children
        if (this.children != null) {
            entity.setChildren(
                this.children.stream()
                             .map(MMenuDto::toEntity)
                             .collect(Collectors.toList())
            );
            // set parent reference
            entity.getChildren().forEach(child -> child.setParent(entity));
        }

        // Set parent reference safely
        if (this.parent != null) {
            MMenu parentEntity = new MMenu();
            parentEntity.setMenuId(this.parent.getMenuId());
            parentEntity.setCode(this.parent.getCode());
            entity.setParent(parentEntity);
        }

        return entity;
    }
}
