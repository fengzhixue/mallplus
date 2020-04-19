package com.zscat.mallplus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zscat.mallplus.annotation.IgnoreAuth;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.entity.UmsMemberReceiveAddress;
import com.zscat.mallplus.ums.mapper.UmsMemberReceiveAddressMapper;
import com.zscat.mallplus.ums.service.IUmsMemberReceiveAddressService;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员收货地址管理Controller
 * https://github.com/shenzhuan/mallplus on 2018/8/28.
 */
@RestController
@Api(tags = "UmsMemberReceiveAddressController", description = "会员收货地址管理")
@RequestMapping("/api/address")
public class UmsMemberReceiveAddressController {
    @Autowired
    private IUmsMemberReceiveAddressService memberReceiveAddressService;

    @Resource
    private UmsMemberReceiveAddressMapper addressMapper;
    @Autowired
    private IUmsMemberService memberService;


    @ApiOperation("删除收货地址")
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        boolean count = memberReceiveAddressService.removeById(id);
        if (count) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @ApiOperation("修改收货地址")
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object update(UmsMemberReceiveAddress address) {
        boolean count = false;
        address.setMemberId(memberService.getNewCurrentMember().getId());
        if (address.getDefaultStatus() == 1) {
            addressMapper.updateStatusByMember(address.getMemberId());
        }
        if (address != null && address.getId() != null && address.getId() > 0) {
            count = memberReceiveAddressService.updateById(address);
        } else {
            count = memberReceiveAddressService.save(address);
        }
        if (count) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }

    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        UmsMember umsMember = memberService.getNewCurrentMember();
        if (umsMember != null && umsMember.getId() != null) {
            List<UmsMemberReceiveAddress> addressList = memberReceiveAddressService.list(new QueryWrapper<UmsMemberReceiveAddress>().eq("member_id", umsMember.getId()));
            return new CommonResult().success(addressList);
        }
        return new ArrayList<UmsMemberReceiveAddress>();
    }

    @IgnoreAuth
    @ApiOperation("显示所有收货地址")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object getItem(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getById(id);
        return new CommonResult().success(address);
    }

    @IgnoreAuth
    @ApiOperation("显示默认收货地址")
    @RequestMapping(value = "/getItemDefautl", method = RequestMethod.GET)
    @ResponseBody
    public Object getItemDefautl() {
        UmsMemberReceiveAddress address = memberReceiveAddressService.getDefaultItem();
        return new CommonResult().success(address);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("设为默认地址")
    @RequestMapping(value = "/address-set-default")
    @ResponseBody
    public Object setDefault(@RequestParam(value = "id", required = false, defaultValue = "0") Long id) {
        int count = memberReceiveAddressService.setDefault(id);
        if (count > 0) {
            return new CommonResult().success(count);
        }
        return new CommonResult().failed();
    }
}
