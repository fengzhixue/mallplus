package ${package}.service.impl;

import ${package}.entity.${className};
import ${package}.mapper.${className}Mapper;
import ${package}.service.I${className}Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
/**
* @author ${author}
* @date ${date}
*/
@Service
public class ${className}ServiceImpl extends ServiceImpl
<${className}Mapper, ${className}> implements I${className}Service {

@Resource
private  ${className}Mapper ${changeClassName}Mapper;


}
