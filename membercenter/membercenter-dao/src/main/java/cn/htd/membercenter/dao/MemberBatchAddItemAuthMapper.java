package cn.htd.membercenter.dao;

import cn.htd.common.Pager;
import cn.htd.membercenter.domain.MemberBatchAddItemAuth;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthInDTO;
import cn.htd.membercenter.dto.QueryBatchAddItemAuthOutDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberBatchAddItemAuthMapper {
    int deleteByPrimaryKey(Long id);

    int deleteBySellerId(Long id);

    int insert(MemberBatchAddItemAuth record);

    int insertSelective(MemberBatchAddItemAuth record);

    MemberBatchAddItemAuth selectByPrimaryKey(Long id);

    MemberBatchAddItemAuth selectBysellerId(Long sellerId);

    int updateByPrimaryKeySelective(MemberBatchAddItemAuth record);

    int updateByPrimaryKey(MemberBatchAddItemAuth record);

    List<QueryBatchAddItemAuthOutDTO> queryBatchAddItemAuthList(@Param("param") QueryBatchAddItemAuthInDTO queryBatchAddItemAuthInDTO, @Param("pager") Pager pager);

    Long queryBatchAddItemAuthListCount(@Param("param") QueryBatchAddItemAuthInDTO queryBatchAddItemAuthInDTO);

    void deleteExpireDate();

}