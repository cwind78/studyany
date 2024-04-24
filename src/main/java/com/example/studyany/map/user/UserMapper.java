package com.example.studyany.map.user;

import com.example.studyany.vo.user.LoginVo;
import com.example.studyany.vo.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "userId", source = "id")
    @Mapping(target = "userPwd", source = "pwd")
    @Mapping(target = "userName", ignore = true)
    @Mapping(target = "userBirth", ignore = true)
    @Mapping(target = "userPhone", ignore = true)
    User transLoginVoToUser(LoginVo vo);
}
