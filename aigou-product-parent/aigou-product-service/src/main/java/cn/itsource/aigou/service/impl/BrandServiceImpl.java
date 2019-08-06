package cn.itsource.aigou.service.impl;

import cn.itsource.aigou.domain.Brand;
import cn.itsource.aigou.mapper.BrandMapper;
import cn.itsource.aigou.query.BrandQuery;
import cn.itsource.aigou.service.IBrandService;
import cn.itsource.util.PageList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 * @author yhh
 * @since 2019-07-30
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public PageList<Brand> selectByQuery(BrandQuery query) {
        Page<Brand> page = new Page<>(query.getPageNum(),query.getPageSize());
        IPage<Brand> iPage = baseMapper.selectByQuery(page, query);
        return new PageList(iPage.getTotal(),iPage.getRecords());
    }
}
