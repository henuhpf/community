package code.community.service;

import code.community.mapper.UserMapper;
import code.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser != null){
            user.setId(dbUser.getId());
            userMapper.updateById(user);
        }else{
            userMapper.insert(user);
        }
    }
}
