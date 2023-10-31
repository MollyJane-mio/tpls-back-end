package com.tpls.pojo;


import com.tpls.depModel.Dependency;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResponse {
    @Setter
    @Getter
    private int code;
    @Nullable
    private List<List<Dependency>> dependencyList;


}
