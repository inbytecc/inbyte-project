package com.inbyte.component.admin.system.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inbyte.component.admin.system.user.model.system.user.merchant.InbyteSystemUserMerchantBrief;
import com.inbyte.component.admin.system.user.model.system.user.merchant.InbyteSystemUserMerchantPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户商户
 *
 * 表名 inbyte_system_user_merchant
 * @author chenjw
 * @date 2024-08-18 14:34:14
 */
public interface InbyteSystemUserMerchantMapper extends BaseMapper<InbyteSystemUserMerchantPo> {

    @Select("SELECT im.mct_no," +
            "       im.mct_name," +
            "       im.logo" +
            "  FROM inbyte_merchant im" +
            " INNER JOIN inbyte_system_user_merchant isum " +
            "    ON im.mct_no = isum.mct_no" +
            " WHERE isum.user_id = #{userId}")
    List<InbyteSystemUserMerchantBrief> listByUserId(@Param("userId") Integer userId);
}
