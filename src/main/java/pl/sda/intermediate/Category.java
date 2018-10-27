package pl.sda.intermediate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Category {
    private Integer id; // Integer a nie "int", aby nowo utworzony obiekt nie miał wartości "zero", ale null
    private Integer parentId;
    private Integer depth;
    private String name;
}
