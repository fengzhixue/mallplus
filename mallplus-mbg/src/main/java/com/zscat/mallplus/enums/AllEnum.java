package com.zscat.mallplus.enums;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 交易API Constant
 *
 * @author dp
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AllEnum {


    /**
     * 改变类型：1->增加；2->减少
     *
     * @author dp
     */
    public enum ChangeType implements BaseEnum<Integer> {

        /**
         * 限价交易
         */
        Add(1, "add"),

        /**
         * 市价交易
         */
        Min(2, "min"),;

        private int code;
        private String value;

        ChangeType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }

    /**
     * 积分来源
     *
     * @author dp
     */
    public enum ChangeSource implements BaseEnum<Integer> {

        /**
         * 下单
         */
        order(1, "order"),

        /**
         * 登录
         */
        login(2, "login"),
        /**
         * 注册
         */
        register(3, "register"),
        /**
         * 签到送积分
         */
        sign(5, "sign"),

        /**
         * 后台添加
         */
        admin(4, "admin"),;

        private int code;
        private String value;

        ChangeSource(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }

    /**
     * 评论类型：1->商品；2->订单
     *
     * @author dp
     */
    public enum ConsultType implements BaseEnum<Integer> {

        /**
         *
         */
        GOODS(1, "goods"),

        /**
         *
         */
        ORDER(2, "order"),;

        private int code;
        private String value;

        ConsultType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }

    /**
     * 订单来源：
     *
     * @author dp
     */
    public enum OrderSource implements BaseEnum<Integer> {

        weixinApplet(1, "weixinApplet"),
        h5Source(2, "h5Source"),
        pcSource(3, "pcSource"),
        android(4, "android"),
        ios(5, "ios"),
        ;

        private int code;
        private String value;

        OrderSource(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }
    /**
     * 订单类型：
     *
     * @author dp
     */
    public enum OrderType implements BaseEnum<Integer> {

        /**
         * 普通订单
         */
        COMMON(1, "common"),
        /**
         * 拼团订单
         */
        PIN_GROUP(2, "pingroup"),
        /**
         * 团购订单
         */
        GROUP_BUY(3, "groupbuy"),
        /**
         * 砍价订单
         */
        KNAN_JIA(4, "kanjia"),
        JIFEN(5, "jifen"),
        /**
         * 秒杀订单
         */
        SKILL(6, "skill"),;

        private int code;
        private String value;

        OrderType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }

    /**
     * 支付类型
     */
    public enum OrderPayType implements BaseEnum<Integer> {

        /**
         * 微信小程序
         */
        weixinAppletPay(1, "weixinAppletPay"),
        /**
         *
         */
        alipay(2, "alipay"),
        /**
         * 余额支付
         */
        balancePay(3, "balancePay"),
        /**
         * 积分兑换
         */
        jifenPay(5, "jifenPay");

        private int code;
        private String value;

        OrderPayType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }

    /**
     * 评论类型：1->商品；2->赠品
     *
     * @author dp
     */
    public enum OrderItemType implements BaseEnum<Integer> {

        /**
         * 商品
         */
        GOODS(1, "goods"),

        /**
         * 礼品
         */
        GIFT(2, "gift"),;

        private int code;
        private String value;

        OrderItemType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }

    /**
     * 余额类型：'全部', '消费', '退款', '充值', '提现', '佣金', '平台调整'
     *
     * @author dp
     */
    public enum BlanceType implements BaseEnum<Integer> {

        ALL(0, "ALL"),
        /**
         *
         */
        CONSUMER(1, "CONSUMER"),

        /**
         *
         */
        REFUND(2, "REFUND"),
        ADD(3, "ADD"),

        DRAW(4, "DRAW"),

        COMMISSION(5, "COMMISSION"),

        MODIFY(6, "MODIFY"),;

        private int code;
        private String value;

        BlanceType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }

    /**
     * 订单退货申请：：0->待处理；1->退货中；2->已完成；3->已拒绝
     *
     * @author mallplus
     */
    public enum OmsOrderReturnApplyStatus implements BaseEnum<Integer> {

        /**
         *
         */
        INIT(0, "INIT"),

        REFUNDING(1, "REFUNDING"),
        REFUNDED(2, "REFUNDED"),
        /**
         *
         */
        REJECT(3, "REJECT"),;

        private int code;
        private String value;

        OmsOrderReturnApplyStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }
    /**
     * 订单退货申请：0换货，1退钱 2退货3 退钱退货
     *
     * @author mallplus
     */
    public enum OmsOrderReturnApplyType implements BaseEnum<Integer> {

        CHANGEGOODS(0, "RETURNMONEY"),
        /**
         *
         */
        RETURNMONEY(1, "RETURNMONEY"),

        RETURNGOODS(2, "RETURNGOODS"),

        /**
         *
         */
        RETURNGOODSMONEY(3, "RETURNGOODSMONEY"),;

        private int code;
        private String value;

        OmsOrderReturnApplyType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public Integer code() {
            return code;
        }

        @Override
        public String desc() {
            return value;
        }
    }
}
