interface MenuItem {
  code: string;
  labels: Record<string, string>; // dynamic keys
  icon?: string;
  path: string;
  children?: MenuItem[];
}

type MenuList = MenuItem[];

// Mapper function
export function mapMenu(dto: any[]): MenuList {
  return dto.map(item => {
    const labelsObj: Record<string, string> = {};

    if (item.labels) {
      item.labels.forEach((label: any) => {
        if (label.langCode && label.label) {
          labelsObj[label.langCode] = label.label;
        }
      });
    }

    // Recursively map children
    const children = item.children && item.children.length > 0 ? mapMenu(item.children) : undefined;

    return {
      code: item.code,
      labels: labelsObj,
      icon: item.icon || undefined,
      path: item.path,
      children
    };
  });
}

export type { MenuItem, MenuList };
