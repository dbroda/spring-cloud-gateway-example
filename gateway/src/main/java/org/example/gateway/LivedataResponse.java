package org.example.gateway;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
class LivedataResponse {

    List<LivedataGameHead> gameHeads;

}
