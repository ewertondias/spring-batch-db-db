package com.springbatchdbdb.batch;

import com.springbatchdbdb.dto.UserDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<UserDTO, UserDTO> {

    @Override
    public UserDTO process(UserDTO userDTO) throws Exception {
        return userDTO;
    }

}
