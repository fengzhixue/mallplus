package com.zscat.mallplus.sms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.sms.entity.SmsGroup;
import com.zscat.mallplus.sms.entity.SmsGroupMember;
import com.zscat.mallplus.sms.entity.SmsGroupRecord;
import com.zscat.mallplus.sms.mapper.SmsGroupMemberMapper;
import com.zscat.mallplus.sms.mapper.SmsGroupRecordMapper;
import com.zscat.mallplus.sms.service.ISmsGroupService;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "SmsGroupController", description = "管理")
@RequestMapping("/sms/SmsGroup")
public class SmsGroupController {
    @Resource
    private SmsGroupRecordMapper groupRecordMapper;
    @Resource
    private ISmsGroupService ISmsGroupService;
    @Resource
    private IUmsMemberService memberService;
    @Resource
    private SmsGroupMemberMapper groupMemberMapper;

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('sms:SmsGroup:read')")
    public Object getSmsGroupByPage(SmsGroup entity,
                                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(ISmsGroupService.page(new Page<SmsGroup>(pageNum, pageSize), new QueryWrapper<>(entity)));

        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "根据条件查询所有列表")
    @ApiOperation("根据条件查询所有列表")
    @GetMapping(value = "/listGroupMember")
    public Object listGroupMember(SmsGroupRecord entity,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            List<SmsGroupRecord> groupRecords = groupRecordMapper.selectList(new QueryWrapper<SmsGroupRecord>().eq("group_id", entity.getGroupId()));
            for (SmsGroupRecord groupRecord : groupRecords) {
                List<SmsGroupMember> groupMembers = groupMemberMapper.selectList(new QueryWrapper<SmsGroupMember>().eq("group_record_id", groupRecord.getId()).eq("status", 2));
                groupRecord.setList(groupMembers);
            }
            return new CommonResult().success(groupRecords);

        } catch (Exception e) {
            log.error("根据条件查询所有列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "保存")
    @ApiOperation("保存")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('sms:SmsGroup:create')")
    public Object saveSmsGroup(@RequestBody SmsGroup smsGroup) {
        try {
            CommonResult commonResult;
            Long now = System.currentTimeMillis();
            if (smsGroup.getStartTime().getTime() < now || smsGroup.getEndTime().getTime() < now ||
                    smsGroup.getEndTime().getTime() < smsGroup.getStartTime().getTime()) {
                return new CommonResult().failed("选中的时间错误");
            }
            SmsGroup sm = new SmsGroup();
            sm.setGoodsId(smsGroup.getGoodsId());
            List<SmsGroup> smsGroupMemberList = ISmsGroupService.list(new QueryWrapper<>(sm));
            if (smsGroupMemberList != null && smsGroupMemberList.size() > 0) {
                return new CommonResult().failed("此商品已有拼团，商品编码=" + smsGroupMemberList.get(0).getGoodsId());
            }

            smsGroup.setCreateTime(new Date());
            boolean count = ISmsGroupService.save(smsGroup);
            if (count) {
                commonResult = new CommonResult().success(count);
            } else {
                commonResult = new CommonResult().failed();
            }
            return commonResult;
        } catch (Exception e) {
            log.error("保存：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @SysLog(MODULE = "sms", REMARK = "更新")
    @ApiOperation("更新")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('sms:SmsGroup:update')")
    public Object updateSmsGroup(@RequestBody SmsGroup entity) {
        try {
            if (ISmsGroupService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "删除")
    @ApiOperation("删除")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('sms:SmsGroup:delete')")
    public Object deleteSmsGroup(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            if (ISmsGroupService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "sms", REMARK = "给分配")
    @ApiOperation("查询明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('sms:SmsGroup:read')")
    public Object getSmsGroupById(@ApiParam("id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("id");
            }
            SmsGroup coupon = ISmsGroupService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除")
    @PreAuthorize("hasAuthority('sms:SmsGroup:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = ISmsGroupService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    private void calateStatus(SmsGroup smsGroup) {
        Long now = System.currentTimeMillis();
        if (now < smsGroup.getStartTime().getTime()) {
            smsGroup.setStatus(1);
        }
        if (now >= smsGroup.getStartTime().getTime() && now <= smsGroup.getEndTime().getTime()) {
            smsGroup.setStatus(2);
        }
        if (now > smsGroup.getEndTime().getTime()) {
            smsGroup.setStatus(3);
        }
    }
}
