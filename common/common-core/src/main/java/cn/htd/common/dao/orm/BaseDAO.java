package cn.htd.common.dao.orm;

import java.util.List;

import cn.htd.common.Pager;
import org.apache.ibatis.annotations.Param;

public interface BaseDAO<T> {
	
	/** 这2个重复方法,以后只保留一个即可 */
	public void insert(T t);
	public void add(T t);
	
	/** 这2个重复方法,以后只保留一个即可 */
	public T selectById(Object id);
	public T queryById(Object id);

	
	public Integer update(T t);

	public Integer updateBySelect(T t);

	public Integer delete(Object id);

	
	/** 这3个重复方法,以后只保留一个即可 */
	public Long queryCount(@Param("entity") T entity);
	public Long selectCountByCondition(@Param("entity") T entity);
	public Long selectCount(@Param("entity") T entity);
	
	/** 这3个重复方法,以后只保留一个即可 */
	public List<T> queryList(@Param("entity") T entity, @Param("page") Pager page);
	public List<T> selectListByCondition(@Param("entity") T entity, @Param("page") Pager page);
	public List<T> selectList(@Param("entity") T entity, @Param("pager") Pager pager);


}
