package com.springbatchdbdb.batch;

import com.springbatchdbdb.dto.UserDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDTO> {

    @Override
    public UserDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(resultSet.getInt("id"));
        userDTO.setName(resultSet.getString("name"));
        return userDTO;
    }

}
