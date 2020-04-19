package com.zscat.mallplus.ums.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zscat.mallplus.annotation.SysLog;
import com.zscat.mallplus.enums.AllEnum;
import com.zscat.mallplus.oms.mapper.OmsOrderMapper;
import com.zscat.mallplus.ums.entity.UmsIntegrationChangeHistory;
import com.zscat.mallplus.ums.entity.UmsMember;
import com.zscat.mallplus.ums.entity.UmsMemberBlanceLog;
import com.zscat.mallplus.ums.mapper.UmsIntegrationChangeHistoryMapper;
import com.zscat.mallplus.ums.service.IUmsMemberBlanceLogService;
import com.zscat.mallplus.ums.service.IUmsMemberLevelService;
import com.zscat.mallplus.ums.service.IUmsMemberService;
import com.zscat.mallplus.util.UserUtils;
import com.zscat.mallplus.utils.CommonResult;
import com.zscat.mallplus.utils.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author zscat
 * @since 2019-04-19
 */
@Slf4j
@RestController
@Api(tags = "UmsMemberController", description = "会员表管理")
@RequestMapping("/ums/UmsMember")
public class UmsMemberController {
    @Resource
    private IUmsMemberService IUmsMemberService;
    @Resource
    private IUmsMemberLevelService memberLevelService;
    @Resource
    private IUmsMemberBlanceLogService memberBlanceLogService;
    @Resource
    private UmsIntegrationChangeHistoryMapper umsIntegrationChangeHistoryService;
    @Resource
    private OmsOrderMapper omsOrderMapper;

    @SysLog(MODULE = "ums", REMARK = "根据条件查询所有会员表列表")
    @ApiOperation("根据条件查询所有会员表列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('ums:UmsMember:read')")
    public Object getUmsMemberByPage(UmsMember entity,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IUmsMemberService.page(new Page<UmsMember>(pageNum, pageSize), new QueryWrapper<>(entity).orderByDesc("create_time")));
        } catch (Exception e) {
            log.error("根据条件查询所有会员表列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ums", REMARK = "保存会员表")
    @ApiOperation("保存会员表")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('ums:UmsMember:create')")
    public Object saveUmsMember(@RequestBody UmsMember entity) {
        try {
            if (IUmsMemberService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存会员表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ums", REMARK = "更新会员表")
    @ApiOperation("更新会员表")
    @PostMapping(value = "/update")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    @Transactional
    public Object updateUmsMember(@RequestBody UmsMember entity) {
        try {
            if (IUmsMemberService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新会员表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }


    @SysLog(MODULE = "ums", REMARK = "后台余额充值")
    @ApiOperation("后台余额充值")
    @PostMapping(value = "/handleEditBlance")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    @Transactional
    public Object handleEditBlance(@RequestParam("id") Long id,
                                   @RequestParam("blance") BigDecimal blance) {
        try {
            UmsMember entity = new UmsMember();
            entity.setId(id);
            entity.setBlance(blance);
            UmsMember member = IUmsMemberService.getById(entity.getId());
            entity.setBlance(entity.getBlance().add(member.getBlance()));
            UmsMemberBlanceLog blog = new UmsMemberBlanceLog();
            blog.setMemberId(entity.getId());
            blog.setCreateTime(new Date());
            blog.setNote("后台余额充值：" + blance);
            blog.setPrice(entity.getBlance());
            blog.setType(AllEnum.BlanceType.ADD.code());
            memberBlanceLogService.save(blog);

            if (IUmsMemberService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("后台余额充值：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ums", REMARK = "后台积分充值")
    @ApiOperation("后台积分充值")
    @PostMapping(value = "/handleEditIntegration")
    //  @PreAuthorize("hasAuthority('ums:UmsMember:update')")
    @Transactional
    public Object handleEditIntegration(@RequestParam("id") Long id,
                                        @RequestParam("integration") Integer integration) {
        try {

            UmsMember entity = new UmsMember();
            entity.setId(id);
            entity.setIntegration(integration);
            UmsMember member = IUmsMemberService.getById(entity.getId());

            entity.setIntegration(entity.getIntegration() + member.getIntegration());
            UmsIntegrationChangeHistory history = new UmsIntegrationChangeHistory();
            history.setMemberId(entity.getId());
            history.setChangeCount(entity.getIntegration());
            history.setCreateTime(new Date());
            history.setChangeType(AllEnum.ChangeType.Add.code());
            history.setOperateNote("后台积分充值:" + integration);
            history.setSourceType(AllEnum.ChangeSource.admin.code());
            history.setOperateMan(UserUtils.getCurrentMember().getId() + "");
            umsIntegrationChangeHistoryService.insert(history);

            if (IUmsMemberService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("后台积分充值：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ums", REMARK = "删除会员表")
    @ApiOperation("删除会员表")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('ums:UmsMember:delete')")
    public Object deleteUmsMember(@ApiParam("会员表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("会员表id");
            }
            if (IUmsMemberService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除会员表：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "ums", REMARK = "给会员表分配会员表")
    @ApiOperation("查询会员表明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ums:UmsMember:read')")
    public Object getUmsMemberById(@ApiParam("会员表id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("会员表id");
            }
            UmsMember coupon = IUmsMemberService.getById(id);
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询会员表明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除会员表")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @ResponseBody
    @SysLog(MODULE = "pms", REMARK = "批量删除会员表")
    @PreAuthorize("hasAuthority('ums:UmsMember:delete')")
    public Object deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean count = IUmsMemberService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }

    @ApiOperation("更新会员的订单信息")
    @PostMapping(value = "/updateMemberOrderInfo")
    public Object updateMemberOrderInfo() {
        try {
            IUmsMemberService.updataMemberOrderInfo();
            return new CommonResult().success();
        } catch (Exception e) {
            log.error("更新会员的订单信息：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }
    }

}
