package com.springbatchdbdb.batch;

import com.springbatchdbdb.dto.UserDTO;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBWriter implements ItemWriter<UserDTO> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void write(List<? extends UserDTO> usersDTO) throws Exception {
        System.out.println("Save in database: " + usersDTO);

        jdbcTemplate.setDataSource(dataSource());

        String sql = "INSERT INTO user (id, name) VALUES (?, ?)";
        List<Object[]> batchArgsList = new ArrayList<>();
        usersDTO.forEach(userDTO -> {
            Object[] objArr = {
                userDTO.getId(),
                userDTO.getName()
            };

            batchArgsList.add(objArr);
        });

        jdbcTemplate.batchUpdate(sql, batchArgsList);
    }

    private DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("");
        dataSource.setUrl("");
        dataSource.setUsername("");
        dataSource.setPassword("");

        return dataSource;
    }

}
