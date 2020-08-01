package com.example.productsale.service;

import com.example.productsale.dao.model.UserEntity;
import com.example.productsale.dao.repository.UserRepository;
import com.example.productsale.dto.LoginDTO;
import com.example.productsale.dto.UserDTO;
import com.example.productsale.exception.BaseException;
import com.example.productsale.msg.Msg;
import com.example.productsale.security.JwtTokenProvider;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;


@Service
public class UserServiceImpl extends AbstractBaseService<UserEntity, UserDTO> implements UserService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private ModelMapper modelMapper = null;

    @Autowired
    private UserRepository repository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<UserEntity> getEntityClass() {
        return UserEntity.class;
    }

    @Override
    protected Class<UserDTO> getDtoClass() {
        return UserDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return (Logger) logger;
    }

    @Override
    protected void specificMapToDTO(UserEntity entity, UserDTO dto) {
        super.specificMapToDTO(entity, dto);

    }

    @Override
    public UserDTO create(UserDTO dto) {
        UserEntity user = mapToEntity(dto);

        UserEntity user1 = repository.findFirstByUsername(user.getUsername());
        if (user1 != null){
            throw new BaseException(1, Msg.getMessage("Ton tai username"));
        }

        if (Objects.isNull(user.getPassword())){
            user.setPassword(getHashPassword("123456", user.getSalt()));
        }else {
            user.setPassword(getHashPassword(user.getPassword(), user.getSalt()));
        }
//        user.setCreatedName(getCreatedName());
        repository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        UserEntity user = repository.getById(id);
        UserEntity user1 = mapToEntity(dto);
        user.setName(user1.getName());
        user.setEmail(user1.getEmail());
        user.setGender(user1.getGender());
        repository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO delete(Long id) {
        UserEntity user = repository.getById(id);
        repository.delete(user);
        return mapToDTO(user);
    }

    @Override
    public UserDTO search(UserDTO dto) {
        UserEntity user = repository.search(dto);
        return mapToDTO(user);
    }

    @Override
    public Map<String, Object> login(LoginDTO loginDTO) {
        try {
            String username = loginDTO.getUsername();
            String password = loginDTO.getPassword();

            if (username == null && password == null){
                throw new BaseException(1, Msg.getMessage("Mời nhập dữ liệu"));
            }


            UserEntity user = repository.findFirstByUsername(username.trim());
            if (!user.getPassword().equals(getHashPassword(password, user.getSalt()))){
                throw new BaseException(2, Msg.getMessage("Password không đúng"));
            }
            Map<String, Object> addInfor = new HashMap<>();
            addInfor.put("gender", user.getGender());
            return jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getEmail(), addInfor);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void init() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        userId = jwtTokenProvider.getUserIdFromToken(token);
        logger.info("Token ...." +token);
        logger.info("User Id ... "+userId);

    }

    @Override
    public String getUsernameById(Long id) {
        return repository.getUsernameById(id);
    }

    private Long userId = null;


//    @Override
//    public String getUsernameById(Long id) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String token = request.getHeader("Authorization");
//        id = jwtTokenProvider.getUserIdFromToken(token);
//        return repository.getUsernameById(id);
//    }

    private String getHashPassword(String password, String salt) {
        if (salt == null) {
            salt = "";
        }

        return DigestUtils.sha256Hex(password + salt);
    }


    String getCreatedName(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        UserEntity user = repository.getById(userId);
        return user.getUsername();
    }
}
