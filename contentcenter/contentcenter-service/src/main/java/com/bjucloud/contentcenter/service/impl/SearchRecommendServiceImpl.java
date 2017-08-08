package com.bjucloud.contentcenter.service.impl;

import cn.htd.common.DataGrid;
import cn.htd.common.ExecuteResult;
import cn.htd.common.Pager;
import com.bjucloud.contentcenter.dao.SearchRecommendDAO;
import com.bjucloud.contentcenter.dto.SearchRecommendDTO;
import com.bjucloud.contentcenter.dto.SearchRecommendProductDTO;
import com.bjucloud.contentcenter.dto.SearchRecommendWordDTO;
import com.bjucloud.contentcenter.service.SearchRecommendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by thinkpad on 2017/3/7.
 */
@Service("searchRecommendService")
public class SearchRecommendServiceImpl implements SearchRecommendService{

    private static final Logger logger = LoggerFactory.getLogger(SearchRecommendServiceImpl.class);
    @Resource
    private SearchRecommendDAO searchRecommendDAO;




    @Override
    public DataGrid<SearchRecommendDTO> queryByCondition(SearchRecommendDTO searchRecommendDTO, Pager page) {
        DataGrid<SearchRecommendDTO> dataGrid = new DataGrid<SearchRecommendDTO>();
        try{
            Long count = searchRecommendDAO.selectCountByCondition(searchRecommendDTO);
            List<SearchRecommendDTO> list = searchRecommendDAO.selectListByCondition(searchRecommendDTO,page);
            dataGrid.setRows(list);
            dataGrid.setTotal(count);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return dataGrid;
    }

    @Override
    public ExecuteResult<String> addRecommend(SearchRecommendDTO searchRecommendDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            if(searchRecommendDTO != null){
                this.searchRecommendDAO.insertRecommend(searchRecommendDTO);
                if(searchRecommendDTO.getWordDTOList() != null && searchRecommendDTO.getWordDTOList().size() > 0){
                    for(SearchRecommendWordDTO wordDto : searchRecommendDTO.getWordDTOList()){
                        wordDto.setRecommendWordId(searchRecommendDTO.getId());
                        this.searchRecommendDAO.insertRecommendWord(wordDto);
                    }
                }
                if(searchRecommendDTO.getProductDTOList() != null && searchRecommendDTO.getProductDTOList().size() > 0){
                    for(SearchRecommendProductDTO productDTO : searchRecommendDTO.getProductDTOList()){
                        productDTO.setRecommendWordId(searchRecommendDTO.getId());
                        this.searchRecommendDAO.insertRecommendProduct(productDTO);
                    }
                }
                result.setResult("success");
            }else {
                result.setResult("参数不能为空！");
                return result;
            }
        }catch (Exception e){
            logger.error("执行方法：【addRecommend】报错！{}", e);
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> modifyByCondition(SearchRecommendDTO searchRecommendDTO) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            if(searchRecommendDTO != null && searchRecommendDTO.getId() != null){
                this.searchRecommendDAO.updateRecommendByCondition(searchRecommendDTO);
                if(searchRecommendDTO.getWordDTOList() != null && searchRecommendDTO.getWordDTOList().size() > 0){
                    this.searchRecommendDAO.deleteWordByRecommendId(searchRecommendDTO.getId());
                    for(SearchRecommendWordDTO wordDTO : searchRecommendDTO.getWordDTOList()){
                        wordDTO.setRecommendWordId(searchRecommendDTO.getId());
                        this.searchRecommendDAO.insertRecommendWord(wordDTO);
                    }
                }
                if(searchRecommendDTO.getProductDTOList() != null && searchRecommendDTO.getProductDTOList().size() > 0){
                    this.searchRecommendDAO.deleteProductByRecommendId(searchRecommendDTO.getId());
                    for(SearchRecommendProductDTO productDTO : searchRecommendDTO.getProductDTOList()){
                        productDTO.setRecommendWordId(searchRecommendDTO.getId());
                        this.searchRecommendDAO.insertRecommendProduct(productDTO);
                    }
                }
                result.setResult("success");
            }else {
                result.setResult("id不能为空");
                return result;
            }
        }catch (Exception e){
            logger.error("执行方法：【addRecommend】报错！{}", e);
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<SearchRecommendDTO> qeuryById(Long id) {
        ExecuteResult<SearchRecommendDTO> result = new ExecuteResult<SearchRecommendDTO>();
        try{
            SearchRecommendDTO searchRecommendDTO = this.searchRecommendDAO.selectById(id);
            if(searchRecommendDTO != null){
                List<SearchRecommendWordDTO> wordDTOs = this.searchRecommendDAO.selectWordByRecommendId(id);
                if(wordDTOs != null && wordDTOs.size() > 0){
                    searchRecommendDTO.setWordDTOList(wordDTOs);
                }
                List<SearchRecommendProductDTO> productDTOs = this.searchRecommendDAO.selectProductByRecommendId(id);
                if(productDTOs != null && productDTOs.size() > 0){
                    searchRecommendDTO.setProductDTOList(productDTOs);
                }
                result.setResult(searchRecommendDTO);
            }
        }catch (Exception e){
            logger.error("执行方法：【qeuryById】报错！{}", e);
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ExecuteResult<String> deleteById(Long id) {
        ExecuteResult<String> result = new ExecuteResult<String>();
        try{
            this.searchRecommendDAO.deleteRecommendById(id);
            result.setResult("success");
        }catch (Exception e){
            logger.error("执行方法：【deleteById】报错！{}", e);
            result.getErrorMessages().add(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public DataGrid<SearchRecommendDTO> queryByName(String name) {
        DataGrid<SearchRecommendDTO> dataGrid = new DataGrid<SearchRecommendDTO>();
        try{
            List<SearchRecommendDTO> list = this.searchRecommendDAO.selectByName(name);
            dataGrid.setRows(list);
        }catch (Exception e){
            logger.error("执行方法：【deleteById】报错！{}", e);
        }
        return dataGrid;
    }

}
