package practice.cn.bn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import practice.cn.bn.service.DataBeanService;
import practice.cn.bn.service.TestBeanService;

/**
 * @description:
 * @version: 1.0
 * 2016/10/25
 */
@Service
public class TestBeanServiceImpl implements TestBeanService {
    @Autowired
    private DataBeanService dataBeanService;
}
